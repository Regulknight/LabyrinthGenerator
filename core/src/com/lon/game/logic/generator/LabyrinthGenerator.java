package com.lon.game.logic.generator;

import com.lon.game.logic.WorldMap;
import com.lon.game.logic.cell.Cell;
import com.lon.game.logic.cell.FloorCell;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LabyrinthGenerator implements Runnable{
    private final PathTree pathThree;
    private final List<BuildProcessListener> buildProcessListenerList = new ArrayList<>();
    private final WorldMap map;

    public LabyrinthGenerator(WorldMap map, Cell startCell, Cell endCell) {
        this.map = map;

        Cell cell = new Cell(startCell.getGridPosition(), new FloorCell());
        map.setCell(cell, startCell.getX(), startCell.getY());
        this.pathThree = new PathTree(cell, new PathBuilder(map), 0);
    }

    public void run() {
        while (pathThree.grow()) {
        }
    }

    public void attachListener(BuildProcessListener listener) {
        buildProcessListenerList.add(listener);
    }

    public void detachListener(BuildProcessListener listener) {
        buildProcessListenerList.remove(listener);
    }

    private void notifyBuildProcessListeners(WorldMap map) {
        for (BuildProcessListener listener : buildProcessListenerList) {
            listener.fireCurrentMapState(map);
        }
    }

    public PathTree getPathThree() {
        return pathThree;
    }
}
