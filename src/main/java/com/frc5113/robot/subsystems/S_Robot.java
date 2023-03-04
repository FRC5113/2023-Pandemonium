// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.subsystems;

import com.frc5113.library.loops.ILooper;
import com.frc5113.library.subsystem.SmartSubsystem;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * General purpose pneumatics subsystem for the robot Subsystems that use pneumatics should be
 * created here for use ease of use of pneumatics components
 */
public class S_Robot extends SmartSubsystem {

  private PowerDistribution pdh;

  public S_Robot() {}

  // Methods required by SmartSubsystem
  @Override
  public void zeroSensors() {
    // Nothing to zero out
  }

  @Override
  public void outputTelemetry() {
    SmartDashboard.putNumber("Robot: Voltage", pdh.getVoltage());
    SmartDashboard.putData("Robot: PDH", pdh);
    SmartDashboard.putNumber("Robot: Match Time", DriverStation.getMatchTime());
  }

  @Override
  public boolean checkSubsystem() {
    // no fair
    return true;
  }

  @Override
  public boolean checkSubsystemPeriodic() {
    return true;
  }

  @Override
  public void registerPeriodicSubsystemCheck(ILooper loop) {}

  @Override
  public void stop() {
    // Nothing to stop
  }

  @Override
  public void readPeriodicInputs() {}

  @Override
  public void writePeriodicOutputs() {}

  @Override
  public void registerEnabledLoops(ILooper a) {}

  public PowerDistribution getPdh() {
    return pdh;
  }
}
