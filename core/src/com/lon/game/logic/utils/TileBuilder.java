package com.lon.game.logic.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.logic.tile.Tile;

public interface TileBuilder {
    Tile createTile(World world, Vector2 position);
}
