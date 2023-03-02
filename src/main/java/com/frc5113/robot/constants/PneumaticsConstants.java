package com.frc5113.robot.constants;

public class PneumaticsConstants {
  /** PH port of forward valve on claw solenoid */
  public static final int CLAW_FORWARD_SOLENOID_ID = 6;
  /** PH port of reverse valve on claw solenoid */
  public static final int CLAW_BACKWARD_SOLENOID_ID = 7;
  /** Pressure at which compressor should stop working (when it is working) */
  public static final int LOOP_MAX_PRESSURE = 90;
  /** Pressure at which compressor should start working (when it isn't working) */
  public static final int LOOP_MIN_PRESSURE = 60;
  /** Physical channel ID of the analog sensor */
  public static final int ANALOG_SENSOR_PORT = 0;
}
