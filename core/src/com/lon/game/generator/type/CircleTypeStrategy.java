package com.lon.game.generator.type;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.area.Area;
import com.lon.game.area.ConeOfView;
import com.lon.game.area.Sector;
import com.lon.game.tile.TileType;

public class CircleTypeStrategy implements TypeStrategy{
	protected final int width;
	protected final int height;

	protected Area circle;
	public CircleTypeStrategy(int width, int height) {
		this.width = width;
		this.height = height;

		float r  = Math.min(width, height) / 2.f;

		circle = new ConeOfView(r, new Sector(0.f, (float) (2 * Math.PI)));
	}

	@Override
	public TileType getType(int x, int y) {
		if (isCircle(x, y))
			return TileType.WALL;
		else
			return TileType.FLOOR;
	}

	protected boolean isCircle(int i, int j) {
		return circle.isContainPoint(new Vector2(width/2.f, height/2.f), new Vector2(i,j));
	}
}
