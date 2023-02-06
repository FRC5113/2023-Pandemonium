// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.security.InvalidParameterException;

import static frc.robot.constants.DrivetrainConstants.*;

public class S_DriveTrain extends SubsystemBase {
  private final CANSparkMax leftLeader;
  private final CANSparkMax leftFollower;
  private final CANSparkMax rightLeader;
  private final CANSparkMax rightFollower;

  private final MotorControllerGroup leftGroup;
  private final MotorControllerGroup rightGroup;

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

    drive = new DifferentialDrive(leftGroup, rightGroup);
  }

  public void tankDrive(double leftSpeed, double rightSpeed){
    if (leftSpeed < -1 || leftSpeed > 1 || rightSpeed < -1 || rightSpeed > 1) {
      throw new InvalidParameterException("Left=" + leftSpeed + " Right=" + rightSpeed + " - MUST -1 < Left||Right < 1");
    }
    drive.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
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
