package com.lon.game.logic.generator;

import com.lon.game.logic.WorldMap;
import com.lon.game.logic.cell.Cell;

public interface ExitCreationListener {
    void fireExitCreation(Cell exit);
}
