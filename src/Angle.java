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

    public double getAngle() {
        return this.radians;
    }

    public Angle addAngle(Angle angle) {
        return new Angle (angle.getAngle() + this.radians);
    }

    public int compare(Angle angle) {
        if (Math.sqrt(Math.abs(this.radians * this.radians - angle.getAngle() * angle.getAngle())) < 0.001) {
            return 0;
        }
        return Double.compare(this.radians, angle.getAngle());
    }

}
