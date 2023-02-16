// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot;

<<<<<<< HEAD

import com.frc5113.robot.commands.auto.Autos;
import com.frc5113.robot.commands.drive.*;
import com.frc5113.robot.commands.auto.Autos;
=======
>>>>>>> d706b3bf2e833105d0c8b9986206315c7d458657
import com.frc5113.robot.commands.C_ResetOdometry;
import com.frc5113.robot.commands.drive.D_TeleopDrive;
import com.frc5113.robot.oi.IOI;
import com.frc5113.robot.oi.JoystickOI;
import com.frc5113.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
<<<<<<< HEAD
=======
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
>>>>>>> d706b3bf2e833105d0c8b9986206315c7d458657
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
  final S_DriveTrain driveTrain = new S_DriveTrain();

  /** General pneumatics controller from which pneumatic components are derived */
  private final S_Pneumatics pneumatics = new S_Pneumatics();

  /** Claw pneumatic component (derived from pneumatics) */
  private final S_Claw claw = pneumatics.getClaw();

  /** Arm/truss subsystem */
  private final S_Arm arm = new S_Arm();

  // Operator interface
  private final IOI controller1 = new JoystickOI();

  private final CommandJoystick leftJoy =
      new CommandJoystick(OperatorInterfaceConstants.LEFT_JOYSTICK_PORT);
  private final CommandJoystick rightJoy =
      new CommandJoystick(OperatorInterfaceConstants.RIGHT_JOYSTICK_PORT);

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
<<<<<<< HEAD
    driveTrain.setDefaultCommand(new D_TeleopDrive(driveTrain, controller1.tankL(), controller1.tankR()));
=======
    driveTrain.setDefaultCommand(new D_TeleopDrive(driveTrain, leftJoy::getY, leftJoy::getY));
    int joyOne = 1;

    driverController.a().whileTrue(new C_ResetOdometry(driveTrain));
    leftJoy.button(joyOne).whileTrue(new C_ResetOdometry(driveTrain));
>>>>>>> d706b3bf2e833105d0c8b9986206315c7d458657
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return Autos.driveBackward(driveTrain); // Do Nothing: new InstantCommand(() -> {});
  }


  public void teleopInit() {}

<<<<<<< HEAD
  public void testPeriodic() {}
=======
>>>>>>> d706b3bf2e833105d0c8b9986206315c7d458657
  public void robotPeriodic() {
    driveTrain.updateOdometry();
  }
}
