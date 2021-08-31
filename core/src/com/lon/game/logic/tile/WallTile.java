package com.lon.game.logic.tile;

import com.badlogic.gdx.graphics.Texture;
import com.lon.game.logic.TextureMap;

public class WallTile implements TileType {
    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public Texture getTexture() {
        return TextureMap.getInstance().get("wall");
    }
}
