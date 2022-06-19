package com.lon.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.tile.Tile;

public interface TileFactory {
    Tile createTile(Vector2 position);
}
