package com.lon.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.angle.Directions;
import com.lon.game.area.Sector;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class SectorCheckerTest {
    private static final float rightAngle = (float) (Math.PI / 2);

    private static final Vector2 center = new Vector2(25, 25);

    public static final Vector2 rightMiddle = new Vector2(50, 25);
    public static final Vector2 rightUp = new Vector2(50, 50);
    public static final Vector2 middleUp = new Vector2(25, 50);
    public static final Vector2 leftUp = new Vector2(0, 50);
    public static final Vector2 leftMiddle = new Vector2(0, 25);
    public static final Vector2 leftDown = new Vector2(0, 0);
    public static final Vector2 middleDown = new Vector2(25, 0);
    public static final Vector2 rightDown = new Vector2(50, 0);

    @Test
    public void upSectorTest() {
        Sector up = new Sector(Directions.UP, rightAngle);

        assertTrue("(25, 15) check", up.isContainPoint(center, new Vector2(25, 35)));

        assertTrue("Upper left corner check", up.isContainPoint(center, leftUp));
        assertTrue("Upside middle check", up.isContainPoint(center, middleUp));
        assertTrue("Upper right corner check", up.isContainPoint(center, rightUp));
        assertFalse("Right side middle check", up.isContainPoint(center, rightMiddle));
        assertFalse("Bottom right corner check", up.isContainPoint(center, rightDown));
        assertFalse("Bottom side middle check", up.isContainPoint(center, middleDown));
        assertFalse("Bottom left corner check", up.isContainPoint(center, leftDown));
        assertFalse("Left side middle check", up.isContainPoint(center, leftMiddle));
    }

    @Test
    public void rightSectorTest() {
        Sector right = new Sector(Directions.RIGHT, rightAngle);
        assertTrue("(35, 25) check", right.isContainPoint(center, new Vector2(35, 25)));

        assertFalse("Upper left corner check", right.isContainPoint(center, leftUp));
        assertFalse("Upside middle check", right.isContainPoint(center, middleUp));
        assertTrue("Upper right corner check", right.isContainPoint(center, rightUp));
        assertTrue("Right side middle check", right.isContainPoint(center, rightMiddle));
        assertTrue("Bottom right corner check", right.isContainPoint(center, rightDown));
        assertFalse("Bottom side middle check", right.isContainPoint(center, middleDown));
        assertFalse("Bottom left corner check", right.isContainPoint(center, leftDown));
        assertFalse("Left side middle check", right.isContainPoint(center, leftMiddle));
    }

    @Test
    public void downSectorTest() {
        Sector down = new Sector(Directions.DOWN, rightAngle);
        assertTrue("(25, 35) check", down.isContainPoint(center, new Vector2(25, 15)));

        assertFalse("Upper left corner check", down.isContainPoint(center, leftUp));
        assertFalse("Upside middle check", down.isContainPoint(center, middleUp));
        assertFalse("Upper right corner check", down.isContainPoint(center, rightUp));
        assertFalse("Right side middle check", down.isContainPoint(center, rightMiddle));
        assertTrue("Bottom right corner check", down.isContainPoint(center, rightDown));
        assertTrue("Bottom side middle check", down.isContainPoint(center, middleDown));
        assertTrue("Bottom left corner check", down.isContainPoint(center, leftDown));
        assertFalse("Left side middle check", down.isContainPoint(center, leftMiddle));
    }

    @Test
    public void leftSectorTest() {
        Sector left = new Sector(Directions.LEFT, rightAngle);
        assertTrue("(15, 25) check", left.isContainPoint(center, new Vector2(-25, 15)));

        assertTrue("Upper left corner check", left.isContainPoint(center, leftUp));
        assertFalse("Upside middle check", left.isContainPoint(center, middleUp));
        assertFalse("Upper right corner check", left.isContainPoint(center, rightUp));
        assertFalse("Right side middle check", left.isContainPoint(center, rightMiddle));
        assertFalse("Bottom right corner check", left.isContainPoint(center, rightDown));
        assertFalse("Bottom side middle check", left.isContainPoint(center, middleDown));
        assertTrue("Bottom left corner check", left.isContainPoint(center, leftDown));
        assertTrue("Left side middle check", left.isContainPoint(center, leftMiddle));
    }


}
