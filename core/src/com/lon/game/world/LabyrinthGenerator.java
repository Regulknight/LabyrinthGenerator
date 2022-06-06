package com.lon.game.world;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.generator.NegativeMemoryPathBuilder;
import com.lon.game.generator.PathBuilder;
import com.lon.game.generator.PathTree;
import com.lon.game.tile.Tile;
import com.lon.game.tile.TileType;

public class LabyrinthGenerator{
    private final PathTree pathThree;
    private Tile exit;
    private boolean growingFlag = true;

    public LabyrinthGenerator(TileGrid grid, Tile startTile) {
        this.pathThree = new PathTree(startTile, new PathBuilder(grid), 0);
    }

    public boolean generationStep() {
        boolean result = false;

        if (growingFlag) {
            growingFlag = pathThree.grow();
            result = true;
        } else if (exit == null) {
            exit = pathThree.generateExit();
            exit.setType(TileType.EXIT);
            result = true;
        }

        return result;
    }

    public PathTree getPathThree() {
        return pathThree;
    }

    public boolean checkWinCondition(Vector2 playerPosition) {
        return exit != null && exit.containCoord(playerPosition);
    }
}
