package com.lon.game.logic.generator;

import com.lon.game.logic.WorldMap;
import com.lon.game.logic.angle.Angle;
import com.lon.game.logic.angle.Directions;
import com.lon.game.logic.area.ConeOfView;
import com.lon.game.logic.area.Sector;
import com.lon.game.logic.cell.Cell;
import com.lon.game.logic.cell.FloorCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LabyrinthGenerator {
    private final PathTree pathThree;
    private final PathBuilder pathBuilder;
    private final List<BuildProcessListener> buildProcessListenerList = new ArrayList<>();
    private final WorldMap map;

    public LabyrinthGenerator(WorldMap map, Cell startCell, Cell endCell) {
        this.map = map;

        Cell cell = new Cell(startCell.getGridPosition(), new FloorCell());
        map.setCell(cell, startCell.getX(), startCell.getY());
        this.pathThree = new PathTree(cell, map);

        this.pathBuilder = new PathBuilder();
    }

    public void generate() {

        for (PathBranch branch : pathThree.getActiveBranches()) {
            if (pathBuilder.isAbleToBuild(branch.getTail(), map)) {
                try {
                    pathBuilder.buildBranch(branch, map);
                } catch (PathBuildException e) {
                    e.printStackTrace();
                }
            } else {
                pathThree.closeBranch(branch);

                List<Cell> cells = new LinkedList<>();

                for (PathBranch branch1: pathThree.getBranches()) {
                    cells.addAll(branch1.getCellList());
                }

                Collections.shuffle(cells);
                for (Cell cell: cells) {
                    if (pathBuilder.isAbleToBuild(cell, map)) {
                        pathThree.createBranch(cell.getGridPosition());
                        return;
                    }
                }
            }
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


}
