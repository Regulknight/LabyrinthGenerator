package com.lon.game.logic;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class HexTextureMap extends HashMap<String, Texture> {

    protected static HexTextureMap instance;

    public HexTextureMap() {
        super();

        loadTexturesMapping();
    }

    public static HexTextureMap getInstance() {
        if (instance == null) {
            instance = new HexTextureMap();
        }
        return instance;
    }

    //TODO add mappingLoading
    private void loadTexturesMapping() {
        put("floor", new Texture("hex-floor.png"));
        put("floor2", new Texture("hex-floor-2.png"));
        put("light", new Texture("light.png"));
        put("player", new Texture("player.png"));
        put("wall", new Texture("hex-wall.png"));
        put("exit", new Texture("hex-exit.png"));
        put("shade", new Texture("shade.png"));
    }

    public void dispose() {
        for (Texture texture : values()) {
            texture.dispose();
        }
    }
}
