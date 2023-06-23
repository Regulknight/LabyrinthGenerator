package com.lon.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class TextureMap extends HashMap<String, Texture> {
    protected static TextureMap instance;

    protected TextureMap() {
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
        put("floor2", new Texture("floor-2.png"));
        put("skybox", new Texture("skybox.png"));
        put("light", new Texture("light.png"));
        put("player", new Texture("player.png"));
        put("wall", new Texture("wall.png"));
        put("exit", new Texture("light.png"));
        put("shade", new Texture("shade.png"));
    }

    public void dispose() {
        for (Texture texture: values()) {
            texture.dispose();
        }
    }
}
