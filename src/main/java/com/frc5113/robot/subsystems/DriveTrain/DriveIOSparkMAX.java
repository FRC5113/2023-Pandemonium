package com.frc5113.robot.subsystems.DriveTrain;

import static com.frc5113.robot.constants.DrivetrainConstants.*;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxPIDController.ArbFFUnits;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import com.frc5113.library.motors.SmartNeo;

public class DriveIOSparkMAX implements DriveIO {

  private final boolean leftInverted;
  private final boolean rightInverted;

  private final SmartNeo leftLeader;
  private final SmartNeo leftFollower;
  private final SmartNeo rightLeader;
  private final SmartNeo rightFollower;

  private final MotorControllerGroup leftGroup;
  private final MotorControllerGroup rightGroup;

  private final double afterEncoderReduction;
  private final boolean hasExternalEncoders;
  private Encoder leftExternalEncoder;
  private Encoder rightExternalEncoder;

  private final RelativeEncoder leftLeaderEncoder;
  private final RelativeEncoder rightLeaderEncoder;
  private final RelativeEncoder leftFollowerEncoder;
  private final RelativeEncoder rightFolslowerEncoder;

  private final SparkMaxPIDController leftPID;
  private final SparkMaxPIDController rightPID;


  public DriveIOSparkMAX() {
      

    afterEncoderReduction = 6.0; // Internal encoders
    hasExternalEncoders = true;
    leftInverted = true;
    rightInverted = false;

    leftLeader = new SmartNeo(LEFT_LEADER_ID, MOTOR_MODE);
    leftFollower = new SmartNeo(LEFT_FOLLOWER_ID, MOTOR_MODE);
    rightLeader = new SmartNeo(RIGHT_LEADER_ID, MOTOR_MODE);
    rightFollower = new SmartNeo(RIGHT_FOLLOWER_ID, MOTOR_MODE);


    
    leftGroup = new MotorControllerGroup(leftLeader, leftFollower);
    rightGroup = new MotorControllerGroup(rightLeader, rightFollower);
    leftLeader.setInverted(leftInverted);
    leftFollower.setInverted(!leftInverted);
    rightGroup.setInverted(rightInverted);

    leftLeaderEncoder = leftLeader.encoder;
    rightLeaderEncoder = rightLeader.encoder;
    leftFollowerEncoder = leftFollower.encoder;
    rightFollowerEncoder = rightFollower.encoder;

    leftLeaderEncoder.setPosition(0.0);
    leftFollowerEncoder.setPosition(0.0);
    rightLeaderEncoder.setPosition(0.0);
    rightFollowerEncoder.setPosition(0.0);
    /* 
    leftExternalEncoder = new Encoder(2, 3);
    rightExternalEncoder = new Encoder(0, 1);
    leftExternalEncoder.setDistancePerPulse(-1.0 / 2048.0);
    rightExternalEncoder.setDistancePerPulse(1.0 / 2048.0);
    */

    leftPID = leftLeader.getPIDController();
    rightPID = rightLeader.getPIDController();

    


    leftLeader.setInverted(leftInverted);
    rightLeader.setInverted(rightInverted);

    leftLeader.enableVoltageCompensation(12.0);
    rightLeader.enableVoltageCompensation(12.0);

    leftLeader.setSmartCurrentLimit(30);
    leftFollower.setSmartCurrentLimit(30);
    rightLeader.setSmartCurrentLimit(30);
    rightFollower.setSmartCurrentLimit(30);
    if (hasThreeControllers) {
      leftFollower2.setSmartCurrentLimit(30);
      rightFollower2.setSmartCurrentLimit(30);
    }

    leftLeader.setCANTimeout(0);
    leftFollower.setCANTimeout(0);
    rightLeader.setCANTimeout(0);
    rightFollower.setCANTimeout(0);
    if (hasThreeControllers) {
      leftFollower2.setCANTimeout(0);
      rightFollower2.setCANTimeout(0);
    }

    if (SparkMAXBurnManager.shouldBurn()) {
      leftLeader.burnFlash();
      leftFollower.burnFlash();
      rightLeader.burnFlash();
      rightFollower.burnFlash();
      if (hasThreeControllers) {
        leftFollower2.burnFlash();
        rightFollower2.burnFlash();
      }
    }

    if (gyro != null) {
      gyro.calibrate();
    }
  }

