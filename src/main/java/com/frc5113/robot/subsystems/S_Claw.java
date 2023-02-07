// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class S_Claw extends SubsystemBase {
  /** Creates a new S_Claw. */
  public final DoubleSolenoid clawSolenoid;

  public S_Claw() {
    clawSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
