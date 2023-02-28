package com.frc5113.robot.commands.drive;

import java.util.function.Supplier;

import org.littletonrobotics.junction.Logger;

import com.frc5113.robot.subsystems.drive.S_DriveTrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.frc5113.robot.constants.DrivetrainConstants.*;

public class D_TeleopDrive extends CommandBase {
  private static final double deadband = DEAD_BAND;
  private static final double maxAcceleration = MAX_ACCELERATION; // Percent velocity per second
  private static final double maxJerk = MAX_JERK;
  private final S_DriveTrain drive;
  private final Supplier<Double> leftXSupplier;
  private final Supplier<Double> leftYSupplier;
  private final Supplier<Double> rightXSupplier;
  private final Supplier<Double> rightYSupplier;
  private final boolean arcade;


  private final AxisProcessor leftXProcessor = new AxisProcessor();
  private final AxisProcessor leftYProcessor = new AxisProcessor();
  private final AxisProcessor rightXProcessor = new AxisProcessor();
  private final AxisProcessor rightYProcessor = new AxisProcessor();

  /** Creates a new DriveWithJoysticks. Drives based on the joystick values. */
  public D_TeleopDrive(S_DriveTrain drive, boolean arcade, Supplier<Double> leftXSupplier,
      Supplier<Double> leftYSupplier, Supplier<Double> rightXSupplier,
      Supplier<Double> rightYSupplier, Supplier<Boolean> sniperModeSupplier) {
    addRequirements(drive);
    this.drive = drive;
    this.leftXSupplier = leftXSupplier;
    this.leftYSupplier = leftYSupplier;
    this.rightXSupplier = rightXSupplier;
    this.rightYSupplier = rightYSupplier;
    this.arcade = arcade;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    leftXProcessor.reset(leftXSupplier.get());
    leftYProcessor.reset(leftYSupplier.get());
    rightXProcessor.reset(rightXSupplier.get());
    rightYProcessor.reset(rightYSupplier.get());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftXValue = leftXProcessor.process(leftXSupplier.get());
    double leftYValue = leftYProcessor.process(leftYSupplier.get());
    double rightXValue = rightXProcessor.process(rightXSupplier.get());
    double rightYValue = rightYProcessor.process(rightYSupplier.get());

    WheelSpeeds speeds = new WheelSpeeds(0.0, 0.0);
    if(arcade) {
      speeds = WheelSpeeds.fromArcade(leftYValue, rightXValue);
    } else {
      speeds = new WheelSpeeds(leftYValue, rightYValue);
    }


    double leftPercent =
        MathUtil.clamp(speeds.left, -1.0, 1.0);
    double rightPercent =
        MathUtil.clamp(speeds.right, -1.0, 1.0);

    Logger.getInstance().recordOutput("ActiveCommands/DriveWithJoysticks",
        true);
    Logger.getInstance().recordOutput("DriveWithJoysticks/LeftPercent",
        leftPercent);
    Logger.getInstance().recordOutput("DriveWithJoysticks/RightPercent",
        rightPercent);
    drive.drivePercent(leftPercent, rightPercent);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public static double getDeadband() {
    return deadband;
  }

  /** Represents a left and right percentage. */
  private static class WheelSpeeds {
    public double left;
    public double right;

    public WheelSpeeds(double left, double right) {
      this.left = left;
      this.right = right;
    }

    public static WheelSpeeds fromArcade(double baseSpeed, double turnSpeed) {
      return new WheelSpeeds(baseSpeed + turnSpeed, baseSpeed - turnSpeed);
    }
  }

  /** Cleans up a series of axis value (deadband + squaring + profile) */
  public static class AxisProcessor {
    private TrapezoidProfile.State state = new TrapezoidProfile.State();

    public void reset(double value) {
      state = new TrapezoidProfile.State(value, 0.0);
    }

    public double process(double value) {
      double scaledValue = 0.0;
      if (Math.abs(value) > deadband) {
        scaledValue = (Math.abs(value) - deadband) / (1 - deadband);
        scaledValue = Math.copySign(scaledValue * scaledValue, value);
      }
      TrapezoidProfile profile = new TrapezoidProfile(
          new TrapezoidProfile.Constraints(maxAcceleration,
              maxJerk),
          new TrapezoidProfile.State(scaledValue, 0.0), state);
      state = profile.calculate(0.02);
      return state.position;
    }
  }
}