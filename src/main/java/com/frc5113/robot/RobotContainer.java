// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot;

import static com.frc5113.robot.constants.DrivetrainConstants.*;
import static com.frc5113.robot.constants.GeneralConstants.LOOP_DT;

import com.frc5113.library.loops.Looper;
import com.frc5113.library.loops.SubsystemManager;
import com.frc5113.robot.commands.drive.*;
import com.frc5113.robot.commands.photonvision.*;
import com.frc5113.robot.oi.XboxOI;
import com.frc5113.robot.subsystems.*;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.io.IOException;
import java.nio.file.Path;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // Robot subsystems
  /** Neo Drivetrain responsible for robot movement (Subsystem) */
  private final DriveTrain driveTrain;

  /** General pneumatics controller from which pneumatic components are derived */
  private final S_Pneumatics pneumatics = new S_Pneumatics();

  /** PhotonLib wrapper for defining pose estimation and targeting utils. */
  public final S_PhotonVision photonVision = new S_PhotonVision();

  /** Claw pneumatic component (derived from pneumatics) */
  private final S_Claw claw = pneumatics.getClaw();

  /** Arm/truss subsystem */
  private final S_Arm arm = new S_Arm();

  /** Gyro subsystem */
  private final S_Gyro gyro = new S_Gyro();

  // Operator interface
  private final XboxOI controller1 = new XboxOI();

  // subsystem manager
  private final Looper enabledLoop =
      new Looper(LOOP_DT); // Loop for when robot is enabled (auto, teleop)
  private final Looper disabledLoop =
      new Looper(LOOP_DT); // Loop for when robot is disabled (disabled)
  private final SubsystemManager manager = new SubsystemManager();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    switch (ROBOT_VERSION) {
      case Pandemonium:
        driveTrain = new S_DriveTrainPandemonium(gyro.getRotation2d());
        break;
      case Pandeguardium:
        driveTrain = new S_DriveTrainPandeguardium(gyro.getRotation2d());
        break;
      default:
        driveTrain = new S_DriveTrainPandemonium(gyro.getRotation2d());
        break;
    }

    // Configure the trigger bindings
    configureBindings();

    // Register loops and subsystems to the manager
    manager.registerEnabledLoops(enabledLoop);
    manager.registerDisabledLoops(disabledLoop);
    manager.setSubsystems(driveTrain, claw, arm, pneumatics, gyro, photonVision);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    driveTrain.setDefaultCommand(
        new D_TeleopDriveArcade(driveTrain, controller1.arcadeSpeed(), controller1.arcadeTurn()));
    controller1
        .button1()
        .onTrue(
            new InstantCommand(() -> driveTrain.resetOdometry(new Rotation2d(0), new Pose2d())));
    controller1.button2().onTrue(new InstantCommand(() -> gyro.zeroSensors()));
    controller1.button3().onTrue(new InstantCommand(() -> driveTrain.zeroSensors()));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    gyro.zeroSensors();
    // Create a voltage constraint to ensure we don't accelerate too fast
    var autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(
                ksVolts, kvVoltSecondsPerMeter, kaVoltSecondsSquaredPerMeter),
            kDriveKinematics,
            10.0);

    // Create config for trajectory
    TrajectoryConfig config =
        new TrajectoryConfig(kMaxSpeedMetersPerSecond, kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(kDriveKinematics)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint)
            .setReversed(true);

    // An example trajectory to follow.  All units in meters.
    // Trajectory exampleTrajectory =
    //     TrajectoryGenerator.generateTrajectory(
    //         // Start at the origin facing the +X direction
    //         new Pose2d(0, 0, new Rotation2d(0)),
    //         // Pass through these two interior waypoints, making an 's' curve path
    //         List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
    //         // End 3 meters straight ahead of where we started, facing forward
    //         new Pose2d(3, 0, new Rotation2d(0)),
    //         // Pass config
    //         config);

    // Trajectory forwardTrajectory =
    //     TrajectoryGenerator.generateTrajectory(
    //         new Pose2d(0, 0, new Rotation2d(0)),
    //         List.of(new Translation2d(1, 0)),
    //         new Pose2d(0, 0, new Rotation2d(0)),
    //         config);

    Trajectory t = getTrajectory("paths/output/Auto1.wpilib.json");

    RamseteCommand ramseteCommand =
        new RamseteCommand(
            t, // forwardTrajectory
            driveTrain::getPose,
            new RamseteController(kRamseteB, kRamseteZeta),
            new SimpleMotorFeedforward(
                ksVolts, kvVoltSecondsPerMeter, kaVoltSecondsSquaredPerMeter),
            kDriveKinematics,
            driveTrain::getWheelSpeeds,
            new PIDController(kPDriveVel, 0, kDDriveVel),
            new PIDController(kPDriveVel, 0, kDDriveVel),
            // RamseteCommand passes volts to the callback
            driveTrain::tankDriveVolts,
            driveTrain);

    // Reset odometry to the starting pose of the trajectory.
    driveTrain.resetOdometry(gyro.getRotation2d(), t.getInitialPose());
    // Run path following command, then stop at the end.
    return ramseteCommand.andThen(() -> driveTrain.tankDriveVolts(0, 0));
  }

  public void robotInit() {}

  public void robotPeriodic() {
    manager.outputToSmartDashboard();
    driveTrain.updatePose(gyro.getRotation2d());
    SmartDashboard.putNumber("Gyro Angle:", gyro.getRotation2d().getDegrees());
  }

  public void autoInit() {
    disabledLoop.stop();
    manager.stop();
    enabledLoop.start();
  }

  public void teleopInit() {
    disabledLoop.stop();
    manager.stop();
    enabledLoop.start();
  }

  public void disabledInit() {
    enabledLoop.stop();
    disabledLoop.start();
  }

  public void testInit() {
    System.out.println("Starting check systems");

    disabledLoop.stop();
    enabledLoop.start();

    if (manager.checkSubsystems()) {
      System.out.println("ALL SYSTEMS PASSED");
    } else {
      System.out.println("CHECK ABOVE OUTPUT SOME SYSTEMS FAILED!!!");
    }
  }

  public void testPeriodic() {}

  public Trajectory getTrajectory(String weaverFile) {
    Trajectory trajectory = new Trajectory();

    try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(weaverFile);
      trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory: " + weaverFile, ex.getStackTrace());
    }

    return trajectory;
  }
}
