package com.lon.game.logic.generator;

import com.lon.game.logic.WorldMap;
import com.lon.game.logic.angle.Angle;
import com.lon.game.logic.cell.Cell;

import java.util.List;

public interface BranchGrowStrategy {
    List<Cell> getCellsForGrowing();
}
