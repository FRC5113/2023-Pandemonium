package com.frc5113.robot.commands.drive;

import com.frc5113.robot.subsystems.S_DriveTrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

import static com.frc5113.robot.constants.DrivetrainConstants.*;

public class D_Balance extends PIDCommand {
  private S_DriveTrain driveTrain;

  public D_Balance(S_DriveTrain driveTrain) {
    super(
        new PIDController(BALANCE_KP, BALANCE_KI, BALANCE_KD),
        () -> driveTrain.roll(ROLL_DEADBAND, ROLL_OFFSET),
        0.0,
        output -> driveTrain.tankDrive(-0.25 * output, 0.25 * output),
        driveTrain);
  }

  @Override
  public boolean isFinished() {
    System.out.println(driveTrain.roll());
    return true; //change
  }
}