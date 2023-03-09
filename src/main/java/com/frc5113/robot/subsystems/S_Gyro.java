// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.subsystems;

import com.frc5113.library.loops.ILooper;
import com.frc5113.library.loops.Loop;
import com.frc5113.library.subsystem.SmartSubsystem;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class S_Gyro extends SmartSubsystem {

  private AHRS navX;
  private double yaw, pitch, roll;
  /** Creates a new S_Gyro. */
  public S_Gyro() {
    navX = new AHRS(SPI.Port.kMXP);
  }

  @Override
  public boolean checkSubsystem() {
    return navX.isConnected();
  }

  @Override
  public void outputTelemetry() {
    SmartDashboard.putNumber("Gyro: Yaw", fetchAngle(0, 0));
    SmartDashboard.putNumber("Gyro: Pitch", fetchPitch(0, 0));
    SmartDashboard.putNumber("Gyro: Roll", fetchRoll(0, 0));
  }

  @Override
  public void readPeriodicInputs() {
    yaw = fetchAngle(0, 0);
    roll = fetchRoll(0, 0);
    pitch = fetchPitch(0, 0);
  }

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
    yaw = roll = pitch = 0;
  }

  // Internal GETTER Methods
  private double fetchAngle(double deadband, double offset) {
    if (Math.abs(navX.getAngle()) < deadband) {
      return 0;
    } else {
      return navX.getAngle() - offset;
    }
  }

  private double fetchPitch(double deadband, double offset) {
    if (Math.abs(navX.getPitch()) < deadband) {
      return 0;
    } else {
      return navX.getPitch() - offset;
    }
  }

  private double fetchRoll(double deadband, double offset) {
    if (Math.abs(navX.getRoll()) < deadband) {
      return 0;
    } else {
      return navX.getRoll() - offset;
    }
  }

  // GETTER Methods
  public double getAngle() {
    return yaw;
  }

  public double getPitch() {
    return pitch;
  }

  public double getRoll() {
    return roll;
  }

  public Rotation2d getRotation2d() {
    return navX.getRotation2d();
  }

  public double getHeading() {
    return getRotation2d().getDegrees();
  }

  @Override
  public boolean checkSubsystemPeriodic() {
    return false;
  }

  @Override
  public void registerPeriodicSubsystemCheck(ILooper loop) {}
}
