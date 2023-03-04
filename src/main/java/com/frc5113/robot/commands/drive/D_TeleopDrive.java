// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.commands.drive;

import com.frc5113.robot.subsystems.drive.DriveTrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.Supplier;

public class D_TeleopDrive extends CommandBase {

  private final DriveTrain driveTrain;
  private final Supplier<Double> leftSpeed;
  private final Supplier<Double> rightSpeed;

  /** Creates a new DEF_DriveTrain. */
  public D_TeleopDrive(
      DriveTrain driveTrain, Supplier<Double> leftSpeed, Supplier<Double> rightSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveTrain);

    this.leftSpeed = leftSpeed;
    this.rightSpeed = rightSpeed;
    this.driveTrain = driveTrain;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveTrain.tankDrive(leftSpeed.get(), rightSpeed.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
