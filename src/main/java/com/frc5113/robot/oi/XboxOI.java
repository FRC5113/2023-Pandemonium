package com.frc5113.robot.oi;

import com.frc5113.library.oi.scalers.*;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import java.util.function.Supplier;

public class XboxOI implements IOI {
  private final CommandXboxController xbox;
  private Curve curve;

  public XboxOI() {
    xbox = new CommandXboxController(0);
    curve = new NoOpCurve();
  }

  public XboxOI(int port) {
    xbox = new CommandXboxController(port);
    curve = new NoOpCurve();
  }

  public XboxOI(int port, Curve curve) {
    this(port);
    setCurve(curve);
  }

  /**
   * @return Axis responsible for arcade speed
   */
  @Override
  public Supplier<Double> arcadeSpeed() {
    return () -> curve.calculateMappedVal(xbox.getLeftY());
  }

  /**
   * @return Axis responsible for arcade turn
   */
  @Override
  public Supplier<Double> arcadeTurn() {
    return () -> curve.calculateMappedVal(xbox.getRightX());
  }

  /**
   * @return Axis responsible for tank left side
   */
  @Override
  public Supplier<Double> tankL() {
    return () -> curve.calculateMappedVal(xbox.getLeftY());
  }

  /**
   * @return Axis responsible for tank right side
   */
  @Override
  public Supplier<Double> tankR() {
    return () -> curve.calculateMappedVal(xbox.getRightY());
  }

  @Override
  public Curve getCurve() {
    return curve;
  }

  @Override
  public void setCurve(Curve curve) {
    this.curve = curve;
  }
}
