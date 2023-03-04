// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.commands.auto;

import com.frc5113.robot.subsystems.drive.DriveTrain;
import edu.wpi.first.wpilibj2.command.Command;

public final class Autos {
  /** Example static factory for an autonomous command. */
  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }

  public static Command driveBackward(DriveTrain drive) {
    return driveBackward(drive, 0.3);
  }

  public static Command driveBackward(DriveTrain drive, double power) {
    return drive(drive, power, 2);
  }

  public static Command drive(DriveTrain drive, double power, double endTime) {
    return driveBackward(drive, power, power, endTime);
  }

  public static Command driveBackward(
      DriveTrain drive, double powerLeft, double powerRight, double endTime) {
    return new A_AutoDrive(drive, () -> powerLeft, () -> powerRight, endTime);
  }
}
