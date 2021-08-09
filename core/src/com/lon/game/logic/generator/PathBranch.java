package com.lon.game.logic.generator;

import com.lon.game.logic.cell.Cell;

import java.util.LinkedList;
import java.util.List;

public class PathBranch {
    private final List<Cell> cellList = new LinkedList<>();

    public PathBranch(Cell root) {
        this.cellList.add(root);
    }

    public Cell getRoot() {
        return this.cellList.get(0);
    }

    public Cell getTail() {
        return this.cellList.get(cellList.size() - 1);
    }

    public void appendCell(Cell cell) {
        cellList.add(cell);
    }

    public List<Cell> getCellList() {
        return cellList;
    }
}
