package com.frc5113.robot.commands.photonvision;

import static com.frc5113.robot.constants.PhotonVisionConstants.*;

import com.frc5113.robot.subsystems.S_PhotonVision;
import com.frc5113.robot.subsystems.DriveTrain.S_DriveTrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

public class P_CenterToTarget extends PIDCommand {
  private S_PhotonVision photonVision;

  public P_CenterToTarget(S_DriveTrain driveTrain, S_PhotonVision photonVision) {
    super(
        new PIDController(kP, kI, kD),
        photonVision::getTx,
        0.0,
        output -> driveTrain.tankDrive(-output, -output),
        driveTrain);
    getController().setTolerance(1);
    this.photonVision = photonVision;
  }

  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
