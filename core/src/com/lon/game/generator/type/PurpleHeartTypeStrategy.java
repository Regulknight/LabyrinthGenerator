package com.lon.game.generator.type;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.area.Area;
import com.lon.game.area.HeartArea;
import com.lon.game.tile.TileType;

public class PurpleHeartTypeStrategy extends PurpleCircleTypeStrategy {
	private final int a;
	private final Area heartArea;

	public PurpleHeartTypeStrategy(int width, int height, int a) {
		super(width, height, a);
		heartArea = new HeartArea(0.000001f);
		this.a = a;
	}

	@Override
	public TileType getType(int x, int y) {
		if (isHeart(x, y)) {
			int color = (int) (getDistance(x, y) / a % 5);
			return TileType.purple.get(color);
		}

		return TileType.SKYBOX;
	}

	private boolean isHeart(int x, int y) {
		return heartArea.isContainPoint(new Vector2(width/2.f, height/2.f), new Vector2(x,y));
	}
}
