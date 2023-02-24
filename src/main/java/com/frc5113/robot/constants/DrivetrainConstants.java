package com.frc5113.robot.constants;

import static com.frc5113.robot.constants.GeneralConstants.TUNING_MODE;

import com.frc5113.library.utils.tunable.TunableNumber;
import com.revrobotics.CANSparkMax.IdleMode;

public class DrivetrainConstants {
  public static final int LEFT_LEADER_ID = 12;
  public static final int LEFT_FOLLOWER_ID = 22;
  public static final int RIGHT_LEADER_ID = 11;
  public static final int RIGHT_FOLLOWER_ID = 21;

  // Odometry stuff
  public static final double TRACK_WIDTH = 0.; // In inches
  public static final double WHEEL_CIRCUMFERENCE = 0.; // In inches
  public static final double GEAR_RATIO = 0.;
  public static final IdleMode MOTOR_MODE = IdleMode.kCoast;

  // Auto Balance stuff
  public static final TunableNumber BALANCE_KP = new TunableNumber("Arm: P", 1, TUNING_MODE);
  public static final TunableNumber BALANCE_KI = new TunableNumber("Arm: I", .01, TUNING_MODE);
  public static final TunableNumber BALANCE_KD = new TunableNumber("Arm: D", .001, TUNING_MODE);
  public static final TunableNumber BALANCE_TOLERANCE_P =
      new TunableNumber("Arm: Tolerance", 1, TUNING_MODE);
  public static final TunableNumber BALANCE_TOLERANCE_D =
      new TunableNumber("Arm: Velocity Tolerance", 1, TUNING_MODE);
}
