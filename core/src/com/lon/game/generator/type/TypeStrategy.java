package com.lon.game.generator.type;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.tile.TileType;

public interface TypeStrategy {
	TileType getType(int x, int y);
}
