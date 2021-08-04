package com.lon.game.logic.cell;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.LGenGame;
import com.lon.game.logic.HasTexture;
import com.lon.game.logic.WorldMap;

import java.awt.*;

public class Cell implements HasTexture {
    private int x;
    private int y;
    private CellType type;
    private final String textureKey = "floor";

    public Cell(int x, int y, CellType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public boolean isSolid() {
        return type.isSolid();
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getTextureKey() {
        return textureKey;
    }

    public Vector2 getPosition() {
        return new Vector2(x + LGenGame.cellSize/2.f, y + LGenGame.cellSize/2.f);
    }

    //TODO rework with directions
    public boolean canGrowUp(WorldMap map) {
        return map.isFreeArea(x - 1, y + 1, x + 1, y + 2);
    }

    public boolean canGrowDown(WorldMap map) {
        return map.isFreeArea(x - 1, y - 2, x + 1, y - 1);
    }

    public boolean canGrowLeft(WorldMap map) {
        return map.isFreeArea(x - 2, y - 1, x - 1, y + 1);
    }

    public boolean canGrowRight(WorldMap map) {
        return map.isFreeArea(x + 1, y - 1, x + 2, y + 1);
    }
}
