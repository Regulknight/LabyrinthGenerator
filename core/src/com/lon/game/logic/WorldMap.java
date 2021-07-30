package com.lon.game.logic;

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

    public boolean isWallCell(int x, int y) {
        if ((x < 0 && x >= width) || (y < 0 && y >= height)) {
            return false;
        }

        return getCell(x, y).isSolid();
    }

    public boolean isFreeArea(int aX, int aY, int bX, int bY) {
        for (int i = aX; i < bX; i++) {
            for (int j = aY; j < bY; j++) {
                if (getCell(i, j).isSolid()) {
                    return false;
                }
            }
        }

        return true;
    }

    public Cell getCell(int x, int y) {
        return map.get(y).get(x);
    }

    public List<List<Cell>> getMap() {
        return map;
    }
}
