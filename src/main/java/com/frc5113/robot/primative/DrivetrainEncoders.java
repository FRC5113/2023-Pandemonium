package com.frc5113.robot.primative;

public class DrivetrainEncoders {
  private double leftLeader = 0;
  private double rightLeader = 0;
  private double leftFollower = 0;
  private double rightFollower = 0;

  public void updateMeasurements(
      double leftLeader, double rightLeader, double leftFollower, double rightFollower) {
    this.leftLeader = leftLeader;
    this.rightLeader = rightLeader;
    this.leftFollower = leftFollower;
    this.rightFollower = rightFollower;
  }

  public double getLeftLeader() {
    return leftLeader;
  }

  public double getRightLeader() {
    return rightLeader;
  }

  public double getLeftFollower() {
    return leftFollower;
  }

  public double getRightFollower() {
    return rightFollower;
  }
}
