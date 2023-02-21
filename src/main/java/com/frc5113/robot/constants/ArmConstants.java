package com.frc5113.robot.constants;

import static com.frc5113.robot.constants.GeneralConstants.TUNING_MODE;

import com.frc5113.library.utils.tunable.TunableNumber;

public class ArmConstants {
  public static final int RIGHT_FALCON_CAN_ID = 31;
  public static final int LEFT_FALCON_CAN_ID = 32;

  // GoToSetpoint PID
  public static final TunableNumber kP = new TunableNumber("Arm: P", 1, TUNING_MODE);
  public static final TunableNumber kI = new TunableNumber("Arm: I", .01, TUNING_MODE);
  public static final TunableNumber kD = new TunableNumber("Arm: D", .001, TUNING_MODE);
  public static final TunableNumber kTolerance =
      new TunableNumber("Arm: Tolerance", 500, TUNING_MODE);
  public static final TunableNumber kVTolerance =
      new TunableNumber("Arm: Velocity Tolerance", 50, TUNING_MODE);

  public static final int ENCODER_PORT = 1; 
}
