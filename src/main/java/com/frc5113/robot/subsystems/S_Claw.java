// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.subsystems;

import static com.frc5113.robot.constants.PneumaticsConstants.*;

import com.frc5113.library.subsystem.SmartSubsystem;
import com.frc5113.robot.enums.ClawState;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** The claw is a primarily pneumatic open/close mechanism for game piece acquisition */
public class S_Claw extends SmartSubsystem {
  private final DoubleSolenoid clawSolenoid;
  private final PneumaticsBase hub;
  /** Save some CAN time by storing state */
  private ClawState state;

  /** Creates a new Claw Subsystem. */
  public S_Claw(PneumaticsBase hub) {
    clawSolenoid = hub.makeDoubleSolenoid(CLAW_FORWARD_SOLENOID_ID, CLAW_BACKWARD_SOLENOID_ID);
    this.hub = hub;
  }

  public DoubleSolenoid getClawSolenoid() {
    return clawSolenoid;
  }

  public void actuate() {
    clawSolenoid.toggle();
    setState(clawSolenoid.get());
  }

  public void expand() {
    clawSolenoid.set(DoubleSolenoid.Value.kReverse);
    setState(DoubleSolenoid.Value.kReverse);
  }

  public void contract() {
    clawSolenoid.set(DoubleSolenoid.Value.kForward);
    setState(DoubleSolenoid.Value.kReverse);
  }

  // methods required by SmartSubsystem
  @Override
  public void outputTelemetry() {
    SmartDashboard.putData("Claw: clawSolenoid", clawSolenoid);
  }

  @Override
  public void stop() {
    contract();
  }

  @Override
  public void zeroSensors() {
    contract();
  }

  @Override
  public boolean checkSubsystem() {
    return hub.checkSolenoidChannel(CLAW_BACKWARD_SOLENOID_ID)
        && hub.checkSolenoidChannel(CLAW_FORWARD_SOLENOID_ID);
  }

  @Override
  public void periodic() {}

  // getters
  public static ClawState solenoidToClawState(DoubleSolenoid.Value v) {
    if (v == DoubleSolenoid.Value.kForward) {
      return ClawState.Closed;
    }
    return ClawState.Open;
  }

  public ClawState getState() {
      return state;
  }

  private void setState(ClawState state) {
    this.state = state;
  }
}
