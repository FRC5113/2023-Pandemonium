// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.subsystems;

import static com.frc5113.robot.constants.PneumaticsConstants.ANALOG_SENSOR_PORT;

import com.frc5113.library.loops.ILooper;
import com.frc5113.library.subsystem.SmartSubsystem;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * General purpose pneumatics subsystem for the robot Subsystems that use pneumatics should be
 * created here for use ease of use of pneumatics components
 */
public class S_Pneumatics extends SmartSubsystem {
  private final S_Claw claw;
  private final PneumaticHub pneumaticHub;

  double pressure;

  public S_Pneumatics() {
    pneumaticHub = new PneumaticHub();
    claw = new S_Claw(pneumaticHub);

    // enable pneumatics compressor to refill loop at specified pressures
    // pneumaticHub.enableCompressorAnalog(LOOP_MIN_PRESSURE, LOOP_MAX_PRESSURE);
    pneumaticHub.enableCompressorDigital();
  }

  // Methods required by SmartSubsystem
  @Override
  public void zeroSensors() {
    // Nothing to zero out
  }

  @Override
  public boolean checkSubsystemPeriodic() {
    return true;
  }

  @Override
  public void registerPeriodicSubsystemCheck(ILooper loop) {}

  @Override
  public void outputTelemetry() {
    SmartDashboard.putNumber("Pneumatics: Pressure", getPressure());
  }

  @Override
  public boolean checkSubsystem() {
    // check conditions: 1) there exists a compressor 2) there exists pressure
    return pneumaticHub.getFaults() == null && getPressure() > 0;
  }

  public double getPressure() {
    return pressure;
  }

  @Override
  public void stop() {
    // Nothing to stop
  }

  @Override
  public void readPeriodicInputs() {
    pressure = pneumaticHub.getPressure(ANALOG_SENSOR_PORT);
  }

  @Override
  public void writePeriodicOutputs() {}

  @Override
  public void registerEnabledLoops(ILooper a) {}

  // Getters

  public S_Claw getClaw() {
    return claw;
  }

  public PneumaticHub getPneumaticHub() {
    return pneumaticHub;
  }
}
