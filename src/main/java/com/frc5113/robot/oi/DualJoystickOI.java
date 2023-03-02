package com.frc5113.robot.oi;

import com.frc5113.robot.constants.OperatorInterfaceConstants;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.util.function.Supplier;

public class DualJoystickOI implements IOI {
  private final CommandJoystick joystick1 = new CommandJoystick(1);
  private final CommandJoystick joystick2 = new CommandJoystick(2);

  /**
   * @return Axis responsible for arcade speed
   */
  @Override
  public Supplier<Double> arcadeSpeed() {
    return joystick1::getX;
  }

  /**
   * @return Axis responsible for arcade turn
   */
  @Override
  public Supplier<Double> arcadeTurn() {
    return joystick1::getZ;
  }

  /**
   * @return Axis responsible for tank left side
   */
  @Override
  public Supplier<Double> tankL() {
    return joystick1::getY;
  }

  /**
   * @return Axis responsible for tank right side
   */
  @Override
  public Supplier<Double> tankR() {
    return joystick2::getY;
  }

  @Override
  public Trigger armFoldedButton() {
    return new JoystickButton(
        joystick1.getHID(), OperatorInterfaceConstants.JOYSTICK_ARM_FOLDED_BUTTON_ID);
  }

  @Override
  public Trigger armGroundButton() {
    return new JoystickButton(
        joystick1.getHID(), OperatorInterfaceConstants.JOYSTICK_ARM_GROUND_BUTTON_ID);
  }

  @Override
  public Trigger armDropButton() {
    return new JoystickButton(
        joystick1.getHID(), OperatorInterfaceConstants.JOYSTICK_ARM_DROP_BUTTON_ID);
  }

  @Override
  public Trigger panicButton() {
    return new JoystickButton(
        joystick1.getHID(), OperatorInterfaceConstants.JOYSTICK_PANIC_BUTTON_ID);
  }

  @Override
  public Trigger actuateClawButton() {
    return new JoystickButton(joystick1.getHID(), 6);
  }

  @Override
  public Supplier<Double> test1() {
    // TODO Auto-generated method stub
    return () -> 0.0;
  }

  @Override
  public Supplier<Double> test2() {
    // TODO Auto-generated method stub
    return () -> 0.0;
  }
}
