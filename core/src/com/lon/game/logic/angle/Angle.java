package com.lon.game.logic.angle;

public class Angle {
    private final double radians;

    public static float convertInDeg(float radians) {
        return (float) Math.toDegrees(normalizeAngle(radians));
    }

    public static float getAngle(float x, float y) {
        return normalizeAngle(Math.atan2(y, x));
    }

    public Angle(double radians) {
        this.radians = normalizeAngle(radians);
    }

    public Angle(double x, double y) {
        this.radians = normalizeAngle(Math.atan2(y, x));
    }

    public static float normalizeAngle(double angle) {
        float doublePICount = (float) Math.floor(angle / (2.0 * Math.PI));

        float result = (float) (angle - (Math.PI * 2 * doublePICount));

        if (result < 0) {
            result += 2 * Math.PI;
        }

        return result;
    }

    public double getRadians() {
        return this.radians;
    }

    public float getAngleDeg() {
        return (float) (this.radians / (2 * Math.PI) * 360);
    }

    public Angle addAngle(Angle angle) {
        return new Angle (angle.getRadians() + this.radians);
    }

    public int compare(Angle angle) {
        if (Math.sqrt(Math.abs(this.radians * this.radians - angle.getRadians() * angle.getRadians())) < 0.001) {
            return 0;
        }
        return Double.compare(this.radians, angle.getRadians());
    }

}
