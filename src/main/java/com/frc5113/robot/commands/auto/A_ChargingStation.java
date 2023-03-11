// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.commands.auto;

import com.frc5113.robot.commands.drive.P_AutoBalance;
import com.frc5113.robot.commands.drive.P_DriveDistance;
import com.frc5113.robot.subsystems.DriveTrain;
import com.frc5113.robot.subsystems.S_Gyro;
// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class A_ChargingStation extends SequentialCommandGroup {
  /** Creates a new A_ChargingStation. */
  public A_ChargingStation(DriveTrain driveTrain, S_Gyro gyro) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new P_DriveDistance(driveTrain, 5), new P_AutoBalance(driveTrain, gyro));
  }
}
