package com.lon.game.generator;

import com.lon.game.tile.Tile;
import com.lon.game.world.TileGrid;

import java.util.HashSet;
import java.util.Set;

public class NegativeMemoryPathBuilder extends PathBuilder{
    public static final Set<Tile> excludedTiles = new HashSet<>();

    public NegativeMemoryPathBuilder(TileGrid map) {
        super(map);
    }

    @Override
    public boolean isAbleToBuild(Tile tile) {
        if (excludedTiles.contains(tile))
            return false;

        boolean buildAbility = super.isAbleToBuild(tile);

        if (!buildAbility) {
            excludedTiles.add(tile);
        }

        return buildAbility;
    }
}
