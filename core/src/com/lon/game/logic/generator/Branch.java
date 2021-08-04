package com.lon.game.logic.generator;

import com.lon.game.logic.cell.Cell;

import java.util.LinkedList;
import java.util.List;

public class Branch {
    List<Cell> cellList = new LinkedList<>();

    public Branch(Cell root) {
        this.cellList.add(root);
    }
}
