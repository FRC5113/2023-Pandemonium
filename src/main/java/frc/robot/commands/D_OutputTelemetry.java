// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class D_OutputTelemetry extends CommandBase {
  private S_PhotonVision photonVision;
  /** Creates a new DEF_OutputTelemetry. */
  public D_OutputTelemetry(S_PhotonVision photonVision) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.photonVision = photonVision;
    addRequirements(photonVision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (photonVision.hasTargets){
      SmartDashboard.putNumber("Best Target Yaw", photonVision.getTargetYaw(photonVision.getBestTarget()));
      SmartDashboard.putNumber("Best Target Pitch", photonVision.getTargetPitch(photonVision.getBestTarget()));
      SmartDashboard.putNumber("Best Target ID", photonVision.getId(photonVision.getBestTarget()));
      SmartDashboard.putNumber("Best Target Area", photonVision.getTargetArea(photonVision.getBestTarget()));

      SmartDashboard.putNumber("Best Target TX", photonVision.getTx());
      SmartDashboard.putNumber("Best Target TY", photonVision.getTy());
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
