package com.lon.game.logic.generator;

import com.lon.game.logic.WorldMap;
import com.lon.game.logic.angle.Angle;
import com.lon.game.logic.angle.Directions;
import com.lon.game.logic.area.ConeOfView;
import com.lon.game.logic.area.Sector;
import com.lon.game.logic.cell.Cell;

import java.util.ArrayList;
import java.util.List;

public class LabyrinthBuilder {
    private double angleOfView = Math.PI/2.0;
    private double coneRadius = 100;

    private List<ConeOfView> directionConesOfView;
    private List<Branch> aliveBranches = new ArrayList<>();
    private List<BuildProcessListener> buildProcessListenerList = new ArrayList<>();
    private WorldMap map;

    public LabyrinthBuilder(int mapWidth, int mapHeight, int cellSize, Cell startCell, Cell endCell) {
        this.map = new WorldMap(mapWidth, mapHeight, cellSize);
        for (Angle angle: Directions.directionList()) {
            directionConesOfView.add(new ConeOfView(coneRadius, new Sector(angle, angleOfView)));
        }

        aliveBranches.add(new Branch(startCell));
    }

    public void attachListener(BuildProcessListener listener) {
        buildProcessListenerList.add(listener);
    }

    public void detachListener(BuildProcessListener listener) {
        buildProcessListenerList.remove(listener);
    }

    private void notifyBuildProcessListeners(WorldMap map) {
        for (BuildProcessListener listener: buildProcessListenerList) {
            listener.fireCurrentMapState(map);
        }
    }


}
