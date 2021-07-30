package com.lon.game.logic;

import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Point2D;

public class ConeOfView {
    private final double coneRadius;
    private final Sector sector;

    public ConeOfView(double coneRadius, Sector sector) {
        this.coneRadius = coneRadius;
        this.sector = sector;
    }

    public boolean isPointContainInCone(Vector2 viewer, Vector2 observer) {
        return sector.isPointContainInDirection(viewer, observer)
                && Point2D.distance(observer.x, observer.y, viewer.x, viewer.y) < coneRadius;
    }
}
