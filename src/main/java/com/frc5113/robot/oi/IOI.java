package com.frc5113.robot.oi;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public interface IOI {
  /**
   * @return Axis responsible for arcade speed
   */
  Supplier<Double> arcadeSpeed();

  /**
   * @return Axis responsible for arcade turn
   */
  Supplier<Double> arcadeTurn();

  /**
   * @return Axis responsible for tank left side
   */
  Supplier<Double> tankL();

  /**
   * @return Axis responsible for tank right side
   */
  Supplier<Double> tankR();

  /**
   * @return get primary button on controller
   */
  Trigger getPrimary();

  /**
   * @return get secondary button on controller
   */
  Trigger getSecondary();
}
