package com.lon.game.tile;

public enum TileType {
    EXIT("exit"),
    FLOOR("floor"),
    FLOOR2("floor2"),
    SKYBOX("skybox"),
    WALL("wall");

    private final String textureKey;

    TileType(String textureKey) {
        this.textureKey = textureKey;
    }

    public String getTextureKey() {
        return textureKey;
    }
}
