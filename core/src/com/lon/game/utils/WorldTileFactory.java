package com.lon.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.tile.Tile;
import com.lon.game.tile.TileType;

public abstract class WorldTileFactory implements TileFactory {
    protected World world;

    public WorldTileFactory(World world) {
        this.world = world;
    }

    @Override
    public Tile createTile(Vector2 position, TileType type) {
        Body tileBody = isSolid(type) ? createTileBody(position) : null;

        return createTile(world, position, tileBody, type);
    }

    private boolean isSolid(TileType type) {
        return type == TileType.WALL || TileType.purple.contains(type);
    }

    protected abstract Tile createTile(World world, Vector2 position, Body tileBody, TileType type);

    protected abstract Body createTileBody(Vector2 position);
}
