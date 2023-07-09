package com.lon.game.area;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.angle.Angle;

public class Sector implements Area{
    private final float leftBorderAngle;
    private final float rightBorderAngle;

    private final boolean isCrossZeroAngle;

    public Sector(float baseAngle, float angleOfView) {
        leftBorderAngle = Angle.normalizeAngle(baseAngle - angleOfView / 2);
        rightBorderAngle = Angle.normalizeAngle(baseAngle + angleOfView / 2);

        //Strict of comparing depends on rounding while angle creation
        boolean isCrossZeroAngleFromTop = baseAngle - angleOfView / 2 < 0;
        boolean isCrossZeroAngleFromBottom = baseAngle + angleOfView / 2 >= 2 * Math.PI;
        isCrossZeroAngle = isCrossZeroAngleFromTop || isCrossZeroAngleFromBottom;
    }

    public Sector(Vector2 first, Vector2 second) {
        leftBorderAngle = Angle.normalizeAngle(Angle.getAngle(first.x, first.y));
        rightBorderAngle = Angle.normalizeAngle(Angle.getAngle(second.x, second.y));

        isCrossZeroAngle =false;
    }

    public boolean isContainPoint(Vector2 areaAttachPoint, Vector2 pointCoordinates) {
        float normalizedX = pointCoordinates.x - areaAttachPoint.x;
        float normalizedY = pointCoordinates.y - areaAttachPoint.y;

        if (normalizedX == 0 && normalizedY == 0) {
            return true;
        }

        float viewAngle = Angle.getAngle(normalizedX, normalizedY);

        if (isCrossZeroAngle) {
            return Float.compare(viewAngle, rightBorderAngle) <= 0 || Float.compare(viewAngle, leftBorderAngle) >= 0;
        }

        return Float.compare(viewAngle, leftBorderAngle) >= 0 && Float.compare(viewAngle, rightBorderAngle) <= 0;
    }


}
