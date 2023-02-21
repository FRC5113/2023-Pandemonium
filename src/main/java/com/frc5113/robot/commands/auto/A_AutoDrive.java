// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.Supplier;

import com.frc5113.robot.subsystems.DriveTrain.S_DriveTrain;

public class A_AutoDrive extends CommandBase {
  /** Creates a new AutoDriveCommand. */
  private final S_DriveTrain drive;

  private final Supplier<Double> rightSpeed;
  private final Supplier<Double> leftSpeed;
  private final double endTime;

  private final Timer timer;

  /**
   * Custom drive command with a timeout
   *
   * @param drive Drivetrain instance
   * @param leftSpeed Supplier for speed on the left side
   * @param rightSpeed Supplier for speed on the right side
   * @param endTime Time (in seconds) to finish at
   */
  public A_AutoDrive(
      S_DriveTrain drive, Supplier<Double> leftSpeed, Supplier<Double> rightSpeed, double endTime) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);

    this.drive = drive;
    this.rightSpeed = rightSpeed;
    this.leftSpeed = leftSpeed;
    this.endTime = endTime;

    timer = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive.tankDrive(leftSpeed.get(), rightSpeed.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.tankDrive(0, 0); // be sure to stop at the end
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.get() >= endTime;
  }
}
