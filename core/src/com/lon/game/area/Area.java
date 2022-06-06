package com.lon.game.area;

import com.badlogic.gdx.math.Vector2;

public interface Area {
    boolean isContainPoint(Vector2 areaAttachPoint, Vector2 pointCoordinates);
}
