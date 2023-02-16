// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot;

import com.frc5113.robot.commands.auto.Autos;
import com.frc5113.robot.commands.drive.*;
import com.frc5113.robot.commands.drive.D_TeleopDrive;
import com.frc5113.robot.oi.IOI;
import com.frc5113.robot.oi.JoystickOI;
import com.frc5113.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
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
  private final S_DriveTrain driveTrain = new S_DriveTrain();

  /** General pneumatics controller from which pneumatic components are derived */
  private final S_Pneumatics pneumatics = new S_Pneumatics();
  public final S_DriveTrain driveTrain = new S_DriveTrain();
  public final S_PhotonVision photonVision = new S_PhotonVision();

  /** Claw pneumatic component (derived from pneumatics) */
  private final S_Claw claw = pneumatics.getClaw();

  /** Arm/truss subsystem */
  private final S_Arm arm = new S_Arm();

  // Operator interface
  private final IOI controller1 = new JoystickOI();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
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
        new D_TeleopDrive(driveTrain, controller1.tankL(), controller1.tankR()));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return Autos.driveBackward(driveTrain); // Do Nothing: new InstantCommand(() -> {});
  }

  public void robotPeriodic() {}

  public void teleopInit() {}

  public void testPeriodic() {}
}
