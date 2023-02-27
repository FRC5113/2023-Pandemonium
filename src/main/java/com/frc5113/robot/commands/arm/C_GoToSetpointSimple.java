// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.commands.arm;

import com.frc5113.robot.enums.ArmPosition;
import com.frc5113.robot.subsystems.S_Arm;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class C_GoToSetpointSimple extends CommandBase {
  private final S_Arm arm;
  private ArmPosition setpoint;

  /** Creates a new C_GoToSetpoint. */
  public C_GoToSetpointSimple(S_Arm arm, ArmPosition setpoint) {
    addRequirements(arm);

    this.arm = arm;
    this.setpoint = setpoint;
  }

  @Override
  public void initialize() {
    arm.setPosition(setpoint.getSetpoint());
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    arm.set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return arm.atPosition(setpoint.getSetpoint(), 500);
  }
}
