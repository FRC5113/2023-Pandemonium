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
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
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

  // encoder values
  private final DrivetrainEncoders encoders;

  // input scaler
  private final CubicCurve curve;

  /** Creates a new DriveTrain. */
  public S_DriveTrainPandeguardium() {
    leftLeader = new SmartFalcon(LEFT_LEADER_ID_PANDEGUARDIUM, true, MOTOR_MODE_PANDEGUARDIUM);
    leftFollower = new SmartFalcon(LEFT_FOLLOWER_ID_PANDEGUARDIUM, true, MOTOR_MODE_PANDEGUARDIUM);
    rightLeader = new SmartFalcon(RIGHT_LEADER_ID_PANDEGUARDIUM, false, MOTOR_MODE_PANDEGUARDIUM);
    rightFollower =
        new SmartFalcon(RIGHT_FOLLOWER_ID_PANDEGUARDIUM, false, MOTOR_MODE_PANDEGUARDIUM);

    leftGroup = new MotorControllerGroup(leftLeader, leftFollower);
    rightGroup = new MotorControllerGroup(rightLeader, rightFollower);

    drive = new DifferentialDrive(leftGroup, rightGroup);

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
    SmartDashboard.putNumber("Drive: Right Leader Enc", rightLeader.getEncoderRotations());
    SmartDashboard.putNumber("Drive: Left Leader Enc", leftLeader.getEncoderRotations());
    SmartDashboard.putNumber("Drive: Right Follower Enc", rightFollower.getEncoderRotations());
    SmartDashboard.putNumber("Drive: Left Follower Enc", leftFollower.getEncoderRotations());
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

  public void updatePositions() {
    encoders.updateMeasurements(
        leftLeader.getEncoderRotations(),
        rightLeader.getEncoderRotations(),
        leftFollower.getEncoderRotations(),
        rightFollower.getEncoderRotations());
  }

  @Override
  public void readPeriodicInputs() {
    updatePositions();
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
}
