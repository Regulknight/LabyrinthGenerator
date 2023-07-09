package com.lon.game;

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
        // TODO Перенести ключи в константы
        put("floor", new Texture("hex-floor.png"));
        put("floor2", new Texture("hex-floor-2.png"));
        put("skybox", new Texture("hex-skybox.png"));
        put("light", new Texture("light.png"));
        put("player", new Texture("player.png"));
        put("wall", new Texture("hex-wall.png"));
        put("exit", new Texture("hex-exit.png"));
        put("shade", new Texture("shade.png"));
        put("tyre", new Texture("tyre.png"));
        put("purple-1", new Texture("colors/hex/purple-1.png"));
        put("purple-2", new Texture("colors/hex/purple-2.png"));
        put("purple-3", new Texture("colors/hex/purple-3.png"));
        put("purple-4", new Texture("colors/hex/purple-4.png"));
        put("purple-5", new Texture("colors/hex/purple-5.png"));
        put("complimentary-1", new Texture("colors/hex/complimentary-1.png"));
        put("complimentary-2", new Texture("colors/hex/complimentary-2.png"));
        put("complimentary-3", new Texture("colors/hex/complimentary-3.png"));
        put("complimentary-4", new Texture("colors/hex/complimentary-4.png"));
        put("complimentary-5", new Texture("colors/hex/complimentary-5.png"));
    }

    public void dispose() {
        for (Texture texture : values()) {
            texture.dispose();
        }
    }
}
