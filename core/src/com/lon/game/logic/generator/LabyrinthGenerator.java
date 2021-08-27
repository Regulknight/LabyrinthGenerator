package com.lon.game.logic.generator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.logic.WorldMap;
import com.lon.game.logic.cell.Cell;
import com.lon.game.logic.cell.ExitCell;
import com.lon.game.logic.cell.FloorCell;

import java.util.ArrayList;
import java.util.List;

import static com.lon.game.logic.utils.Constatns.CELL_SIZE;

public class LabyrinthGenerator{
    private final PathTree pathThree;
    private final List<ExitCreationListener> exitCreationListeners = new ArrayList<>();
    private final WorldMap map;
    private final World world;
    private boolean growingFlag = true;
    private boolean exitFlag = false;
    private Cell exit;

    public LabyrinthGenerator(WorldMap map, Cell startCell, World world) {
        this.map = map;
        this.world = world;

        startCell.setType(new FloorCell());
        world.destroyBody(startCell.getBody());
        startCell.setBody(null);

        this.pathThree = new PathTree(startCell, new PathBuilder(map), 0, world);
    }

    public void step() {
        if (growingFlag) {
            growingFlag = pathThree.grow();
        } else if (exit == null){
            exit = pathThree.generateExit();
            exit.setType(new ExitCell());
            notifyExitCreationListeners(exit);
        }
    }

    public void attachListener(ExitCreationListener listener) {
        exitCreationListeners.add(listener);
    }

    public void detachListener(ExitCreationListener listener) {
        exitCreationListeners.remove(listener);
    }

    private void notifyExitCreationListeners(Cell exit) {
        for (ExitCreationListener listener : exitCreationListeners) {
            listener.fireExitCreation(exit);
        }
    }

    public PathTree getPathThree() {
        return pathThree;
    }

    public boolean checkWinCondition(Vector2 playerPosition) {
        return exit != null && playerPosition.dst(exit.getPixelPosition()) < CELL_SIZE/2.f;
    }
}
