// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.subsystems;

import com.frc5113.library.loops.ILooper;
import com.frc5113.library.loops.Loop;
import com.frc5113.library.subsystem.SmartSubsystem;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class S_Gyro extends SmartSubsystem {

  private AHRS navX;
  /** Creates a new S_Gyro. */
  public S_Gyro() {
    navX = new AHRS(SPI.Port.kMXP);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public boolean checkSubsystem() {
    return true;
  }

  @Override
  public void outputTelemetry() {
    SmartDashboard.putNumber("YAW: ", getAngle(0, 0));
    SmartDashboard.putNumber("PITCH: ", getPitch(0, 0));
    SmartDashboard.putNumber("ROLL: ", getRoll(0, 0));
  }

  @Override
  public void readPeriodicInputs() {}

  @Override
  public void registerEnabledLoops(ILooper enabledLooper) {
    enabledLooper.register(
        new Loop() {
          @Override
          public void onLoop(double arg0) {}

          @Override
          public void onStart(double arg0) {
            stop(); // be sure to stop
            zeroSensors();
          }

          @Override
          public void onStop(double arg0) {
            stop(); // be sure to stop
            zeroSensors();
          }
        });
  }

  @Override
  public void stop() {}

  @Override
  public void writePeriodicOutputs() {}

  @Override
  public void zeroSensors() {
    navX.reset();
  }

  // GETTER Methods

  public double getAngle(double deadband, double offset) {
    if (Math.abs(navX.getAngle()) < deadband) {
      return 0;
    } else {
      return navX.getAngle() - offset;
    }
  }

  public double getPitch(double deadband, double offset) {
    if (Math.abs(navX.getPitch()) < deadband) {
      return 0;
    } else {
      return navX.getPitch() - offset;
    }
  }

  public double getRoll(double deadband, double offset) {
    if (Math.abs(navX.getRoll()) < deadband) {
      return 0;
    } else {
      return navX.getRoll() - offset;
    }
  }
}
