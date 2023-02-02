// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import java.util.*;
import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final class PhotonVisionConstants{
    public static final double CENTER_X = 1280/2;
    public static final double CENTER_Y = 720/2;
    public static final String CAMERA_NAME = "camera";
    public static final double kP = 0.0;
    public static final double kI = 0.0;
    public static final double kD = 0.0;
    public static final Transform3d RELATIVE_CAM_POSITION = 
    new Transform3d(
            new Translation3d(0.5, 0.0, 0.5),
            new Rotation3d(
                    0, 0,
                    0)); // Cam mounted facing forward, half a meter forward of center, half a meter up
  }

  public static class DrivetrainConstants{
    public static final int LEFT_LEADER_ID = 11;
    public static final int LEFT_FOLLOWER_ID = 22;
    public static final int RIGHT_LEADER_ID = 12;
    public static final int RIGHT_FOLLOWER_ID = 21;
    
    public static final double TRACK_WIDTH = 0; // INCHES
    public static final double WHEEL_RADIUS = 0; // INCHES
    public static final double WHEEL_CIRCUMFERENCE = (2 * Math.PI * WHEEL_RADIUS); // INCHES
    public static final double GEAR_RATIO = 0;


  }

  public static final class FieldConstants{
    public static final double length = Units.inchesToMeters(651.25);
    public static final double width = Units.inchesToMeters(315.5);
    public static final double tapeWidth = Units.inchesToMeters(2.0);
    public static final double aprilTagWidth = Units.inchesToMeters(6.0);

    // The following AprilTags were found by MechanicalAdvantage (https://github.com/Mechanical-Advantage/RobotCode2023/blob/main/src/main/java/org/littletonrobotics/frc2023/FieldConstants.java)
    static final AprilTag AT_1 =
    new AprilTag(
      1,
      new Pose3d(
          Units.inchesToMeters(610.77),
          Units.inchesToMeters(42.19),
          Units.inchesToMeters(18.22),
          new Rotation3d(0.0, 0.0, Math.PI)));

    static final AprilTag AT_2 =
    new AprilTag(
      2,
      new Pose3d(
          Units.inchesToMeters(610.77),
          Units.inchesToMeters(108.19),
          Units.inchesToMeters(18.22),
          new Rotation3d(0.0, 0.0, Math.PI)));
      
    static final AprilTag AT_3 =
    new AprilTag(
      3,
      new Pose3d(
          Units.inchesToMeters(610.77),
          Units.inchesToMeters(174.19), // FIRST's diagram has a typo (it says 147.19)
          Units.inchesToMeters(18.22),
          new Rotation3d(0.0, 0.0, Math.PI)));

    static final AprilTag AT_4 =
    new AprilTag(
      4,
      new Pose3d(
          Units.inchesToMeters(636.96),
          Units.inchesToMeters(265.74),
          Units.inchesToMeters(27.38),
          new Rotation3d(0.0, 0.0, Math.PI)));

    static final AprilTag AT_5 =
    new AprilTag(
      5,
      new Pose3d(
          Units.inchesToMeters(14.25),
          Units.inchesToMeters(265.74),
          Units.inchesToMeters(27.38),
          new Rotation3d()));

    static final AprilTag AT_6 =
    new AprilTag(
      6,
      new Pose3d(
          Units.inchesToMeters(40.45),
          Units.inchesToMeters(174.19), // FIRST's diagram has a typo (it says 147.19)
          Units.inchesToMeters(18.22),
          new Rotation3d()));

    static final AprilTag AT_7 =
    new AprilTag(
      7,
      new Pose3d(
          Units.inchesToMeters(40.45),
          Units.inchesToMeters(108.19),
          Units.inchesToMeters(18.22),
          new Rotation3d()));

    static final AprilTag AT_8 =
    new AprilTag(
      8,
      new Pose3d(
          Units.inchesToMeters(40.45),
          Units.inchesToMeters(42.19),
          Units.inchesToMeters(18.22),
          new Rotation3d()));

    public static final List<AprilTag> AT_LIST = Arrays.asList(AT_1, AT_2, AT_3, AT_4, AT_5, AT_6, AT_7, AT_8);
    public static final AprilTagFieldLayout FIELD_LAYOUT = new AprilTagFieldLayout(AT_LIST, length, width);

  }
}
