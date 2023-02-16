package com.frc5113.robot.constants;

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
  public static final double BALANCE_KP = 0.;
  public static final double BALANCE_KI = 0.;
  public static final double BALANCE_KD = 0.;
  public static final double BALANCE_TOLERANCE_P = 0.;
  public static final double BALANCE_TOLERANCE_D = 0.;
}
