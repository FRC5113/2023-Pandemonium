package com.frc5113.robot.constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.frc5113.robot.enums.RobotVersion;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

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

  public static final double TRACK_WIDTH = 30.7; // inches
  public static final double WHEEL_DIAMETER = 6.0; // inches
  public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI; // inches
  public static final double GEAR_RATIO = 11.5;

  public static final double kMaxSpeedMetersPerSecond = 3;
  public static final double kMaxAccelerationMetersPerSecondSquared = 1;
  public static final double kRamseteB = 2.0;
  public static final double kRamseteZeta = 0.7;
  public static final double kPDriveVel = 1.3837;

  public static final double ksVolts = 0.10284;
  public static final double kvVoltSecondsPerMeter = 1.2526;
  public static final double kaVoltSecondsSquaredPerMeter = 0.097782;
  public static final DifferentialDriveKinematics kDriveKinematics =
      new DifferentialDriveKinematics(TRACK_WIDTH);
}
