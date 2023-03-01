package com.frc5113.robot.commands.drive;

import static com.frc5113.robot.constants.DrivetrainConstants.*;
import static com.frc5113.robot.constants.GeneralConstants.TUNING_MODE;

import com.frc5113.library.utils.tunable.TunableNumber;
import com.frc5113.robot.subsystems.DriveTrain;
import com.frc5113.robot.subsystems.S_Gyro;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

public class P_AutoBalance extends PIDCommand {

  public static final TunableNumber BALANCE_KP =
      new TunableNumber("AutoBalance: P", 0.02, TUNING_MODE);
  public static final TunableNumber BALANCE_KI =
      new TunableNumber("AutoBalance: I", 0.0, TUNING_MODE);
  public static final TunableNumber BALANCE_KD =
      new TunableNumber("AutoBalance: D", 0.0, TUNING_MODE);
  public static final TunableNumber BALANCE_TOLERANCE_P =
      new TunableNumber("AutoBalance: Tolerance", 0, TUNING_MODE);
  public static final TunableNumber BALANCE_TOLERANCE_D =
      new TunableNumber("AutoBalance: Velocity Tolerance", 0, TUNING_MODE);

  public P_AutoBalance(DriveTrain driveTrain, S_Gyro gyro) {
    super(
        new PIDController(BALANCE_KP.get(), BALANCE_KI.get(), BALANCE_KD.get()),
        () -> gyro.getRoll(),
        90.0,
        output -> driveTrain.tankDrive(output, output),
        driveTrain);
    getController().setTolerance(BALANCE_TOLERANCE_P.get(), BALANCE_TOLERANCE_D.get());
  }

  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
