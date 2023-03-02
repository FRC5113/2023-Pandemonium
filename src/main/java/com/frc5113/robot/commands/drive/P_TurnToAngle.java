package com.frc5113.robot.commands.drive;

import static com.frc5113.robot.constants.DrivetrainConstants.*;
import static com.frc5113.robot.constants.GeneralConstants.TUNING_MODE;

import com.frc5113.library.utils.tunable.TunableNumber;
import com.frc5113.robot.subsystems.DriveTrain;
import com.frc5113.robot.subsystems.S_Gyro;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

public class P_TurnToAngle extends PIDCommand {

  public static final TunableNumber TURNTOANGLE_KP =
      new TunableNumber("TurnToAngle: P", 0.02, TUNING_MODE);
  public static final TunableNumber TURNTOANGLE_KI =
      new TunableNumber("TurnToAngle: I", 0.0, TUNING_MODE);
  public static final TunableNumber TURNTOANGLE_KD =
      new TunableNumber("TurnToAngle: D", 0.0, TUNING_MODE);
  public static final TunableNumber TURNTOANGLE_TOLERANCE_P =
      new TunableNumber("TurnToAngle: Tolerance", 0.0, TUNING_MODE);
  public static final TunableNumber TURNTOANGLE_TOLERANCE_D =
      new TunableNumber("TurnToAngle: Velocity Tolerance", 0.0, TUNING_MODE);

  public P_TurnToAngle(DriveTrain driveTrain, S_Gyro gyro, double angle) {
    super(
        new PIDController(TURNTOANGLE_KP.get(), TURNTOANGLE_KI.get(), TURNTOANGLE_KD.get()),
        () -> gyro.getAngle(),
        angle,
        output -> driveTrain.tankDrive(output, -output),
        driveTrain);
    getController().setTolerance(TURNTOANGLE_TOLERANCE_P.get(), TURNTOANGLE_TOLERANCE_D.get());
  }

  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
