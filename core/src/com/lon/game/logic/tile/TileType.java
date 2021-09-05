package com.lon.game.logic.tile;

import com.badlogic.gdx.graphics.Texture;

public enum TileType {
    EXIT("exit"),
    FLOOR("floor"),
    FLOOR2("floor2"),
    WALL("wall"),
    ADD_VISION("add_vision"),
    KEY("key");

    private String textureKey;

    TileType(String textureKey) {
        this.textureKey = textureKey;
    }

    public String getTextureKey() {
        return textureKey;
    }
}
