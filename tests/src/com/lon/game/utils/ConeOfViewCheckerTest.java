package com.lon.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.angle.Directions;
import com.lon.game.area.ConeOfView;
import com.lon.game.area.Sector;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConeOfViewCheckerTest {
    private static final Vector2 center = new Vector2(25, 25);
    private static final float rightAngle = (float) (Math.PI / 2);

    @Test
    public void upperMiddleConeTest() {
        Sector up = new Sector(Directions.UP, rightAngle);
        ConeOfView upCone = new ConeOfView(2, up);

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                boolean result = (24 <= i && i <= 26 && j == 26) || (i == 25 && j == 25);

                Assert.assertEquals(i + " " + j, result, upCone.isContainPoint(center, new Vector2(i, j)));
            }
        }
    }

    @Test
    public void rightMiddleConeTest() {
        Sector right = new Sector(Directions.RIGHT, rightAngle);
        ConeOfView rightCone = new ConeOfView(2, right);

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                boolean result = (24 <= j && j <= 26 && i == 26) || (i == 25 && j == 25);

                Assert.assertEquals(i + " " + j, result, rightCone.isContainPoint(center, new Vector2(i, j)));
            }
        }
    }

    @Test
    public void downMiddleConeTest() {
        Sector down = new Sector(Directions.DOWN, rightAngle);
        ConeOfView downCone = new ConeOfView(2, down);

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                boolean result = (24 <= i && i <= 26 && j == 24) || (i == 25 && j == 25);

                Assert.assertEquals(i + " " + j, result, downCone.isContainPoint(center, new Vector2(i, j)));
            }
        }
    }

    @Test
    public void leftMiddleConeTest() {
        Sector left = new Sector(Directions.LEFT, rightAngle);
        ConeOfView leftCone = new ConeOfView(2, left);

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                boolean result = (24 <= j && j <= 26 && i == 24) || (i == 25 && j == 25);

                Assert.assertEquals(i + " " + j, result, leftCone.isContainPoint(center, new Vector2(i, j)));
            }
        }
    }
}
