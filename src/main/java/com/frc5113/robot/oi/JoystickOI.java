package com.frc5113.robot.oi;

import com.frc5113.library.oi.scalers.*;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import java.util.function.Supplier;

public class JoystickOI implements IOI {
  private final CommandJoystick joystick;
  private Curve curve;

  public JoystickOI() {
    joystick = new CommandJoystick(1);
    curve = new NoOpCurve();
  }

  public JoystickOI(int port) {
    joystick = new CommandJoystick(port);
    curve = new NoOpCurve();
  }

  public JoystickOI(int port, Curve curve) {
    this(port);
    setCurve(curve);
  }

  /**
   * @return Axis responsible for arcade speed
   */
  @Override
  public Supplier<Double> arcadeSpeed() {
    return () -> curve.calculateMappedVal(joystick.getX());
  }

  /**
   * @return Axis responsible for arcade turn
   */
  @Override
  public Supplier<Double> arcadeTurn() {
    return () -> curve.calculateMappedVal(joystick.getZ());
  }

  /**
   * @return Axis responsible for tank left side
   */
  @Override
  public Supplier<Double> tankL() {
    return () -> curve.calculateMappedVal(joystick.getZ());
  }

  /**
   * @return Axis responsible for tank right side
   */
  @Override
  public Supplier<Double> tankR() {
    return () -> curve.calculateMappedVal(joystick.getY());
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
