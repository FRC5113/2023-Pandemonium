// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// DOCS:
// https://docs.photonvision.org/en/latest/docs/programming/photonlib/getting-target-data.html#getting-apriltag-data-from-a-target

package com.frc5113.robot.subsystems;

import static com.frc5113.robot.constants.FieldConstants.*;
import static com.frc5113.robot.constants.PhotonVisionConstants.*;

import com.frc5113.library.loops.ILooper;
import com.frc5113.library.loops.Loop;
import com.frc5113.library.subsystem.SmartSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.*;

public class S_PhotonVision extends SmartSubsystem {

  private final PhotonCamera camera = new PhotonCamera(CAMERA_NAME);
  private List<PhotonTrackedTarget> targets = new ArrayList<>();
  private PhotonTrackedTarget bestTarget;
  public PhotonPoseEstimator poseEstimator;
  public boolean hasTargets = false;

  /** Creates a new PhotonVision. */
  public S_PhotonVision() {
    poseEstimator =
        new PhotonPoseEstimator(
            FIELD_LAYOUT, PoseStrategy.CLOSEST_TO_REFERENCE_POSE, camera, RELATIVE_CAM_POSITION);
  }

  public void updateBestTarget(PhotonPipelineResult result) {
    if (!result.hasTargets()) {
      bestTarget = null;
      return;
    }
    bestTarget = result.getBestTarget();
  }

  public void updateTargets(PhotonPipelineResult result) {
    if (!result.hasTargets()) {
      targets.clear();
      return;
    }
    targets = result.getTargets();
  }

  public PhotonTrackedTarget getBestTarget() {
    return bestTarget;
  }

  public double getTargetYaw(PhotonTrackedTarget target) {
    return target.getYaw();
  }

  public double getTargetPitch(PhotonTrackedTarget target) {
    return target.getPitch();
  }

  public double getTargetArea(PhotonTrackedTarget target) {
    return target.getArea();
  }

  public int getId(PhotonTrackedTarget target) {
    return target.getFiducialId();
  }

  /**
   * Acts as the PhotonVision equivalent of Limelight::getTx()
   *
   * @return Amount of pixels between center of "screen" and center of bounding box horizontally.
   */
  public double getTx() {
    PhotonTrackedTarget target = getBestTarget();
    List<TargetCorner> corners = target.getDetectedCorners();
    double leftCorner = corners.get(0).x;
    double rightCorner = corners.get(3).x;

    return (leftCorner - rightCorner) / 2 - CENTER_X;
  }
  /**
   * Acts as the PhotonVision equivalent of Limelight::getTy()
   *
   * @return Amount of pixels between center of "screen" and center of bounding box vertically.
   */
  public double getTy() {

    PhotonTrackedTarget target = getBestTarget();
    List<TargetCorner> corners = target.getDetectedCorners();

    double leftCorner = corners.get(0).y;
    double rightCorner = corners.get(3).y;

    return (leftCorner - rightCorner) / 2 - CENTER_Y;
  }

  /**
   * Given a Pose2D previousPose, this returns a new Pose3D based on AprilTags. This requires
   * AprilTags to be in view, so please check if PhotonVision.hasTargets is true, before using.
   *
   * @param prevEstimatedRobotPose The previous estimated robot pose (last pose since new one).
   * @return An estimated robot pose object.
   */
  public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
    poseEstimator.setReferencePose(prevEstimatedRobotPose);
    return poseEstimator.update();
  }

  @Override
  public boolean checkSubsystem() {
    return true;
  }

  @Override
  public void outputTelemetry() {
    if (hasTargets) {
      SmartDashboard.putNumber("Best Target Yaw", getTargetYaw(getBestTarget()));
      SmartDashboard.putNumber("Best Target Pitch", getTargetPitch(getBestTarget()));
      SmartDashboard.putNumber("Best Target ID", getId(getBestTarget()));
      SmartDashboard.putNumber("Best Target Area", getTargetArea(getBestTarget()));
      SmartDashboard.putNumber("FIDUCIAL Id", getId(getBestTarget()));

      SmartDashboard.putNumber("Best Target TX", getTx());
      SmartDashboard.putNumber("Best Target TY", getTy());
    }
  }

  @Override
  public void readPeriodicInputs() {
    var result = camera.getLatestResult();
    updateBestTarget(result);
    updateTargets(result);
    hasTargets = result.hasTargets();
  }

  @Override
  public void registerEnabledLoops(ILooper enabledLooper) {
    enabledLooper.register(
        new Loop() {
          @Override
          public void onLoop(double arg0) {}

          @Override
          public void onStart(double arg0) {
            stop(); // be sure to stop
            zeroSensors();
          }

          @Override
          public void onStop(double arg0) {
            stop(); // be sure to stop
            zeroSensors();
          }
        });
  }

  @Override
  public void stop() {}

  @Override
  public void writePeriodicOutputs() {}

  @Override
  public void zeroSensors() {}
}
