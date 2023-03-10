package com.frc5113.robot.oi;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.util.function.Supplier;

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

  Trigger button1();
}
