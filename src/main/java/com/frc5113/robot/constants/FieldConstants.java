package com.frc5113.robot.constants;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import java.util.Arrays;
import java.util.List;

public final class FieldConstants {
  public static final double length = Units.inchesToMeters(651.25);
  public static final double width = Units.inchesToMeters(315.5);
  public static final double tapeWidth = Units.inchesToMeters(2.0);
  public static final double aprilTagWidth = Units.inchesToMeters(6.0);

  // The following AprilTags were found by MechanicalAdvantage
  // (https://github.com/Mechanical-Advantage/RobotCode2023/blob/main/src/main/java/org/littletonrobotics/frc2023/FieldConstants.java)
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

  public static final List<AprilTag> AT_LIST =
      Arrays.asList(AT_1, AT_2, AT_3, AT_4, AT_5, AT_6, AT_7, AT_8);
  public static final AprilTagFieldLayout FIELD_LAYOUT =
      new AprilTagFieldLayout(AT_LIST, length, width);
}
