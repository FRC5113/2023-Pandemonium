// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.subsystems;

import static com.frc5113.robot.constants.DrivetrainConstants.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.frc5113.library.loops.ILooper;
import com.frc5113.library.loops.Loop;
import com.frc5113.library.motors.SmartFalcon;
import com.frc5113.library.oi.scalers.CubicCurve;
import com.frc5113.robot.primative.DrivetrainEncoders;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.security.InvalidParameterException;

/** The drivetrain, well... drives */
public class S_DriveTrainPandeguardium extends DriveTrain {
  private final SmartFalcon leftLeader;
  private final SmartFalcon leftFollower;
  private final SmartFalcon rightLeader;
  private final SmartFalcon rightFollower;

  private final MotorControllerGroup leftGroup;
  private final MotorControllerGroup rightGroup;

  private final DifferentialDrive drive;
  private final DifferentialDrivePoseEstimator driveOdometry;

  // encoder values
  private final DrivetrainEncoders encoders;

  // input scaler
  private final CubicCurve curve;

  private final Field2d field = new Field2d();

  /** Creates a new DriveTrain. */
  public S_DriveTrainPandeguardium(Rotation2d initialRotation) {
    leftLeader = new SmartFalcon(LEFT_LEADER_ID_PANDEGUARDIUM, false, MOTOR_MODE_PANDEGUARDIUM);
    leftFollower = new SmartFalcon(LEFT_FOLLOWER_ID_PANDEGUARDIUM, false, MOTOR_MODE_PANDEGUARDIUM);
    rightLeader = new SmartFalcon(RIGHT_LEADER_ID_PANDEGUARDIUM, true, MOTOR_MODE_PANDEGUARDIUM);
    rightFollower =
        new SmartFalcon(RIGHT_FOLLOWER_ID_PANDEGUARDIUM, true, MOTOR_MODE_PANDEGUARDIUM);
    leftLeader.enableVoltageCompensation(false);
    rightLeader.enableVoltageCompensation(false);
    leftFollower.enableVoltageCompensation(false);
    rightFollower.enableVoltageCompensation(false);
    leftGroup = new MotorControllerGroup(leftLeader, leftFollower);
    rightGroup = new MotorControllerGroup(rightLeader, rightFollower);

    drive = new DifferentialDrive(leftGroup, rightGroup);
    driveOdometry =
        new DifferentialDrivePoseEstimator(kDriveKinematics, initialRotation, 0, 0, new Pose2d());

    encoders = new DrivetrainEncoders();

    curve = new CubicCurve(0.0, 0.3, 0.0);
  }

  public void tankDrive(double leftSpeedRaw, double rightSpeedRaw) {
    double leftSpeed = curve.calculateMappedVal(leftSpeedRaw);
    double rightSpeed = curve.calculateMappedVal(rightSpeedRaw);
    if (leftSpeed < -1 || leftSpeed > 1 || rightSpeed < -1 || rightSpeed > 1) {
      throw new InvalidParameterException(
          "Left=" + leftSpeed + " Right=" + rightSpeed + " - MUST -1 < Left||Right < 1");
    }
    drive.tankDrive(leftSpeed, rightSpeed);
  }

  public void arcadeDrive(double arcadeSpeedRaw, double arcadeTurnRaw) {
    double arcadeSpeed = curve.calculateMappedVal(arcadeSpeedRaw);
    double arcadeTurn = curve.calculateMappedVal(arcadeTurnRaw);
    if (arcadeSpeed < -1 || arcadeSpeed > 1 || arcadeTurn < -1 || arcadeTurn > 1) {
      throw new InvalidParameterException(
          "Speed=" + arcadeSpeed + " Turn=" + arcadeTurn + " - MUST -1 < Speed||Turn < 1");
    }
    drive.arcadeDrive(arcadeSpeed, arcadeTurn);
  }

