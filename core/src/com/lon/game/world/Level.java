package com.lon.game.world;

import com.lon.game.tile.Tile;
import com.lon.game.utils.TileBuilder;

import java.util.List;

public class Level {
    private TileGrid map;

    public Level(int gridWidth, int gridHeight, TileBuilder tileBuilder) {
        this.map = new TileGrid(gridWidth, gridHeight, tileBuilder);
    }

    public void destroy() {
        for (List<Tile> row: map.getGrid()) {
            for (Tile tile : row) {
                tile.destroyBody();
            }
        }

        this.map = null;
    }

    public TileGrid getMap() {
        return map;
    }
}
