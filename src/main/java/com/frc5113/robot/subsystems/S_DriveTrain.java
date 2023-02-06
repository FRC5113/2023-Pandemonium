// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.subsystems;

import static com.frc5113.robot.constants.DrivetrainConstants.*;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.security.InvalidParameterException;

public class S_DriveTrain extends SubsystemBase {
  private final CANSparkMax leftLeader;
  private final CANSparkMax leftFollower;
  private final CANSparkMax rightLeader;
  private final CANSparkMax rightFollower;

  private final MotorControllerGroup leftGroup;
  private final MotorControllerGroup rightGroup;

  private AHRS navX;
  private RelativeEncoder leftEncoder;
  private RelativeEncoder rightEncoder;

  private final DifferentialDriveKinematics driveKinematics;
  private final DifferentialDrivePoseEstimator poseEstimator;

  private final DifferentialDrive drive;

  /** Creates a new SUB_DriveTrain. */
  public S_DriveTrain() {
    leftLeader = new CANSparkMax(LEFT_LEADER_ID, MotorType.kBrushless);
    leftFollower = new CANSparkMax(LEFT_FOLLOWER_ID, MotorType.kBrushless);
    rightLeader = new CANSparkMax(RIGHT_LEADER_ID, MotorType.kBrushless);
    rightFollower = new CANSparkMax(RIGHT_FOLLOWER_ID, MotorType.kBrushless);

    leftGroup = new MotorControllerGroup(leftLeader, leftFollower);
    rightGroup = new MotorControllerGroup(rightLeader, rightFollower);

    leftGroup.setInverted(true);
    rightGroup.setInverted(false);

    navX = new AHRS(SPI.Port.kMXP);
    leftEncoder = leftLeader.getEncoder();
    rightEncoder = rightLeader.getEncoder();

    drive = new DifferentialDrive(leftGroup, rightGroup);
    driveKinematics = new DifferentialDriveKinematics(TRACK_WIDTH);
    poseEstimator =
        new DifferentialDrivePoseEstimator(
            driveKinematics, navX.getRotation2d(), 0.0, 0.0, new Pose2d());
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    if (leftSpeed < -1 || leftSpeed > 1 || rightSpeed < -1 || rightSpeed > 1) {
      throw new InvalidParameterException(
          "Left=" + leftSpeed + " Right=" + rightSpeed + " - MUST -1 < Left||Right < 1");
    }
    drive.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /** Updates the pose estimator with the newest measurements */
  public void updateOdometry() {
    poseEstimator.update(
        navX.getRotation2d(),
        posToMeters(leftEncoder.getPosition()),
        posToMeters(rightEncoder.getPosition()));
  }

  /**
   * Returns the current pose of the robot
   *
   * @return A Pose2d current pose of the robot
   */
  public Pose2d getEstimatedPose() {
    return poseEstimator.getEstimatedPosition();
  }

  /**
   * Converts encoder position (in rotations) into meters
   *
   * @param position The current encoder position (in rotations)
   * @return The meters corresponding to the current encoder position in rotations
   */
  public double posToMeters(double position) {
    return Units.inchesToMeters((WHEEL_CIRCUMFERENCE / GEAR_RATIO) * position);
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
}
