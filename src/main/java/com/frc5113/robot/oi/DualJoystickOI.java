package com.frc5113.robot.oi;

import com.frc5113.library.oi.scalers.*;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import java.util.function.Supplier;

public class DualJoystickOI implements IOI {
  private final CommandJoystick joystick1;
  private final CommandJoystick joystick2;
  private Curve curve;

  public DualJoystickOI() {
    joystick1 = new CommandJoystick(1);
    joystick2 = new CommandJoystick(2);
    curve = new NoOpCurve();
  }

  public DualJoystickOI(int port1, int port2) {
    joystick1 = new CommandJoystick(port1);
    joystick2 = new CommandJoystick(port2);
    curve = new NoOpCurve();
  }

  public DualJoystickOI(int port1, int port2, Curve curve) {
    this(port1, port2);
    setCurve(curve);
  }

  /**
   * @return Axis responsible for arcade speed
   */
  @Override
  public Supplier<Double> arcadeSpeed() {
    return () -> curve.calculateMappedVal(joystick1.getX());
  }

  /**
   * @return Axis responsible for arcade turn
   */
  @Override
  public Supplier<Double> arcadeTurn() {
    return () -> curve.calculateMappedVal(joystick1.getZ());
  }

  /**
   * @return Axis responsible for tank left side
   */
  @Override
  public Supplier<Double> tankL() {
    return () -> curve.calculateMappedVal(joystick1.getY());
  }

  /**
   * @return Axis responsible for tank right side
   */
  @Override
  public Supplier<Double> tankR() {
    return () -> curve.calculateMappedVal(joystick2.getY());
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
