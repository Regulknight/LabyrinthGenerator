package com.lon.game.tile;

import java.util.List;

public enum TileType {
    EXIT("exit"),
    FLOOR("floor"),
    FLOOR2("floor2"),
    SKYBOX("skybox"),
    WALL("wall"),
    PURPLE_1("purple-1"),
    PURPLE_2("purple-2"),
    PURPLE_3("purple-3"),
    PURPLE_4("purple-4"),
    PURPLE_5("purple-5"),
    COMPLIMENTARY_1("complimentary-1"),
    COMPLIMENTARY_2("complimentary-2"),
    COMPLIMENTARY_3("complimentary-3"),
    COMPLIMENTARY_4("complimentary-4"),
    COMPLIMENTARY_5("complimentary-5");

    public final static List<TileType> purple = getPurple();
    public final static List<TileType> complementary = getComplementary();

    private final String textureKey;

    TileType(String textureKey) {
        this.textureKey = textureKey;
    }

    public String getTextureKey() {
        return textureKey;
    }

    private static List<TileType> getPurple() {
        return List.of(PURPLE_1, PURPLE_2, PURPLE_3, PURPLE_4, PURPLE_5);
    }

    private static List<TileType> getComplementary() {
        return List.of(COMPLIMENTARY_1, COMPLIMENTARY_2, COMPLIMENTARY_3, COMPLIMENTARY_4, COMPLIMENTARY_5);
    }

}
