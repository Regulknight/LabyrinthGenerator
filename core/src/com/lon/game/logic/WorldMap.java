package com.lon.game.logic;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.logic.area.Area;
import com.lon.game.logic.area.ConeOfView;
import com.lon.game.logic.cell.Cell;
import com.lon.game.logic.cell.EmptyCell;

import java.util.LinkedList;
import java.util.List;

public class WorldMap {
    private final int width;
    private final int height;
    private final int cellSize;

    private final List<List<Cell>> map;

    public WorldMap(int width, int height, int cellSize) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;

        this.map = createMap();
    }

    private List<List<Cell>> createMap() {
        List<List<Cell>> map = new LinkedList<>();

        for (int i = 0; i < height; i++) {
            List<Cell> row = new LinkedList<>();
            for (int j = 0; j < width; j++) {
                row.add(new Cell(j * cellSize, i * cellSize, new EmptyCell()));
            }
            map.add(row);
        }

        return map;
    }

    public List<Cell> getCellsFromArea(Vector2 areaAttachPoint, Area area) {
        List<Cell> result = new LinkedList<>();

        //TODO Make cell check smarter
        for (List<Cell> row: map) {
            for (Cell cell: row) {
                if (area.isContainPoint(areaAttachPoint, cell.getPosition()))
                    result.add(cell);
            }
        }

        return result;
    }

    public Cell getCell(int x, int y) {
        return map.get(y).get(x);
    }

    public List<List<Cell>> getMap() {
        return map;
    }
}
