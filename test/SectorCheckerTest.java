import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

public class SectorCheckerTest {
    private static final double rightAngle = Math.PI / 2;

    private static final Point2D center = new Point2D.Double(25, 25);

    public static final Point2D rightMiddle = new Point2D.Double(50, 25);
    public static final Point2D rightUp = new Point2D.Double(50, 0);
    public static final Point2D middleUp = new Point2D.Double(25, 0);
    public static final Point2D leftUp = new Point2D.Double(0, 0);
    public static final Point2D leftMiddle = new Point2D.Double(0, 25);
    public static final Point2D leftDown = new Point2D.Double(0, 50);
    public static final Point2D middleDown = new Point2D.Double(25, 50);
    public static final Point2D rightDown = new Point2D.Double(50, 50);

    @Test
    public void upSectorTest() {
        Sector up = new Sector(Directions.UP, rightAngle);

        assertTrue(up.isPointContainInDirection(center, new Point2D.Double(25, 15)), "(25, 15) check");

        assertTrue(up.isPointContainInDirection(center, leftUp), "Upper left corner check");
        assertTrue(up.isPointContainInDirection(center, middleUp), "Upside middle check");
        assertTrue(up.isPointContainInDirection(center, rightUp), "Upper right corner check");
        assertFalse(up.isPointContainInDirection(center, rightMiddle), "Right side middle check");
        assertFalse(up.isPointContainInDirection(center, rightDown), "Bottom right corner check");
        assertFalse(up.isPointContainInDirection(center, middleDown), "Bottom side middle check");
        assertFalse(up.isPointContainInDirection(center, leftDown), "Bottom left corner check");
        assertFalse(up.isPointContainInDirection(center, leftMiddle), "Left side middle check");
    }

    @Test
    public void rightSectorTest() {
        Sector right = new Sector(Directions.RIGHT, rightAngle);
        assertTrue(right.isPointContainInDirection(center, new Point2D.Double(35, 25)), "(35, 25) check");

        assertFalse(right.isPointContainInDirection(center, leftUp), "Upper left corner check");
        assertFalse(right.isPointContainInDirection(center, middleUp), "Upside middle check");
        assertTrue(right.isPointContainInDirection(center, rightUp), "Upper right corner check");
        assertTrue(right.isPointContainInDirection(center, rightMiddle), "Right side middle check");
        assertTrue(right.isPointContainInDirection(center, rightDown), "Bottom right corner check");
        assertFalse(right.isPointContainInDirection(center, middleDown), "Bottom side middle check");
        assertFalse(right.isPointContainInDirection(center, leftDown), "Bottom left corner check");
        assertFalse(right.isPointContainInDirection(center, leftMiddle), "Left side middle check");
    }

    @Test
    public void DownSectorTest() {
        Sector down = new Sector(Directions.DOWN, rightAngle);
        assertTrue(down.isPointContainInDirection(center, new Point2D.Double(25, 35)), "(25, 35) check");

        assertFalse(down.isPointContainInDirection(center, leftUp), "Upper left corner check");
        assertFalse(down.isPointContainInDirection(center, middleUp), "Upside middle check");
        assertFalse(down.isPointContainInDirection(center, rightUp), "Upper right corner check");
        assertFalse(down.isPointContainInDirection(center, rightMiddle), "Right side middle check");
        assertTrue(down.isPointContainInDirection(center, rightDown), "Bottom right corner check");
        assertTrue(down.isPointContainInDirection(center, middleDown), "Bottom side middle check");
        assertTrue(down.isPointContainInDirection(center, leftDown), "Bottom left corner check");
        assertFalse(down.isPointContainInDirection(center, leftMiddle), "Left side middle check");
    }

    @Test
    public void leftSectorTest() {
        Sector left = new Sector(Directions.LEFT, rightAngle);
        assertTrue(left.isPointContainInDirection(center, new Point2D.Double(-25, 15)), "(15, 25) check");

        assertTrue(left.isPointContainInDirection(center, leftUp), "Upper left corner check");
        assertFalse(left.isPointContainInDirection(center, middleUp), "Upside middle check");
        assertFalse(left.isPointContainInDirection(center, rightUp), "Upper right corner check");
        assertFalse(left.isPointContainInDirection(center, rightMiddle), "Right side middle check");
        assertFalse(left.isPointContainInDirection(center, rightDown), "Bottom right corner check");
        assertFalse(left.isPointContainInDirection(center, middleDown), "Bottom side middle check");
        assertTrue(left.isPointContainInDirection(center, leftDown), "Bottom left corner check");
        assertTrue(left.isPointContainInDirection(center, leftMiddle), "Left side middle check");
    }


}
