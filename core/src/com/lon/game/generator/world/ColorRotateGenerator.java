package com.lon.game.generator.world;

import com.lon.game.tile.Tile;
import com.lon.game.tile.TileType;
import com.lon.game.world.TileGrid;

import java.util.List;

public class ColorRotateGenerator implements WorldStateGenerator{
	private static int counter = 0;
	private final List<TileType> purpleTypes = TileType.purple;
	private final List<TileType> complimentaryTypes = TileType.complementary;

	public boolean generationStep(TileGrid map) {
		List<List<Tile>> grid = map.getGrid();

		if (counter % 20 == 0) {
			for (List<Tile> row : grid) {
				for (Tile tile : row) {
					if (purpleTypes.contains(tile.getType()))
						tile.setType(getNext(tile.getType(), purpleTypes));
					if (complimentaryTypes.contains(tile.getType()))
						tile.setType(getNext(tile.getType(), complimentaryTypes));
				}
			}
		}

		counter++;

		return true;
	}

	private TileType getNext(TileType type, List<TileType> rotateSequence) {
		int rawIndex = rotateSequence.indexOf(type);
		if (rawIndex > -1) {
			int typeIndex = rawIndex > 0 ? (rawIndex - 1) % 5 : 4;
			return rotateSequence.get(typeIndex);
		}

		return type;
	}
}
