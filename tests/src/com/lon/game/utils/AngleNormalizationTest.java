package com.lon.game.utils;

import com.lon.game.angle.Angle;
import com.lon.game.angle.Directions;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AngleNormalizationTest {

    @Test
    public void checkAboveDoublePiNormalization() {
        float angle = Angle.normalizeAngle(Math.PI * 5 / 2);

        Assert.assertEquals(Directions.UP, angle, 0.00001);
    }

    @Test
    public void checkBelowZeroPiNormalization() {
        float angle = Angle.normalizeAngle(-Math.PI / 2);

        Assert.assertEquals(Directions.DOWN, angle, 0.00001);
    }

    @Test
    public void checkPiNormalization() {
        float angle = Angle.normalizeAngle(Math.PI * 3);

        Assert.assertEquals(Directions.LEFT, angle, 0.00001);
    }

    @Test
    public void checkMinusPiNormalization() {
        float angle = Angle.normalizeAngle(-Math.PI * 3);

        Assert.assertEquals(Directions.LEFT, angle, 0.00001);
    }

}
