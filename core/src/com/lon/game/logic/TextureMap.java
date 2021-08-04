package com.lon.game.logic;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class TextureMap extends HashMap<String, Texture> {
    private static TextureMap instance;

    private TextureMap() {
        super();

        loadTexturesMapping();
    }

    public static TextureMap getInstance() {
        if (instance == null) {
            instance = new TextureMap();
        }
        return instance;
    }

    //TODO add mappingLoading
    private void loadTexturesMapping() {
        put("floor", new Texture("floor.png"));
        put("light", new Texture("light.png"));
        put("player", new Texture("player.png"));
        put("wall", new Texture("wall.png"));
    }

    public void dispose() {
        for (Texture texture: values()) {
            texture.dispose();
        }
    }
}
