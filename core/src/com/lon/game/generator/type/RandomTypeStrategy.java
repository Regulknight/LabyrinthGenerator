package com.lon.game.generator.type;

import com.lon.game.tile.TileType;

import java.util.List;
import java.util.Random;

public class RandomTypeStrategy implements TypeStrategy{
	private final Random r;
	private final List<TileType> colors;

	public RandomTypeStrategy() {
		this.r = new Random();
		this.colors = TileType.purple;
	}

	@Override
	public TileType getType(int x, int y) {
		int tileIndex = r.nextInt(colors.size());
		return colors.get(tileIndex);
	}
}
