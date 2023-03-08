package com.frc5113.robot.subsystems;

import com.frc5113.library.subsystem.SmartSubsystem;
import com.frc5113.robot.primative.DrivetrainEncoders;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public abstract class DriveTrain extends SmartSubsystem {
  public abstract void tankDrive(double leftSpeedRaw, double RightSpeedRaw);

  public abstract void arcadeDrive(double arcadeSpeedRaw, double arcadeTurnRaw);

  public abstract void updatePositions();

  public abstract DifferentialDrive getDifferentialDrive();

  public abstract MotorControllerGroup getLeftMotorGroup();

  public abstract MotorControllerGroup getRightMotorGroup();

  public abstract DrivetrainEncoders getEncoders();

  public abstract Pose2d getPose();

  public abstract DifferentialDriveWheelSpeeds getWheelSpeeds();

  public abstract void updatePose(Rotation2d gyroAngle);

  public abstract void tankDriveVolts(double leftVolts, double rightVolts);

  public abstract void resetOdometry(Rotation2d gyroAngle, Pose2d pose);
}
