
package com.frc5113.robot.subsystems.DriveTrain;

import java.util.function.Supplier;

import org.littletonrobotics.junction.Logger;

import static com.frc5113.robot.constants.DrivetrainConstants.*;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.frc5113.robot.subsystems.DriveTrain.DriveIO.DriveIOInputs;
import com.frc5113.robot.subsystems.S_Gyro;

public class S_DriveTrain extends SubsystemBase {
  private static final double maxCoastVelocityMetersPerSec = 0.05; // Need to be under this to
                                                                   // switch to coast when disabling

  private final double wheelRadiusMeters;
  private final double maxVelocityMetersPerSec;
  private final double trackWidthMeters;
  private final SimpleMotorFeedforward leftModel, rightModel;
  

  private final DriveIO io;
  private final DriveIOInputs inputs = new DriveIOInputs();

  private final S_Gyro gyro;

  private Supplier<Boolean> disableOverride = () -> false;
  private Supplier<Boolean> openLoopOverride = () -> false;
  private Supplier<Boolean> internalEncoderOverride = () -> false;


  private double lastLeftPositionMeters = 0.0;
  private double lastRightPositionMeters = 0.0;
  private boolean lastGyroConnected = false;
  private Rotation2d lastGyroRotation = new Rotation2d();
  private boolean brakeMode = false;
  private boolean pitchResetComplete = false;
  private double basePitchRadians = 0.0;

  /** Creates a new DriveTrain. */
  public S_DriveTrain(DriveIO io) {
    this.io = io;
    maxVelocityMetersPerSec = MAX_VELOCITY;
    wheelRadiusMeters = WHEEL_RADIUS;
    trackWidthMeters = TRACK_WIDTH;
    leftModel = new SimpleMotorFeedforward(0.23071, 0.12413);
    rightModel = new SimpleMotorFeedforward(0.23455, 0.12270);
    io.setBrakeMode(false);
    gyro = new S_Gyro();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    io.updateInputs(inputs);
    Logger.getInstance().processInputs("Drive", inputs);

    // Update odometry
    Rotation2d currentGyroRotation =
        new Rotation2d(gyro.getAngle());
    double leftPositionMetersDelta =
        getLeftPositionMeters() - lastLeftPositionMeters;
    double rightPositionMetersDelta =
        getRightPositionMeters() - lastRightPositionMeters;
    double avgPositionMetersDelta =
        (leftPositionMetersDelta + rightPositionMetersDelta) / 2.0;
    Rotation2d gyroRotationDelta =
        (inputs.gyroConnected && !lastGyroConnected) ? new Rotation2d()
            : currentGyroRotation.minus(lastGyroRotation);


    lastLeftPositionMeters = getLeftPositionMeters();
    lastRightPositionMeters = getRightPositionMeters();
    lastGyroRotation = currentGyroRotation;



    // Update brake mode
    if (DriverStation.isEnabled()) {
      if (!brakeMode) {
        brakeMode = true;
        io.setBrakeMode(true);
      }
    } else {
      if (brakeMode
          && Math
              .abs(getLeftVelocityMetersPerSec()) < maxCoastVelocityMetersPerSec
          && Math.abs(
              getRightVelocityMetersPerSec()) < maxCoastVelocityMetersPerSec) {
        brakeMode = false;
        io.setBrakeMode(false);
      }
    }
  }

  /** Return left position in meters. */
  public double getLeftPositionMeters() {
    if (inputs.externalAvailable && !internalEncoderOverride.get()) {
      return inputs.externalLeftPositionRad * wheelRadiusMeters;
    } else {
      return inputs.leftPositionRad * wheelRadiusMeters;
    }
  }

  /** Return right position in meters. */
  public double getRightPositionMeters() {
    if (inputs.externalAvailable && !internalEncoderOverride.get()) {
      return inputs.externalRightPositionRad * wheelRadiusMeters;
    } else {
      return inputs.rightPositionRad * wheelRadiusMeters;
    }
  }

