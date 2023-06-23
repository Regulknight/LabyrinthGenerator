package com.lon.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.tile.Tile;
import com.lon.game.tile.TileType;

public interface TileFactory {
    Tile createTile(Vector2 position, TileType type);
}
