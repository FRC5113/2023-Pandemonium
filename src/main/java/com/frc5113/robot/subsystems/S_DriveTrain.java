// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.subsystems;

import static com.frc5113.robot.constants.DrivetrainConstants.*;

import com.frc5113.library.loops.ILooper;
import com.frc5113.library.loops.Loop;
import com.frc5113.library.motors.SmartNeo;
import com.frc5113.library.subsystem.SmartSubsystem;
import com.frc5113.robot.primative.DrivetrainEncoders;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.security.InvalidParameterException;

/** The drivetrain, well... drives */
public class S_DriveTrain extends SmartSubsystem {
  private final SmartNeo leftLeader;
  private final SmartNeo leftFollower;
  private final SmartNeo rightLeader;
  private final SmartNeo rightFollower;

  private final RelativeEncoder leftLeaderEncoder;
  private final RelativeEncoder rightLeaderEncoder;
  private final RelativeEncoder leftFollowerEncoder;
  private final RelativeEncoder rightFolowerEncoder;

  private final MotorControllerGroup leftGroup;
  private final MotorControllerGroup rightGroup;

  private final DifferentialDrive drive;

  // encoder values
  private final DrivetrainEncoders encoders;

  /** Creates a new DriveTrain. */
  public S_DriveTrain() {
    leftLeader = new SmartNeo(LEFT_LEADER_ID, MOTOR_MODE);
    leftFollower = new SmartNeo(LEFT_FOLLOWER_ID, MOTOR_MODE);
    rightLeader = new SmartNeo(RIGHT_LEADER_ID, MOTOR_MODE);
    rightFollower = new SmartNeo(RIGHT_FOLLOWER_ID, MOTOR_MODE);

    leftGroup = new MotorControllerGroup(leftLeader, leftFollower);
    rightGroup = new MotorControllerGroup(rightLeader, rightFollower);

    leftLeader.setInverted(true);
    leftFollower.setInverted(true);
    rightGroup.setInverted(false);

    drive = new DifferentialDrive(leftGroup, rightGroup);

    // Generate the onboard encoders
    leftLeaderEncoder = leftLeader.encoder;
    rightLeaderEncoder = rightLeader.encoder;
    leftFollowerEncoder = leftFollower.encoder;
    rightFolowerEncoder = rightFollower.encoder;

    encoders = new DrivetrainEncoders();
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    if (leftSpeed < -1 || leftSpeed > 1 || rightSpeed < -1 || rightSpeed > 1) {
      throw new InvalidParameterException(
          "Left=" + leftSpeed + " Right=" + rightSpeed + " - MUST -1 < Left||Right < 1");
    }
    drive.tankDrive(leftSpeed, rightSpeed);
  }

  public void arcadeDrive(double arcadeSpeed, double arcadeTurn) {
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
    SmartDashboard.putNumber("Drive: Right Leader Enc", rightLeaderEncoder.getPosition());
    SmartDashboard.putNumber("Drive: Left Leader Enc", leftLeaderEncoder.getPosition());
    SmartDashboard.putNumber("Drive: Right Follower Enc", rightFolowerEncoder.getPosition());
    SmartDashboard.putNumber("Drive: Left Follower Enc", leftFollowerEncoder.getPosition());
  }

  @Override
  public void zeroSensors() {
    leftLeaderEncoder.setPosition(0);
    leftFollowerEncoder.setPosition(0);
    rightLeaderEncoder.setPosition(0);
    rightFolowerEncoder.setPosition(0);
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
        leftLeaderEncoder.getPosition(),
        rightLeaderEncoder.getPosition(),
        leftFollowerEncoder.getPosition(),
        rightFollower.getPosition());
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
  public CANSparkMax getLeftFollower() {
    return leftFollower;
  }

  public CANSparkMax getLeftLeader() {
    return leftLeader;
  }

  public CANSparkMax getRightFollower() {
    return rightFollower;
  }

  public CANSparkMax getRightLeader() {
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
