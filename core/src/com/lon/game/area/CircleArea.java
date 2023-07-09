package com.lon.game.area;

import com.badlogic.gdx.math.Vector2;

public class CircleArea implements Area{
	@Override
	public boolean isContainPoint(Vector2 areaAttachPoint, Vector2 pointCoordinates) {
		return false;
	}
}
