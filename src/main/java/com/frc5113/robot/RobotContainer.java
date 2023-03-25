// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot;

import static com.frc5113.robot.constants.DrivetrainConstants.ROBOT_VERSION;
import static com.frc5113.robot.constants.GeneralConstants.LOOP_DT;

import com.frc5113.library.loops.Looper;
import com.frc5113.library.loops.SubsystemManager;
import com.frc5113.robot.commands.auto.A_NJTAB;
import com.frc5113.robot.commands.claw.C_ActuateClaw;
import com.frc5113.robot.commands.drive.*;
import com.frc5113.robot.commands.photonvision.*;
import com.frc5113.robot.oi.CMPOI;
import com.frc5113.robot.subsystems.*;
import com.frc5113.robot.subsystems.drive.DriveTrain;
import com.frc5113.robot.subsystems.drive.S_DriveTrainPandeguardium;
import com.frc5113.robot.subsystems.drive.S_DriveTrainPandemonium;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

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
  // public final S_PhotonVision photonVision = new S_PhotonVision();

  /** Claw pneumatic component (derived from pneumatics) */
  private final S_Claw claw = pneumatics.getClaw();

  /** Arm/truss subsystem */
  private final S_Arm arm = new S_Arm();

  /** Gyro subsystem */
  private final S_Gyro gyro = new S_Gyro();

  /** Robot Logging + GenOps */
  private final S_Robot robot = new S_Robot();

  // Operator interface
  private final CMPOI oi = new CMPOI();
  // private final XboxOI oi = new XboxOI();

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
        driveTrain = new S_DriveTrainPandemonium();
        break;
      case Pandeguardium:
        driveTrain = new S_DriveTrainPandeguardium();
        break;
      default:
        driveTrain = new S_DriveTrainPandemonium();
        break;
    }

    // Configure the trigger bindings
    configureBindings();

    // Register loops and subsystems to the manager
    manager.registerEnabledLoops(enabledLoop);
    manager.registerDisabledLoops(disabledLoop);
    manager.setSubsystems(driveTrain, claw, arm, pneumatics, gyro, robot);
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
    // driveTrain.setDefaultCommand(
    //     new D_TeleopDriveArcade(driveTrain, oi.arcadeSpeed(), oi.arcadeTurn()));
    driveTrain.setDefaultCommand(
        new D_TeleopDrive(driveTrain, oi.tankL(), oi.tankR(), oi.slowMode())); // Joysticks

    oi.clawButton().whileTrue(new C_ActuateClaw(claw)); // B
    // oi.armGroundButton() // Y
    //     .whileTrue(
    //         new InstantCommand(
    //             () -> {
    //               arm.zeroSensors();
    //             }));
    oi.armDropButton()
        .whileTrue(
            new InstantCommand(
                () -> {
                  driveTrain.setAllBrake();
                }));
    // oi.armGroundButton().onTrue(new C_GoToSetpoint(arm, ArmPosition.Ground)); // Y
    // oi.armDropButton().onTrue(new C_GoToSetpoint(arm, ArmPosition.Drop)); //  X
    arm.setDefaultCommand(
        new RepeatCommand(
            new InstantCommand(
                () -> {
                  double left = oi.leftTrigger().get() * .3;
                  double right = oi.rightTrigger().get() * .3;
                  if (right > left) {
                    arm.set(right * .8);
                  } else {
                    arm.set(-left);
                  }
                },
                arm)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new A_NJTAB(driveTrain);
    // return Autos.drive(driveTrain, .5, 2); // Do Nothing: new InstantCommand(() -> {});
  }

  public void robotInit() {}

  public void robotPeriodic() {
    manager.outputToSmartDashboard();
  }

  public void autoInit() {
    disabledLoop.stop();
    manager.stop();
    enabledLoop.start();
    driveTrain.setAllCoast();
  }

  public void teleopInit() {
    disabledLoop.stop();
    manager.stop();
    enabledLoop.start();
    driveTrain.setAllCoast();
  }

  public void disabledInit() {
    enabledLoop.stop();
    disabledLoop.start();
    driveTrain.setAllBrake();
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
}