  @Override
  public void updateInputs(DriveIOInputs inputs) {
    inputs.leftPositionRad =
        Units.rotationsToRadians(leftInternalEncoder.getPosition())
            / afterEncoderReduction;
    inputs.rightPositionRad =
        Units.rotationsToRadians(rightInternalEncoder.getPosition())
            / afterEncoderReduction;
    inputs.leftVelocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(
        leftInternalEncoder.getVelocity()) / afterEncoderReduction;
    inputs.rightVelocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(
        rightInternalEncoder.getVelocity()) / afterEncoderReduction;

    inputs.externalAvailable = hasExternalEncoders;
    if (hasExternalEncoders) {
      inputs.externalLeftPositionRad =
          Units.rotationsToRadians(leftExternalEncoder.getDistance());
      inputs.externalRightPositionRad =
          Units.rotationsToRadians(rightExternalEncoder.getDistance());
      inputs.externalLeftVelocityRadPerSec =
          Units.rotationsToRadians(leftExternalEncoder.getRate());
      inputs.externalRightVelocityRadPerSec =
          Units.rotationsToRadians(rightExternalEncoder.getRate());
    }

    inputs.leftAppliedVolts =
        leftLeader.getAppliedOutput() * RobotController.getBatteryVoltage();
    inputs.rightAppliedVolts =
        rightLeader.getAppliedOutput() * RobotController.getBatteryVoltage();

    if (hasThreeControllers) {
      inputs.leftCurrentAmps = new double[] {leftLeader.getOutputCurrent(),
          leftFollower.getOutputCurrent(), leftFollower2.getOutputCurrent()};
      inputs.rightCurrentAmps = new double[] {rightLeader.getOutputCurrent(),
          rightFollower.getOutputCurrent(), rightFollower2.getOutputCurrent()};

      inputs.leftTempCelcius = new double[] {leftLeader.getMotorTemperature(),
          leftFollower.getMotorTemperature(),
          leftFollower2.getMotorTemperature()};
      inputs.rightTempCelcius = new double[] {rightLeader.getMotorTemperature(),
          rightFollower.getMotorTemperature(),
          rightFollower2.getMotorTemperature()};
    } else {
      inputs.leftCurrentAmps = new double[] {leftLeader.getOutputCurrent(),
          leftFollower.getOutputCurrent()};
      inputs.rightCurrentAmps = new double[] {rightLeader.getOutputCurrent(),
          rightFollower.getOutputCurrent()};

      inputs.leftTempCelcius = new double[] {leftLeader.getMotorTemperature(),
          leftFollower.getMotorTemperature()};
      inputs.rightTempCelcius = new double[] {rightLeader.getMotorTemperature(),
          rightFollower.getMotorTemperature()};
    }

    if (gyro != null) {
      inputs.gyroConnected = gyro.isConnected();
      inputs.gyroYawPositionRad = Math.toRadians(gyro.getAngle());
      inputs.gyroYawVelocityRadPerSec = Math.toRadians(gyro.getRate());
      inputs.gyroPitchPositionRad = Math.toRadians(gyro.getRoll());
      inputs.gyroRollPositionRad = Math.toRadians(gyro.getPitch());
      inputs.gyroZAccelMetersPerSec2 = gyro.getWorldLinearAccelZ() * 9.806;
    } else {
      inputs.gyroConnected = false;
    }
  }

  @Override
  public void setVoltage(double leftVolts, double rightVolts) {
    leftLeader.setVoltage(leftVolts);
    rightLeader.setVoltage(rightVolts);
  }

  @Override
  public void setVelocity(double leftVelocityRadPerSec,
      double rightVelocityRadPerSec, double leftFFVolts, double rightFFVolts) {
    double leftRPM =
        Units.radiansPerSecondToRotationsPerMinute(leftVelocityRadPerSec)
            * afterEncoderReduction;
    double rightRPM =
        Units.radiansPerSecondToRotationsPerMinute(rightVelocityRadPerSec)
            * afterEncoderReduction;
    leftPID.setReference(leftRPM, ControlType.kVelocity, 0, leftFFVolts,
        ArbFFUnits.kVoltage);
    rightPID.setReference(rightRPM, ControlType.kVelocity, 0, rightFFVolts,
        ArbFFUnits.kVoltage);
  }

  @Override
  public void setBrakeMode(boolean enable) {
    IdleMode mode = enable ? IdleMode.kBrake : IdleMode.kCoast;
    leftLeader.setIdleMode(mode);
    leftFollower.setIdleMode(mode);
    rightLeader.setIdleMode(mode);
    rightFollower.setIdleMode(mode);
    if (hasThreeControllers) {
      leftFollower2.setIdleMode(mode);
      rightFollower2.setIdleMode(mode);
    }
  }

  @Override
  public void configurePID(double kp, double ki, double kd) {
    leftPID.setP(kp);
    leftPID.setI(ki);
    leftPID.setD(kd);

    rightPID.setP(kp);
    rightPID.setI(ki);
    rightPID.setD(kd);
  }
}