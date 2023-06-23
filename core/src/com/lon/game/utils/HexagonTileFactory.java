package com.lon.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.tile.Tile;
import com.lon.game.tile.TileType;
import com.lon.game.tile.Hexagon;

public class HexagonTileFactory extends WorldTileFactory {

    public HexagonTileFactory(World world) {
        super(world);
    }

    @Override
    protected Tile createTile(World world, Vector2 position, Body tileBody, TileType type) {
        return new Hexagon(world, position, tileBody, type);
    }

    @Override
    protected Body createTileBody(Vector2 position) {
        return BodyFactory.createHexagonTile(world, position);
    }
}
