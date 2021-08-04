package com.lon.game.logic.area;

import com.badlogic.gdx.math.Vector2;

public class ConeOfView implements Area{
    private final double coneRadius;
    private final Sector sector;

    public ConeOfView(double coneRadius, Sector sector) {
        this.coneRadius = coneRadius;
        this.sector = sector;
    }

    public boolean isContainPoint(Vector2 areaAttachPoint, Vector2 pointCoordinates) {
        return sector.isContainPoint(areaAttachPoint, pointCoordinates)
                && Vector2.dst(areaAttachPoint.x, areaAttachPoint.y, pointCoordinates.x, pointCoordinates.y) < coneRadius;
    }
}
