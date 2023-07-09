package com.lon.game.area;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class PolygonArea implements Area{
	private final List<Vector2> points;

	public PolygonArea(List<Vector2> points) {
		this.points = points;
	}

	@Override
	public boolean isContainPoint(Vector2 areaAttachPoint, Vector2 pointCoordinates) {
		int intersectionCount = 0;

		for (int i = 1; i < points.size(); i++) {
			Sector sector = new Sector(points.get(i-1), points.get(i));

			if (sector.isContainPoint(areaAttachPoint, pointCoordinates)) {
				Vector2 edgeVector = new Vector2(points.get(i - 1).x - points.get(i).x, points.get(i - 1).y - points.get(i).y);
				Vector2 pointVector = new Vector2(pointCoordinates.x - areaAttachPoint.x, pointCoordinates.y - areaAttachPoint.y);

				intersectionCount++;
			}
		}

		return intersectionCount % 2 == 1;
	}
}
