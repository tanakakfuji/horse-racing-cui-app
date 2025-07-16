package com.github.tanakakfuji.hrcui.enums;

public enum ConditionType {
    PERFECT(0.4),
    FINE(0.2),
    NORMAL(0.0),
    WEAK(-0.2),
    AWFUL(-0.4);

    double speed;

    ConditionType(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }
}
