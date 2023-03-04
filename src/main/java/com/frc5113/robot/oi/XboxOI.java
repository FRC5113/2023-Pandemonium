package com.frc5113.robot.oi;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.util.function.Supplier;

public class XboxOI implements IOI {
  private final CommandXboxController xbox = new CommandXboxController(0);

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
    return xbox::getLeftY;
  }

  /**
   * @return Axis responsible for tank right side
   */
  @Override
  public Supplier<Double> tankR() {
    return xbox::getRightY;
  }

  public Trigger armButton() {
    return xbox.a();
  }

  public Trigger armButtonTest() {
    return xbox.y();
  }

  public Trigger clawButton() {
    return xbox.b();
  }
}
