package com.frc5113.robot.constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.frc5113.robot.enums.RobotVersion;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

public class DrivetrainConstants {
  public static final RobotVersion ROBOT_VERSION = RobotVersion.Pandemonium;

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

  // public static final double TRACK_WIDTH = Units.inchesToMeters(30.7); // inches
  // public static final double WHEEL_DIAMETER = 6.0; // inches
  // public static final double WHEEL_CIRCUMFERENCE = Units.inchesToMeters(WHEEL_DIAMETER *
  // Math.PI); // inches
  // public static final double GEAR_RATIO = 11.5;

  // public static final double kMaxSpeedMetersPerSecond = .5;
  // public static final double kMaxAccelerationMetersPerSecondSquared = .25;
  // public static final double kRamseteB = 2.0;
  // public static final double kRamseteZeta = 0.7;
  // public static final double kPDriveVel = 2.9388;
  // public static final double kDDriveVel = 0.;

  public static final double TRACK_WIDTH = Units.inchesToMeters(24.6); // inches
  public static final double WHEEL_DIAMETER = 6.0; // inches
  public static final double WHEEL_CIRCUMFERENCE =
      Units.inchesToMeters(WHEEL_DIAMETER * Math.PI); // inches
  public static final double GEAR_RATIO = 12.75;

  public static final double kMaxSpeedMetersPerSecond = 1.0;
  public static final double kMaxAccelerationMetersPerSecondSquared = 0.5;
  public static final double kRamseteB = 2.0;
  public static final double kRamseteZeta = 0.7;
  public static final double kPDriveVel = 1.0822;
  public static final double kDDriveVel = 0.0;

  public static final double ksVolts = 0.13119;
  public static final double kvVoltSecondsPerMeter = 3.4317;
  public static final double kaVoltSecondsSquaredPerMeter = 0.35008;
  public static final DifferentialDriveKinematics kDriveKinematics =
      new DifferentialDriveKinematics(TRACK_WIDTH);
}
