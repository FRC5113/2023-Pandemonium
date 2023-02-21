// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.commands.arm;

import static com.frc5113.robot.constants.ArmConstants.kD;
import static com.frc5113.robot.constants.ArmConstants.kI;
import static com.frc5113.robot.constants.ArmConstants.kP;
import static com.frc5113.robot.constants.ArmConstants.kTolerance;
import static com.frc5113.robot.constants.ArmConstants.kVTolerance;

import com.frc5113.robot.enums.ArmPosition;
import com.frc5113.robot.subsystems.S_Arm;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

public class P_GoToSetpoint extends PIDCommand {
  /** Creates a new C_GoToSetpoint. */
  public P_GoToSetpoint(S_Arm arm, ArmPosition setpoint) {
    super(
        // The controller that the command will use
        new PIDController(kP.get(), kI.get(), kD.get()),
        // This should return the measurement
        () -> arm.getThroughBorePosition(),
        // This should return the setpoint (can also be a constant)
        () -> setpoint.getSetpoint(),
        // This uses the output
        output -> {
          // Use output to set position on arm
          arm.setPosition(output);
        },
        arm);
    // Aditional configs
    getController().setTolerance(kTolerance.get(), kVTolerance.get());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
