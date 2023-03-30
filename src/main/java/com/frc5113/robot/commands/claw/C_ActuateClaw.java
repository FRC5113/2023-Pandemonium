// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.commands.claw;

import com.frc5113.robot.enums.ClawState;
import com.frc5113.robot.subsystems.S_Claw;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class C_ActuateClaw extends CommandBase {
  public final S_Claw claw;
  public ClawState state;

  /** Creates a new C_ActuateClaw. */
  public C_ActuateClaw(S_Claw claw) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(claw);
    this.claw = claw;
  }

  /** Creates a new ActuateClaw with tostate. */
  public C_ActuateClaw(S_Claw claw, ClawState state) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(claw);
    this.claw = claw;
    this.state = state;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // System.out.println("Acutate claw");
    if (state == null) {
      claw.actuate();
    } else {
      claw.set(state);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true; // should use InstantCommand, but not that important
  }
}
