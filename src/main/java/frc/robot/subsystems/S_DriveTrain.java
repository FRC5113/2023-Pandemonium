// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import static frc.robot.Constants.DrivetrainConstants.*;

import java.util.Optional;

public class S_DriveTrain extends SubsystemBase {
  private CANSparkMax leftLeader = new CANSparkMax(LEFT_LEADER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
  private CANSparkMax leftFollower = new CANSparkMax(LEFT_FOLLOWER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
  private CANSparkMax rightLeader = new CANSparkMax(RIGHT_LEADER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
  private CANSparkMax rightFollower = new CANSparkMax(RIGHT_FOLLOWER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

  private AHRS navX = new AHRS(SPI.Port.kMXP);
  private RelativeEncoder leftEncoder = leftLeader.getEncoder(); // Replace with correct DIO ports
  private RelativeEncoder rightEncoder = rightLeader.getEncoder(); // Replace with correct DIO ports

  private MotorControllerGroup leftGroup = new MotorControllerGroup(leftLeader, leftFollower);
  private MotorControllerGroup rightGroup = new MotorControllerGroup(rightLeader, rightFollower);

  private DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);
  private final DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(TRACK_WIDTH);
  private final DifferentialDrivePoseEstimator poseEstimator =  new DifferentialDrivePoseEstimator(
          driveKinematics, navX.getRotation2d(), 0.0, 0.0, new Pose2d());

  /** Creates a new SUB_DriveTrain. */
  public S_DriveTrain() {

    leftGroup.setInverted(true);
    rightGroup.setInverted(false);

  }

  public void tankDrive(double leftSpeed, double rightSpeed){
    drive.tankDrive(leftSpeed, rightSpeed);
  }

  public void updateOdometry(){
    poseEstimator.update(navX.getRotation2d(), posToMeters(leftEncoder.getPosition()), posToMeters(rightEncoder.getPosition()));
  }

  public Pose2d getEstimatedPose(){
    return poseEstimator.getEstimatedPosition();
  }

  public double posToMeters(double position){
    return Units.inchesToMeters((WHEEL_CIRCUMFERENCE/GEAR_RATIO) * position);
  }

  public void addVisionMeasurement(Optional<Pair<Pose3d, Double>> result){
    if (result.isPresent()){
      Pair<Pose3d, Double> visionResult = result.get();
      poseEstimator.addVisionMeasurement(visionResult.getFirst().toPose2d(), visionResult.getSecond());
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
