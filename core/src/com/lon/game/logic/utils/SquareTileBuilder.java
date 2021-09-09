package com.lon.game.logic.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.logic.TextureMap;
import com.lon.game.logic.tile.Tile;
import com.lon.game.logic.tile.TileType;

public class SquareTileBuilder implements TileBuilder{
    private TileType type;
    private Texture texture;

    public SquareTileBuilder(TileType type) {
        this.type = type;

        TextureMap map = TextureMap.getInstance();

        this.texture = map.get(type.getTextureKey());
    }

    @Override
    public Tile createTile(World world, Vector2 gridPosition) {
        Body tileBody = BodyBuilder.createSquareTile(world, gridPosition);
        return new Tile(gridPosition, tileBody, type);
    }
}
