package com.lon.game.world;

import com.lon.game.utils.WorldTileFactory;

public class TileGridBuilder {
    private WorldTileFactory tileFactory;
    private int width;
    private int height;

    private int widthInc = 0;
    private int heightInc = 0;

    public TileGridBuilder(WorldTileFactory tileFactory, int width, int height) {
        this.tileFactory = tileFactory;
        this.width = width;
        this.height = height;
    }

    public WorldTileFactory getTileFactory() {
        return tileFactory;
    }

    public void setTileFactory(WorldTileFactory tileFactory) {
        this.tileFactory = tileFactory;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public TileGrid build() {
        TileGrid result = new TileGrid(width, height, tileFactory);

        width += widthInc;
        height += heightInc;

        return result;
    }

    public void setWidthInc(int widthInc) {
        this.widthInc = widthInc;
    }

    public void setHeightInc(int heightInc) {
        this.heightInc = heightInc;
    }
}
