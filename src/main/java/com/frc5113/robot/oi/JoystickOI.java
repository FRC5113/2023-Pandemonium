package com.frc5113.robot.oi;

import com.frc5113.robot.constants.OperatorInterfaceConstants;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.util.function.Supplier;

public class JoystickOI implements IOI {
  private final CommandJoystick joystick = new CommandJoystick(1);

  /**
   * @return Axis responsible for arcade speed
   */
  @Override
  public Supplier<Double> arcadeSpeed() {
    return joystick::getX;
  }

  /**
   * @return Axis responsible for arcade turn
   */
  @Override
  public Supplier<Double> arcadeTurn() {
    return joystick::getZ;
  }

  /**
   * @return Axis responsible for tank left side
   */
  @Override
  public Supplier<Double> tankL() {
    return joystick::getX;
  }

  /**
   * @return Axis responsible for tank right side
   */
  @Override
  public Supplier<Double> tankR() {
    return joystick::getY;
  }

  @Override
  public Trigger armFoldedButton() {
    return new JoystickButton(
        joystick.getHID(), OperatorInterfaceConstants.JOYSTICK_ARM_FOLDED_BUTTON_ID);
  }

  @Override
  public Trigger armGroundButton() {
    return new JoystickButton(
        joystick.getHID(), OperatorInterfaceConstants.JOYSTICK_ARM_GROUND_BUTTON_ID);
  }

  @Override
  public Trigger armDropButton() {
    return new JoystickButton(
        joystick.getHID(), OperatorInterfaceConstants.JOYSTICK_ARM_DROP_BUTTON_ID);
  }
}
