package org.firstinspires.ftc.teamcode;

public enum TimeConstants {
    FORWARD_SPEED(1),
    TURN_SPEED(.65),
    BLOCK_POWER(.6),
    GRAB_TIME(1),
    TURN_TIME(1.1),
    TAPE_POWER(.7),
    BUILD_ZONE_TIME(3.1);

    private final double value;

    TimeConstants(double v){
        value = v;
    }

    public double getValue() {
        return value;
    }
}
