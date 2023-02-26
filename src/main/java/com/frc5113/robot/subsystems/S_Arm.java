// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.subsystems;

import static com.frc5113.robot.constants.ArmConstants.LEFT_FALCON_CAN_ID;
import static com.frc5113.robot.constants.ArmConstants.RIGHT_FALCON_CAN_ID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.frc5113.library.loops.ILooper;
import com.frc5113.library.loops.Loop;
import com.frc5113.library.motors.SmartFalcon;
import com.frc5113.library.subsystem.SmartSubsystem;
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

  private double leftFalconEncoderTicks;
  private double rightFalconEncoderTicks;

  /** Creates a new Arm Subsystem. */
  public S_Arm() {
    rightFalcon = new SmartFalcon(RIGHT_FALCON_CAN_ID); // create a regular falcon on the right side
    leftFalcon =
        new SmartFalcon(LEFT_FALCON_CAN_ID, true); // create a inverted falcon on the left side
    leftFalcon.follow(rightFalcon); // this is necessary, and motor controller groups can't be used
    // because we will be using "position set()"
  }

  // Stuff required by SmartSubsystem
  @Override
  public void zeroSensors() {
    rightFalcon.resetEncoder();
    leftFalcon.resetEncoder();
  }

  @Override
  public void stop() {
    rightFalcon.set(0);
  }

  @Override
  public void outputTelemetry() {
    SmartDashboard.putData("Arm: leftFalcon", leftFalcon);
    SmartDashboard.putData("Arm: rightFalcon", rightFalcon);
    SmartDashboard.putNumber("Arm: leftFalconTicks", leftFalcon.getEncoderTicks());
    SmartDashboard.putNumber("Arm: rightFalconTicks", rightFalcon.getEncoderTicks());
  }

  @Override
  public boolean checkSubsystem() {
    return leftFalcon.isConnected() && rightFalcon.isConnected();
  }

  @Override
  public void readPeriodicInputs() {
    leftFalconEncoderTicks = leftFalcon.getEncoderTicks();
    rightFalconEncoderTicks = rightFalcon.getEncoderTicks();
  }

  @Override
  public void writePeriodicOutputs() {}

  @Override
  public void registerEnabledLoops(ILooper looper) {
    looper.register(
        new Loop() {
          @Override
          public void onLoop(double dt) {}

          @Override
          public void onStart(double dt) {
            rightFalcon.setNeutralMode(NeutralMode.Brake);
            leftFalcon.setNeutralMode(NeutralMode.Brake);
          }

          @Override
          public void onStop(double dt) {
            rightFalcon.setNeutralMode(NeutralMode.Coast);
            leftFalcon.setNeutralMode(NeutralMode.Coast);
          }
        });
  }

  public void set(double speed) {
    rightFalcon.set(speed);
  }

  public void setPosition(double position) {
    rightFalcon.set(ControlMode.Position, position);
  }

  // Getters
  public double getTickPosition() {
    return rightFalcon.getEncoderTicks();
  }
}
