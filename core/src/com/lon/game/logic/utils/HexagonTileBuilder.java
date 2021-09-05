package com.lon.game.logic.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.logic.HexTextureMap;
import com.lon.game.logic.TextureMap;
import com.lon.game.logic.tile.Tile;
import com.lon.game.logic.tile.TileType;
import com.lon.game.logic.world.Hexagon;

import static com.lon.game.logic.utils.WorldConstants.TILE_SIZE;

public class HexagonTileBuilder implements TileBuilder{
    private TileType type;
    private Texture texture;

    public HexagonTileBuilder(TileType type) {
        this.type = type;

        HexTextureMap map = HexTextureMap.getInstance();

        this.texture = map.get(type.getTextureKey());
    }

    @Override
    public Tile createTile(World world, Vector2 position) {
        Body body = BodyBuilder.createHexagonTile(world, position);
        return new Hexagon(position, body, type, texture);
    }
}
