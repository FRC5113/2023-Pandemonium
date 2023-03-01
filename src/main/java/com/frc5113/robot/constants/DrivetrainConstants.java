package com.frc5113.robot.constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.frc5113.robot.enums.RobotVersion;
import com.revrobotics.CANSparkMax.IdleMode;

public class DrivetrainConstants {
  public static final RobotVersion ROBOT_VERSION = RobotVersion.Pandeguardium;

  public static final int LEFT_LEADER_ID_PANDEMONIUM = 12;
  public static final int LEFT_FOLLOWER_ID_PANDEMONIUM = 22;
  public static final int RIGHT_LEADER_ID_PANDEMONIUM = 11;
  public static final int RIGHT_FOLLOWER_ID_PANDEMONIUM = 21;

  public static final IdleMode MOTOR_MODE_PANDEMONIUM = IdleMode.kBrake;

  public static final int LEFT_LEADER_ID_PANDEGUARDIUM = 11;
  public static final int LEFT_FOLLOWER_ID_PANDEGUARDIUM = 21;
  public static final int RIGHT_LEADER_ID_PANDEGUARDIUM = 12;
  public static final int RIGHT_FOLLOWER_ID_PANDEGUARDIUM = 22;

  public static final NeutralMode MOTOR_MODE_PANDEGUARDIUM = NeutralMode.Brake;
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
