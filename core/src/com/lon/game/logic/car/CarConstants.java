package com.lon.game.logic.car;

public class CarConstants {
    public static float CAR_SIZE = 1f;
    public static float CAR_FORCE = 0.0005f;
    public static float CAR_ANGLE_SPEED = 0.00025f;
    public static float CAR_MASS_MULTIPLIER = 100f;
    public static float CAR_ANGULAR_DAMPING = 3f;
    public static float CAR_LINEAR_DAMPING = 0.0025f;

    public static float HALF_WHEEL_WIDTH = CAR_SIZE * 0.175f;
    public static float HALF_WHEEL_HEIGHT = CAR_SIZE * 0.06f;

    public static float WHEEL_MAX_ANGLE = (float) (Math.PI/4);
}
