package com.frc5113.robot.commands.drive;

import com.frc5113.robot.subsystems.DriveTrain;
import com.frc5113.robot.subsystems.S_Gyro;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.PIDCommand;

public class P_AutoBalance extends PIDCommand {

  public P_AutoBalance(DriveTrain driveTrain, S_Gyro gyro) {
    super(
        new PIDController(
            Preferences.getDouble("autobalance/kp", 0.0),
            Preferences.getDouble("autobalance/ki", 0.0),
            Preferences.getDouble("autobalance/kd", 0.0)),
        () -> gyro.getRoll(),
        0.0,
        output -> driveTrain.tankDrive(output, output),
        driveTrain);
    getController()
        .setTolerance(
            Preferences.getDouble("autobalance/tolerancep", 0.0),
            Preferences.getDouble("autobalance/toleranced", 0.0));
  }

  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
