// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.commands.auto;

import com.frc5113.robot.subsystems.drive.DriveTrain;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class A_NJTAB extends SequentialCommandGroup {
  /** Creates a new A_NJTAB. */
  public A_NJTAB(DriveTrain drive) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addRequirements(drive);
    Command n1 = new A_AutoDrive(drive, () -> -0.5, () -> -0.5, .5);
    Command n2 = new A_AutoDrive(drive, () -> 0.0, () -> 0.0, .25);
    Command n3 = new A_AutoDrive(drive, () -> 0.5, () -> 0.5, .5);
    Command n4 = new A_AutoDrive(drive, () -> 0.0, () -> 0.0, .25);
    Command n5 = new A_AutoDrive(drive, () -> -0.5, () -> -0.5, 4);
    addCommands(n1, n2, n3, n4, n5);
  }
}
