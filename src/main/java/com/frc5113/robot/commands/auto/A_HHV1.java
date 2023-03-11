// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.commands.auto;

import com.frc5113.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class A_HHV1 extends SequentialCommandGroup {
  /** Creates a new A_HHV1. */
  public A_HHV1(DriveTrain drive) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addRequirements(drive);
    Command c1 = new A_AutoDrive(drive, () -> .5, () -> .5, .8);
    Command c2 = new A_AutoDrive(drive, () -> 0.0, () -> 0.0, .5);
    // Command c2 = new WaitCommand(.5);
    Command c3 = new A_AutoDrive(drive, () -> -.5, () -> -.5, 1.2);
    Command c4 = new A_AutoDrive(drive, () -> 0.0, () -> 0.0, .5);
    Command c5 = new A_AutoDrive(drive, () -> .5, () -> .5, 5);

    addCommands(c1, c2, c3, c4, c5);
  }
}
