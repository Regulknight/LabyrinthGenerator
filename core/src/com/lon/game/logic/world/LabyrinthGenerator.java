package com.lon.game.logic.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.logic.generator.PathBuilder;
import com.lon.game.logic.generator.PathTree;
import com.lon.game.logic.tile.Tile;
import com.lon.game.logic.tile.TileType;

import static com.lon.game.logic.utils.WorldConstants.GENERATION_CYCLE_TIME;

public class LabyrinthGenerator{
    private final PathTree pathThree;
    private Tile exit;
    private boolean growingFlag = true;
    private float generationTimer = 0;

    public LabyrinthGenerator(TileGrid grid, Tile startTile, World world) {
        this.pathThree = new PathTree(startTile, new PathBuilder(grid), 0, world);
    }

    public void generationStep(float delta) {
        generationTimer += delta;
        if (generationTimer > GENERATION_CYCLE_TIME) {
            generationTimer -= GENERATION_CYCLE_TIME;

            if (growingFlag) {
                growingFlag = pathThree.grow();
            } else if (exit == null) {
                exit = pathThree.generateExit();
                exit.setType(TileType.EXIT);
            }
        }
    }

    public PathTree getPathThree() {
        return pathThree;
    }

    public boolean checkWinCondition(Vector2 playerPosition) {
        return exit != null && exit.containCoord(playerPosition);
    }
}
