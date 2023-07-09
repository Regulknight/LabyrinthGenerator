package com.lon.game.generator.world;

import com.lon.game.tile.TileType;
import com.lon.game.world.TileGrid;

public interface WorldStateGenerator {
	boolean generationStep(TileGrid map);
}
