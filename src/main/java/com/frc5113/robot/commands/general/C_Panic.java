// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.commands.general;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class C_Panic extends CommandBase {
  /** Creates a new AutoDriveCommand. */
  private final CommandScheduler scheduler;

  /**
   * Panic command for a situation where all commands must immediately be stopped
   *
   * @param scheduler CommandScheduler instance
   */
  public C_Panic(CommandScheduler scheduler) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.scheduler = scheduler;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    scheduler.cancelAll(); // might cancel this command
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
