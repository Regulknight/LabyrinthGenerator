import java.awt.geom.Point2D;

public class ConeOfView {
    private final double coneRadius;
    private final Sector sector;

    public ConeOfView(double coneRadius, Sector sector) {
        this.coneRadius = coneRadius;
        this.sector = sector;
    }

    public boolean isPointContainInCone(Point2D viewer, Point2D observer) {
        return sector.isPointContainInDirection(viewer, observer)
                && Point2D.distance(observer.getX(), observer.getY(), viewer.getX(), viewer.getY()) < coneRadius;
    }
}
