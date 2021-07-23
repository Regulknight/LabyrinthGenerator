import java.awt.geom.Point2D;

public class Sector {
    private final Angle leftBorderAngle;
    private final Angle rightBorderAngle;

    private final boolean isCrossZeroAngle;

    public Sector(Angle baseAngle, double angleOfView) {
        leftBorderAngle = new Angle(baseAngle.getAngle() - angleOfView / 2);
        rightBorderAngle = new Angle(baseAngle.getAngle() + angleOfView / 2);

        //Strict of comparing depends on rounding while angle creation
        boolean isCrossZeroAngleFromTop = baseAngle.getAngle() - angleOfView / 2 < 0;
        boolean isCrossZeroAngleFromBottom = baseAngle.getAngle() + angleOfView / 2 >= 2 * Math.PI;
        isCrossZeroAngle = isCrossZeroAngleFromTop || isCrossZeroAngleFromBottom;
    }

    public boolean isPointContainInDirection(Point2D viewer, Point2D observer) {
        double normalizedX = observer.getX() - viewer.getX();
        double normalizedY = - observer.getY() + viewer.getY();

        if (normalizedX == 0 && normalizedY == 0) {
            return true;
        }

        Angle viewAngle = new Angle(normalizedX, normalizedY);

        if (isCrossZeroAngle) {
            return viewAngle.compare(rightBorderAngle) <= 0 || viewAngle.compare(leftBorderAngle) >= 0;
        }

        return viewAngle.compare(leftBorderAngle) >= 0 && viewAngle.compare(rightBorderAngle) <= 0;
    }


}