  // Methods required by SmartSubsystem
  @Override
  public void outputTelemetry() {
    SmartDashboard.putData("Drive: Diff Drive", drive);
    SmartDashboard.putNumber("Drive: Right Leader Enc", rightLeader.getEncoderTicks());
    SmartDashboard.putNumber("Drive: Left Leader Enc", leftLeader.getEncoderTicks());
    SmartDashboard.putNumber("Drive: Right Follower Enc", rightFollower.getEncoderTicks());
    SmartDashboard.putNumber("Drive: Left Follower Enc", leftFollower.getEncoderTicks());
    SmartDashboard.putNumber(
        "Drive: Right Leader VT", ticksToMeters(rightLeader.getEncoderTicks()));
    SmartDashboard.putNumber("Drive: Left Leader VT", ticksToMeters(leftLeader.getEncoderTicks()));
    SmartDashboard.putNumber(
        "Drive: Right Follower VT", ticksToMeters(rightFollower.getEncoderTicks()));
    SmartDashboard.putNumber(
        "Drive: Left Follower VT", ticksToMeters(leftFollower.getEncoderTicks()));
    SmartDashboard.putData("Drive: Field", field);
    System.out.println();
  }

  @Override
  public void zeroSensors() {
    leftLeader.resetEncoder();
    leftFollower.resetEncoder();
    rightLeader.resetEncoder();
    rightFollower.resetEncoder();
  }

  @Override
  public boolean checkSubsystem() {
    return true; // FIXME
  }

  @Override
  public void stop() {
    rightGroup.set(0);
    leftGroup.set(0);
  }

  @Override
  public void readPeriodicInputs() {
    updatePositions();
    field.setRobotPose(getPose());
  }

  @Override
  public void writePeriodicOutputs() {}

  @Override
  public void registerEnabledLoops(ILooper enabledLooper) {
    enabledLooper.register(
        new Loop() {
          @Override
          public void onLoop(double arg0) {}

          @Override
          public void onStart(double arg0) {
            stop(); // be sure to stop
            zeroSensors();
          }

          @Override
          public void onStop(double arg0) {
            stop(); // be sure to stop
            zeroSensors();
          }
        });
  }

  // GETTERS
  public WPI_TalonFX getLeftFollower() {
    return leftFollower;
  }

  public WPI_TalonFX getLeftLeader() {
    return leftLeader;
  }

  public WPI_TalonFX getRightFollower() {
    return rightFollower;
  }

  public WPI_TalonFX getRightLeader() {
    return rightLeader;
  }

  public DifferentialDrive getDifferentialDrive() {
    return drive;
  }

  public MotorControllerGroup getLeftMotorGroup() {
    return leftGroup;
  }

  public MotorControllerGroup getRightMotorGroup() {
    return rightGroup;
  }

  public DrivetrainEncoders getEncoders() {
    return encoders;
  }

  // public double posToMeters(double position) {
  //   // Wheel circ in inches
  //   return Units.inchesToMeters((WHEEL_CIRCUMFERENCE / GEAR_RATIO) * position);
  // }

  @Override
  public void updatePositions() {}

  public void updatePose(Rotation2d gyroAngle) {
    driveOdometry.update(
        gyroAngle,
        ticksToMeters(leftLeader.getEncoderTicks()),
        ticksToMeters(rightLeader.getEncoderTicks()));
  }

  public Pose2d getPose() {
    return driveOdometry.getEstimatedPosition();
  }

  private double ticksToMeters(double ticks) {
    // return (ticks * (Units.inchesToMeters(WHEEL_CIRCUMFERENCE))) / (2048 * GEAR_RATIO);
    double shaftRotations = ticks / 2048;
    double wheelRotations = shaftRotations / GEAR_RATIO;
    double distTraveled = wheelRotations * WHEEL_CIRCUMFERENCE;
    return distTraveled;
  }

  public void resetOdometry(Rotation2d gyroAngle, Pose2d pose) {
    zeroSensors();
    driveOdometry.resetPosition(
        gyroAngle,
        ticksToMeters(leftLeader.getEncoderTicks()),
        ticksToMeters(rightLeader.getEncoderTicks()),
        pose);
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(
        ticksToMeters(leftLeader.getEncoderVelocity()),
        ticksToMeters(rightLeader.getEncoderVelocity()));
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftGroup.setVoltage(leftVolts);
    rightGroup.setVoltage(rightVolts);
    SmartDashboard.putNumber("tdv/left", leftVolts);
    SmartDashboard.putNumber("tdv/right", rightVolts);
    drive.feed();
  }

  @Override
  public boolean checkSubsystemPeriodic() {
    return false;
  }

  @Override
  public void registerPeriodicSubsystemCheck(ILooper loop) {}
}
