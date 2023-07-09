package com.lon.game.world;

import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.generator.type.RandomTypeStrategy;
import com.lon.game.generator.type.TypeStrategy;
import com.lon.game.utils.*;

public class TileGridBuilder {
    private final TypeStrategy typeStrategy;
    private WorldTileFactory tileFactory;
    private int width;
    private int height;

    private int widthInc = 0;
    private int heightInc = 0;

    public TileGridBuilder(World world, GridType gridType, int width, int height) {
        if (gridType == GridType.SQUARE)
            tileFactory = new SquareTileFactory(world);
        else
            tileFactory = new HexagonTileFactory(world);

//        typeStrategy = new PurpleCircleTypeStrategy(width,height,3);
        typeStrategy = new RandomTypeStrategy();

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
        TileGrid result = new TileGrid(width, height, tileFactory, typeStrategy);

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
