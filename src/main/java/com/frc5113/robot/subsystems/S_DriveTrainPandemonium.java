// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.subsystems;

import static com.frc5113.robot.constants.DrivetrainConstants.*;

import com.frc5113.library.loops.ILooper;
import com.frc5113.library.loops.Loop;
import com.frc5113.robot.primative.DrivetrainEncoders;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.security.InvalidParameterException;

/** The drivetrain, well... drives */
public class S_DriveTrainPandemonium extends DriveTrain {
  private final CANSparkMax leftLeader;
  private final CANSparkMax leftFollower;
  private final CANSparkMax rightLeader;
  private final CANSparkMax rightFollower;

  private final RelativeEncoder leftLeaderEncoder;
  private final RelativeEncoder rightLeaderEncoder;
  private final RelativeEncoder leftFollowerEncoder;
  private final RelativeEncoder rightFollowerEncoder;

  private final MotorControllerGroup leftGroup;
  private final MotorControllerGroup rightGroup;

  private final DifferentialDrive drive;
  private final DifferentialDrivePoseEstimator driveOdometry;

  // encoder values
  private final DrivetrainEncoders encoders;

  /** Creates a new DriveTrain. */
  public S_DriveTrainPandemonium(Rotation2d initialRotation) {
    leftLeader = new CANSparkMax(LEFT_LEADER_ID_PANDEMONIUM, MotorType.kBrushless);
    rightLeader = new CANSparkMax(RIGHT_LEADER_ID_PANDEMONIUM, MotorType.kBrushless);
    leftFollower = new CANSparkMax(LEFT_FOLLOWER_ID_PANDEMONIUM, MotorType.kBrushless);
    rightFollower = new CANSparkMax(RIGHT_FOLLOWER_ID_PANDEMONIUM, MotorType.kBrushless);

    leftGroup = new MotorControllerGroup(leftLeader, leftFollower);
    rightGroup = new MotorControllerGroup(rightLeader, rightFollower);

    leftLeader.setInverted(true);
    leftFollower.setInverted(true);
    rightLeader.setInverted(false);
    rightFollower.setInverted(false);

    drive = new DifferentialDrive(leftGroup, rightGroup);

    // Generate the onboard encoders
    leftLeaderEncoder = leftLeader.getEncoder();
    rightLeaderEncoder = rightLeader.getEncoder();
    leftFollowerEncoder = leftFollower.getEncoder();
    rightFollowerEncoder = rightFollower.getEncoder();

    encoders = new DrivetrainEncoders();
    driveOdometry =
        new DifferentialDrivePoseEstimator(kDriveKinematics, initialRotation, 0, 0, new Pose2d());
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
    SmartDashboard.putNumber("Drive: Right Follower Enc", rightFollowerEncoder.getPosition());
    SmartDashboard.putNumber("Drive: Left Follower Enc", leftFollowerEncoder.getPosition());
  }

  @Override
  public void zeroSensors() {
    leftLeaderEncoder.setPosition(0);
    leftFollowerEncoder.setPosition(0);
    rightLeaderEncoder.setPosition(0);
    rightFollowerEncoder.setPosition(0);
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
        rightFollowerEncoder.getPosition());
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

  public double posToMeters(double rawPosition) {
    return (rawPosition * WHEEL_CIRCUMFERENCE) / GEAR_RATIO;
  }

  public double posVelocityToMeters(double rawVelocity) {
    return (rawVelocity * WHEEL_CIRCUMFERENCE) / (GEAR_RATIO * 60);
  }

  @Override
  public void updatePose(Rotation2d gyroAngle) {
    driveOdometry.update(
        gyroAngle,
        posToMeters(leftLeaderEncoder.getPosition()),
        posToMeters(rightLeaderEncoder.getPosition()));
  }

  @Override
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftGroup.setVoltage(leftVolts);
    rightGroup.setVoltage(rightVolts);
    drive.feed();
  }

  @Override
  public void resetOdometry(Rotation2d gyroAngle, Pose2d pose) {
    zeroSensors();
    driveOdometry.resetPosition(
        gyroAngle,
        posToMeters(leftLeaderEncoder.getPosition()),
        posToMeters(rightLeaderEncoder.getPosition()),
        pose);
  }

  @Override
  public Pose2d getPose() {
    return driveOdometry.getEstimatedPosition();
  }

  @Override
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(
        posVelocityToMeters(leftLeaderEncoder.getVelocity()),
        posVelocityToMeters(rightLeaderEncoder.getVelocity()));
  }

  @Override
  public boolean checkSubsystemPeriodic() {
    return false;
  }

  @Override
  public void registerPeriodicSubsystemCheck(ILooper loop) {}
}