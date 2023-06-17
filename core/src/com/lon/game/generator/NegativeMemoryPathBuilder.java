package com.lon.game.generator;

import com.lon.game.generator.direction.DirectionChooser;
import com.lon.game.tile.Tile;
import com.lon.game.world.TileGrid;

import java.util.HashSet;
import java.util.Set;

public class NegativeMemoryPathBuilder extends PathBuilder{
    public static final Set<Tile> excludedTiles = new HashSet<>();

    public NegativeMemoryPathBuilder(DirectionChooser chooser) {
        super(chooser);
    }

    @Override
    public boolean isAbleToBuild(TileGrid map, Tile tile) {
        if (excludedTiles.contains(tile))
            return false;

        boolean buildAbility = super.isAbleToBuild(map, tile);

        if (!buildAbility) {
            excludedTiles.add(tile);
        }

        return buildAbility;
    }

    public void reset() {
        excludedTiles.clear();
    }
}
