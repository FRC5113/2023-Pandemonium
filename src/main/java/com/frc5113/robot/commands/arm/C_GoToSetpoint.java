// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc5113.robot.commands.arm;

import static com.frc5113.robot.constants.ArmConstants.*;
import static com.frc5113.robot.constants.GeneralConstants.TUNING_MODE;

import com.frc5113.library.utils.tunable.TunableNumber;
import com.frc5113.robot.enums.ArmPosition;
import com.frc5113.robot.subsystems.S_Arm;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

public class C_GoToSetpoint extends PIDCommand {
  private static final TunableNumber kP = new TunableNumber("Arm: P", ARM_P, TUNING_MODE);
  private static final TunableNumber kI = new TunableNumber("Arm: I", ARM_I, TUNING_MODE);
  private static final TunableNumber kD = new TunableNumber("Arm: D", ARM_D, TUNING_MODE);
  private static final TunableNumber kTolerance =
      new TunableNumber("Arm: Tolerance", ARM_TOLERANCE, TUNING_MODE);
  private static final TunableNumber kVTolerance =
      new TunableNumber("Arm: VTolerance", ARM_VTOLERANCE, TUNING_MODE);

  /** Creates a new C_GoToSetpoint. */
  public C_GoToSetpoint(S_Arm arm, ArmPosition setpoint) {
    super(
        // The controller that the command will use
        new PIDController(kP.get(), kI.get(), kD.get()),
        // This should return the measurement
        () -> arm.getTickPosition(),
        // This should return the setpoint (can also be a constant)
        () -> setpoint.getSetpoint(),
        // This uses the output
        output -> {
          // Use output to set position on arm
          arm.set(output);
        },
        arm);
    // Aditional configs
    getController().setTolerance(kTolerance.get(), kVTolerance.get());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
