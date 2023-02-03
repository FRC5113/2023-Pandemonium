// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.DrivetrainConstants.*;

public class S_DriveTrain extends SubsystemBase {
  public CANSparkMax leftLeader = new CANSparkMax(LEFT_LEADER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
  public CANSparkMax leftFollower = new CANSparkMax(LEFT_FOLLOWER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
  public CANSparkMax rightLeader = new CANSparkMax(RIGHT_LEADER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
  public CANSparkMax rightFollower = new CANSparkMax(RIGHT_FOLLOWER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

  private MotorControllerGroup leftGroup = new MotorControllerGroup(leftLeader, leftFollower);
  private MotorControllerGroup rightGroup = new MotorControllerGroup(rightLeader, rightFollower);

  private DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);
  /** Creates a new SUB_DriveTrain. */
  public S_DriveTrain() {

    leftGroup.setInverted(true);
    rightGroup.setInverted(false);

  }

  public void tankDrive(double leftSpeed, double rightSpeed){
    drive.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
