package com.lon.game.logic;

public class World {
    private final WorldMap map;

    public World(WorldMap map) {
        this.map = map;
    }

    public WorldMap getCellMap() {
        return this.map;
    }
}
