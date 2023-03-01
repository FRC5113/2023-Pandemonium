package com.frc5113.robot.commands.drive;

import static com.frc5113.robot.constants.DrivetrainConstants.*;

import com.frc5113.robot.subsystems.S_DriveTrain;
import com.frc5113.robot.subsystems.S_Gyro;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

public class P_AutoBalance extends PIDCommand {

  public P_AutoBalance(S_DriveTrain driveTrain, S_Gyro gyro) {
    super(
        new PIDController(BALANCE_KP, BALANCE_KI, BALANCE_KD),
        () -> gyro.getRoll(),
        0.0,
        output -> driveTrain.tankDrive(output, output),
        driveTrain);
    getController().setTolerance(BALANCE_TOLERANCE_P, BALANCE_TOLERANCE_D);
  }

  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
