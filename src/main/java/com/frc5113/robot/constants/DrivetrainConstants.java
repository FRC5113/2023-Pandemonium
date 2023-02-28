package com.frc5113.robot.constants;

import com.revrobotics.CANSparkMax.IdleMode;

public class DrivetrainConstants {
  public static final int LEFT_LEADER_ID = 12;
  public static final int LEFT_FOLLOWER_ID = 22;
  public static final int RIGHT_LEADER_ID = 11;
  public static final int RIGHT_FOLLOWER_ID = 21;

  public static final IdleMode MOTOR_MODE = IdleMode.kCoast;

  public static final double MAX_VELOCITY = 10.0; // change me
  public static final double WHEEL_RADIUS = 0.0; // change me
  public static final double TRACK_WIDTH = 0.0; // change me
  public static final double MAX_ACCELERATION = 1; // change me
  public static final double DEAD_BAND = 0.1;
  public static final double MAX_JERK = 0.1; // change me
}
