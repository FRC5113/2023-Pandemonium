package com.frc5113.robot.enums;

/** Posotions in which the arm can be */
public enum ArmPosition {
    // Note that arm positions are in falcon ticks, so the numbers get high
    Folded(0), Ground(10000), Drop(50000);
    private double setpoint;


    ArmPosition(double setpoint) {
        this.setpoint = setpoint;
    }

    public double getSetpoint() {
        return setpoint;
    }
}
