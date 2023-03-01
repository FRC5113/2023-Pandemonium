package com.frc5113.robot.commands.drive;

import static com.frc5113.robot.constants.DrivetrainConstants.*;

import com.frc5113.robot.subsystems.S_DriveTrain;
import com.frc5113.robot.subsystems.S_Gyro;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

public class P_AutoBalance extends PIDCommand {

  public P_AutoBalance(S_DriveTrain driveTrain, S_Gyro gyro) {
    super(
<<<<<<< HEAD
<<<<<<< HEAD
        new PIDController(BALANCE_KP.get(), BALANCE_KI.get(), BALANCE_KD.get()),
=======
        new PIDController(BALANCE_KP, BALANCE_KI, BALANCE_KD),
>>>>>>> 41436d7 (add auto balance)
=======
        new PIDController(BALANCE_KP.get(), BALANCE_KI.get(), BALANCE_KD.get()),
>>>>>>> 88faa89 (implemented tunable numbers)
        () -> gyro.getRoll(),
        0.0,
        output -> driveTrain.tankDrive(output, output),
        driveTrain);
<<<<<<< HEAD
<<<<<<< HEAD
    getController().setTolerance(BALANCE_TOLERANCE_P.get(), BALANCE_TOLERANCE_D.get());
=======
    getController().setTolerance(BALANCE_TOLERANCE_P, BALANCE_TOLERANCE_D);
>>>>>>> 41436d7 (add auto balance)
=======
    getController().setTolerance(BALANCE_TOLERANCE_P.get(), BALANCE_TOLERANCE_D.get());
>>>>>>> 88faa89 (implemented tunable numbers)
  }

  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
