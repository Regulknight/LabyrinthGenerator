package com.lon.game.logic.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.logic.tile.Tile;
import com.lon.game.logic.tile.TileType;
import com.lon.game.logic.world.Hexagon;

public class HexagonTileBuilder implements TileBuilder{
    private TileType type;

    public HexagonTileBuilder(TileType type) {
        this.type = type;
    }

    @Override
    public Tile createTile(World world, Vector2 position) {
        Body body = null;
        if (type.equals(TileType.WALL))
            body = BodyBuilder.createHexagonTile(world, position);
        return new Hexagon(position, body, type);
    }
}
