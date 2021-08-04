package com.lon.game.logic.generator;

import com.lon.game.logic.WorldMap;

public interface BuildProcessListener {
    void fireCurrentMapState(WorldMap map);
}
