package com.lon.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.tile.Tile;
import com.lon.game.tile.TileType;

public class SquareTileFactory extends WorldTileFactory {

    public SquareTileFactory(World world, TileType type) {
        super(world, type);
    }

    @Override
    protected Tile createTile(World world, Vector2 position, Body tileBody, TileType type) {
        return new Tile(world, position, tileBody, type);
    }

    @Override
    protected Body createTileBody(Vector2 gridPosition) {
        return BodyFactory.createSquareTile(world, gridPosition);
    }
}
