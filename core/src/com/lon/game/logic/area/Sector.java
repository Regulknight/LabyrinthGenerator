package com.lon.game.logic.area;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.logic.angle.Angle;

public class Sector implements Area{
    private final Angle leftBorderAngle;
    private final Angle rightBorderAngle;

    private final boolean isCrossZeroAngle;

    public Sector(Angle baseAngle, double angleOfView) {
        leftBorderAngle = new Angle(baseAngle.getRadians() - angleOfView / 2);
        rightBorderAngle = new Angle(baseAngle.getRadians() + angleOfView / 2);

        //Strict of comparing depends on rounding while angle creation
        boolean isCrossZeroAngleFromTop = baseAngle.getRadians() - angleOfView / 2 < 0;
        boolean isCrossZeroAngleFromBottom = baseAngle.getRadians() + angleOfView / 2 >= 2 * Math.PI;
        isCrossZeroAngle = isCrossZeroAngleFromTop || isCrossZeroAngleFromBottom;
    }

    public boolean isContainPoint(Vector2 areaAttachPoint, Vector2 pointCoordinates) {
        double normalizedX = pointCoordinates.x - areaAttachPoint.x;
        double normalizedY = pointCoordinates.y - areaAttachPoint.y;

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
