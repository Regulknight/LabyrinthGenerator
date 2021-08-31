package com.lon.game.logic.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.logic.generator.ExitCreationListener;
import com.lon.game.logic.generator.PathBuilder;
import com.lon.game.logic.generator.PathTree;
import com.lon.game.logic.tile.ExitTile;
import com.lon.game.logic.tile.Tile;
import com.lon.game.logic.tile.FloorTile;

import java.util.ArrayList;
import java.util.List;

import static com.lon.game.logic.utils.WorldConstants.TILE_SIZE;
import static com.lon.game.logic.utils.WorldConstants.GENERATION_CYCLE_TIME;

public class LabyrinthGenerator{
    private final PathTree pathThree;
    private final List<ExitCreationListener> exitCreationListeners = new ArrayList<>();
    private final TileGrid grid;
    private final World world;
    private boolean growingFlag = true;
    private Tile exit;
    private float generationTimer = 0;

    public LabyrinthGenerator(TileGrid grid, Tile startTile, World world) {
        this.grid = grid;
        this.world = world;



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
                exit.setType(new ExitTile());
                notifyExitCreationListeners(exit);
            }
        }
    }

    public void attachListener(ExitCreationListener listener) {
        exitCreationListeners.add(listener);
    }

    public void detachListener(ExitCreationListener listener) {
        exitCreationListeners.remove(listener);
    }

    private void notifyExitCreationListeners(Tile exit) {
        for (ExitCreationListener listener : exitCreationListeners) {
            listener.fireExitCreation(exit);
        }
    }

    public PathTree getPathThree() {
        return pathThree;
    }

    public boolean checkWinCondition(Vector2 playerPosition) {
        return exit != null && playerPosition.dst(exit.getPixelPosition()) < TILE_SIZE /2.f;
    }
}
