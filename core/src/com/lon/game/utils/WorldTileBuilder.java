package com.lon.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.tile.Tile;
import com.lon.game.tile.TileType;

public abstract class WorldTileBuilder implements TileBuilder{
    protected World world;
    protected TileType type;

    public WorldTileBuilder(World world, TileType type) {
        this.world = world;
        this.type = type;
    }

    @Override
    public Tile createTile(Vector2 position) {
        Body tileBody = type.equals(TileType.WALL) ? createTileBody(position) : null;

        return createTile(world, position, tileBody, type);
    }

    protected abstract Tile createTile(World world, Vector2 position, Body tileBody, TileType type);

    protected abstract Body createTileBody(Vector2 position);
}
