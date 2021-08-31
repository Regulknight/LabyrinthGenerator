package com.lon.game.logic.generator;

import com.lon.game.logic.tile.Tile;

public interface ExitCreationListener {
    void fireExitCreation(Tile exit);
}
