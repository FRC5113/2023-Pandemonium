package com.frc5113.robot.oi;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.util.function.Supplier;

public class CMPOI implements IOI {
  private final CommandXboxController xbox = new CommandXboxController(0);
  private final CommandJoystick joystickLeft = new CommandJoystick(1);
  private final CommandJoystick joystickRight = new CommandJoystick(2);

  /**
   * @return Axis responsible for arcade speed
   */
  @Override
  public Supplier<Double> arcadeSpeed() {
    return xbox::getLeftY;
  }

  /**
   * @return Axis responsible for arcade turn
   */
  @Override
  public Supplier<Double> arcadeTurn() {
    return xbox::getRightX;
  }

  /**
   * @return Axis responsible for tank left side
   */
  @Override
  public Supplier<Double> tankL() {
    return () -> joystickLeft.getY();
  }

  /**
   * @return Axis responsible for tank right side
   */
  @Override
  public Supplier<Double> tankR() {
    return () -> joystickRight.getY();
  }

  public Trigger armGroundButton() {
    return xbox.y();
  }

  public Trigger armDropButton() {
    return xbox.x();
  }

  public Trigger armButtonTest() {
    return xbox.a();
  }

  public Trigger clawButton() {
    return xbox.b();
  }

  public Supplier<Double> leftTrigger() {
    return xbox::getLeftTriggerAxis;
  }

  public Supplier<Double> rightTrigger() {
    return xbox::getRightTriggerAxis;
  }

  public Supplier<Boolean> slowMode() {
    return () -> joystickLeft.button(1).getAsBoolean() || joystickRight.button(1).getAsBoolean();
  }
}
