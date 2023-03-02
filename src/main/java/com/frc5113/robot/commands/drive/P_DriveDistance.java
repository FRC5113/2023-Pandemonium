package com.frc5113.robot.commands.drive;

import static com.frc5113.robot.constants.DrivetrainConstants.*;
import static com.frc5113.robot.constants.GeneralConstants.TUNING_MODE;

import com.frc5113.library.utils.tunable.TunableNumber;
import com.frc5113.robot.subsystems.DriveTrain;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

public class P_DriveDistance extends PIDCommand {

  public static final TunableNumber DRIVEDISTANCE_KP =
      new TunableNumber("DriveDistance: P", -0.02, TUNING_MODE);
  public static final TunableNumber DRIVEDISTANCE_KI =
      new TunableNumber("DriveDistance: I", 0.0, TUNING_MODE);
  public static final TunableNumber DRIVEDISTANCE_KD =
      new TunableNumber("DriveDistance: D", 0.0, TUNING_MODE);
  public static final TunableNumber DRIVEDISTANCE_TOLERANCE_P =
      new TunableNumber("DriveDistance: Tolerance", 0, TUNING_MODE);
  public static final TunableNumber DRIVEDISTANCE_TOLERANCE_D =
      new TunableNumber("DriveDistance: Velocity Tolerance", 0, TUNING_MODE);

  // distance is in rotations for the time being
  public P_DriveDistance(DriveTrain driveTrain, double distance) {
    super(
        new PIDController(DRIVEDISTANCE_KP.get(), DRIVEDISTANCE_KI.get(), DRIVEDISTANCE_KD.get()),
        () -> driveTrain.getEncoders().getLeftLeader(),
        distance,
        output -> driveTrain.tankDrive(output, output),
        driveTrain);
    getController().setTolerance(DRIVEDISTANCE_TOLERANCE_P.get(), DRIVEDISTANCE_TOLERANCE_D.get());

    driveTrain.zeroSensors();
  }

  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
