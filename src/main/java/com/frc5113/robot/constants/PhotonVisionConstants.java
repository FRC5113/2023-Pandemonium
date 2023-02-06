package com.frc5113.robot.constants;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

public final class PhotonVisionConstants {
  public static final double CENTER_X = 1280 / 2;
  public static final double CENTER_Y = 720 / 2;
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
