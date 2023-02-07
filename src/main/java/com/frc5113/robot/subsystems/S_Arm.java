// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.subsystems;

import static com.frc5113.robot.constants.ArmConstants.LEFT_FALCON_CAN_ID;
import static com.frc5113.robot.constants.ArmConstants.RIGHT_FALCON_CAN_ID;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.frc5113.library.motors.SmartFalcon;
import com.frc5113.library.subsystem.SmartSubsystem;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The arm is a primary component of the robot that lifts and lowers the claw.
 *
 * <p>It consists of two Falcon 500s in a parallel config
 */
public class S_Arm extends SmartSubsystem {

  /** Falcon located right of robot */
  private final SmartFalcon rightFalcon;
  /** Falcon located left of robot */
  private final SmartFalcon leftFalcon;

  /** Group of arm motors */
  private final MotorControllerGroup armGroup;

  /** Creates a new Arm Subsystem. */
  public S_Arm() {
    rightFalcon = new SmartFalcon(RIGHT_FALCON_CAN_ID); // create a regular falcon on the right side
    leftFalcon =
        new SmartFalcon(LEFT_FALCON_CAN_ID, true); // create a inverted falcon on the left side

    armGroup = new MotorControllerGroup(rightFalcon, leftFalcon);
  }

  @Override
  public void periodic() {}

  // Stuff required by SmartSubsystem
  @Override
  public void zeroSensors() {
    rightFalcon.resetEncoder();
    leftFalcon.resetEncoder();
  }

  @Override
  public void stop() {
    armGroup.set(0);
  }

  @Override
  public void outputTelemetry() {
    SmartDashboard.putData("Arm: leftFalcon", leftFalcon);
    SmartDashboard.putData("Arm: rightFalcon", rightFalcon);
  }

  @Override
  public boolean checkSubsystem() {
    return leftFalcon.isConnected() && rightFalcon.isConnected();
  }

  // Getters
  public WPI_TalonFX getLeftFalcon() {
    return leftFalcon;
  }

  public WPI_TalonFX getRightFalcon() {
    return rightFalcon;
  }

  public MotorControllerGroup getArmGroup() {
    return armGroup;
  }
}
