// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.commands.drive;

import com.frc5113.robot.subsystems.drive.DriveTrain;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.Supplier;

public class D_TeleopDrive extends CommandBase {

  private final DriveTrain driveTrain;
  private final Supplier<Double> leftSpeed;
  private final Supplier<Double> rightSpeed;
  private final Supplier<Boolean> slowMode;
  // private final Curve cubicCurve;
  private final SlewRateLimiter slewLeft;
  private final SlewRateLimiter slewRight;

  /** Creates a new DEF_DriveTrain. */
  public D_TeleopDrive(
      DriveTrain driveTrain,
      Supplier<Double> leftSpeed,
      Supplier<Double> rightSpeed,
      Supplier<Boolean> slowMode) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveTrain);

    this.leftSpeed = leftSpeed;
    this.rightSpeed = rightSpeed;
    this.driveTrain = driveTrain;
    this.slowMode = slowMode;
    this.slewLeft = new SlewRateLimiter(1.75);
    this.slewRight = new SlewRateLimiter(1.75);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // System.out.print(leftSpeed.get() + " - " + rightSpeed.get());
    if (slowMode.get()) {
      driveTrain.tankDrive(leftSpeed.get() * .5, rightSpeed.get() * .5);
    } else {
      driveTrain.tankDrive(
          slewLeft.calculate(leftSpeed.get()), slewRight.calculate(rightSpeed.get()));
    }
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
