package com.lon.game.logic.angle;

public class Angle {
    private final double radians;

    public Angle(double radians) {
        this.radians = normalizeAngle(radians);
    }

    public Angle(double x, double y) {
        this.radians = normalizeAngle(Math.atan2(y, x));
    }

    private double normalizeAngle(double angle) {
        double doublePICount = Math.floor(angle / (2.0 * Math.PI));

        double result = angle - (Math.PI * 2 * doublePICount);

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
