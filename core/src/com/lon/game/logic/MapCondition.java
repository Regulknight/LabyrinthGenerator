package com.lon.game.logic;

import com.lon.game.logic.cell.Cell;

public interface MapCondition {
    boolean isValid(Cell cell);
}
