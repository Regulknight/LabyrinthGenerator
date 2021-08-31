package com.lon.game.logic.tile;

import com.badlogic.gdx.graphics.Texture;

public interface TileType {
    boolean isSolid();
    Texture getTexture();
}
