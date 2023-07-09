package com.lon.game.generator.type;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.tile.TileType;

public class PurpleCircleTypeStrategy extends CircleTypeStrategy{
	private final float r;
	public PurpleCircleTypeStrategy(int width, int height, float r) {
		super(width, height);
		this.r = r;
	}

	@Override
	public TileType getType(int x, int y) {
		if (isCircle(x, y)) {
			int color = (int) (getDistance(x, y) / r % 5);
				return TileType.purple.get(color);
		}

		return TileType.SKYBOX;
	}

	protected float getDistance(int x, int y) {
		return (int) Vector2.dst(width/2.f, height/2.f, x, y);
	}
}
