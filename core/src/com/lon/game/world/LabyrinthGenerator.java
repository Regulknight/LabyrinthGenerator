package com.lon.game.world;

import com.lon.game.generator.CounterPathTree;
import com.lon.game.generator.PathBuilder;
import com.lon.game.generator.PathTree;
import com.lon.game.tile.Tile;
import com.lon.game.tile.TileType;

public class LabyrinthGenerator{
    private final PathTree pathThree;
    private Tile exit;
    private boolean growingFlag = true;

    public LabyrinthGenerator(PathBuilder builder, Tile startTile) {
        this.pathThree = new CounterPathTree(startTile, builder, 0, 1, 8, 2);
    }

    public boolean generationStep(TileGrid map) {
        boolean result = false;

        if (growingFlag) {
            growingFlag = pathThree.grow(map);
            result = true;
        } else if (exit == null) {
            exit = pathThree.generateExit();
            exit.setType(TileType.EXIT);
            result = true;
        }

        return result;
    }
}
