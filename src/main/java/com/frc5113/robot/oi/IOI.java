package com.frc5113.robot.oi;

import com.frc5113.library.oi.scalers.Curve;
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

  Curve getCurve();

  void setCurve(Curve curve);
}