  /** Return left velocity in meters per second. */
  public double getLeftVelocityMetersPerSec() {
    if (inputs.externalAvailable && !internalEncoderOverride.get()) {
      return inputs.externalRightVelocityRadPerSec * wheelRadiusMeters;
    } else {
      return inputs.rightVelocityRadPerSec * wheelRadiusMeters;
    }
  }

  /** Return right velocity in meters per second. */
  public double getRightVelocityMetersPerSec() {
    if (inputs.externalAvailable && !internalEncoderOverride.get()) {
      return inputs.externalLeftVelocityRadPerSec * wheelRadiusMeters;
    } else {
      return inputs.leftVelocityRadPerSec * wheelRadiusMeters;
    }
  }

  /**
   * Drive at the specified voltage with no other processing. Only use when characterizing.
   */
  public void driveVoltage(double leftVolts, double rightVolts) {
    if (disableOverride.get()) {
      io.setVoltage(0, 0);
      return;
    }

    io.setVoltage(leftVolts, rightVolts);
  }

  /**
   * Drive at the specified percentage of max speed.
   */
  public void drivePercent(double leftPercent, double rightPercent) {
    if (disableOverride.get()) {
      io.setVoltage(0, 0);
      return;
    }

    driveVelocity(leftPercent * maxVelocityMetersPerSec,
        rightPercent * maxVelocityMetersPerSec);
  }

  /**
   * Drive at the specified velocity.
   */
  public void driveVelocity(double leftVelocityMetersPerSec,
      double rightVelocityMetersPerSec) {
    if (disableOverride.get()) {
      io.setVoltage(0, 0);
      return;
    }

    // Calculate setpoint and feed forward voltage
    double leftVelocityRadPerSec = leftVelocityMetersPerSec / wheelRadiusMeters;
    double rightVelocityRadPerSec =
        rightVelocityMetersPerSec / wheelRadiusMeters;
    double leftFFVolts = leftModel.calculate(leftVelocityRadPerSec);
    double rightFFVolts = rightModel.calculate(rightVelocityRadPerSec);

    Logger.getInstance().recordOutput("Drive/LeftSetpointRadPerSec",
        leftVelocityRadPerSec);
    Logger.getInstance().recordOutput("Drive/RightSetpointRadPerSec",
        rightVelocityRadPerSec);

    // Send commands to motors
    if (openLoopOverride.get()) {
      // Use open loop control
      io.setVoltage(leftFFVolts, rightFFVolts);
    } else {
      io.setVelocity(leftVelocityRadPerSec, rightVelocityRadPerSec, leftFFVolts,
          rightFFVolts);
    }
  }

  /**
   * In open loop, goes to neutral. In closed loop, resets velocity setpoint.
   */
  public void stop() {
    drivePercent(0, 0);
  }

  /** Resets the pitch measurement. */
  public void resetPitch() {
    basePitchRadians = inputs.gyroPitchPositionRad;
  }

  /** Gets the current pitch measurement in degrees. */
  public double getPitchDegrees() {
    return Math.toDegrees(inputs.gyroPitchPositionRad - basePitchRadians);
  }

  /** Return track width in meters. */
  public double getTrackWidthMeters() {
    return trackWidthMeters;
  }

  /** Return average kS. */
  public double getKs() {
    return (leftModel.ks + rightModel.ks) / 2;
  }

  /** Return average kV in (volts * second) / meter. */
  public double getKv() {
    return ((leftModel.kv + rightModel.kv) / 2) / wheelRadiusMeters;
  }

  /** Return average kA in (volts * second^2) / meter. */
  public double getKa() {
    return ((leftModel.ka + rightModel.ka) / 2) / wheelRadiusMeters;
  }

  /** Returns left velocity in radians per second. Only use for characterization. */
  public double getCharacterizationVelocityLeft() {
    return getLeftVelocityMetersPerSec() / wheelRadiusMeters;
  }

  /** Returns right velocity in radians per second. Only use for characterization. */
  public double getCharacterizationVelocityRight() {
    return getRightVelocityMetersPerSec() / wheelRadiusMeters;
  }

  /** Returns gyro position in radians. Only use for characterization */
  public double getCharacterizationGyroPosition() {
    return inputs.gyroYawPositionRad;
  }
}