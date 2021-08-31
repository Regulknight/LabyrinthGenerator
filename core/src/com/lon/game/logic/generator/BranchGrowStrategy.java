package com.lon.game.logic.generator;

import com.lon.game.logic.tile.Tile;

import java.util.List;

public interface BranchGrowStrategy {
    List<Tile> getCellsForGrowing();
}
