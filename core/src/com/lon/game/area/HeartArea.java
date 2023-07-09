package com.lon.game.area;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;
import static java.lang.Math.cos;

public class HeartArea implements Area{
	private final PolygonArea area;

	public HeartArea(float k) {
		List<Vector2> points = new ArrayList<>();

		for (float t = 0; t < 2 * Math.PI; t +=0.1) {
			float x = (float) (16 * pow(sin(t),3)) * k;
			float y = (float) (13 * cos(t) - 5 * cos(2 * t)  - 2 * cos(3 * t) - cos(4 * t)) * k;

			points.add(new Vector2(x,y));
		}

		area = new PolygonArea(points);
	}

	@Override
	public boolean isContainPoint(Vector2 areaAttachPoint, Vector2 pointCoordinates) {
		return area.isContainPoint(areaAttachPoint, pointCoordinates);
	}
}
