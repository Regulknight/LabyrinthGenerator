package com.lon.game.logic.cell;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.LGenGame;
import com.lon.game.logic.HasTexture;
import com.lon.game.logic.WorldMap;

import java.awt.*;

public class Cell implements HasTexture {
    private Vector2 gridPosition;
    private CellType type;

    public Cell(Vector2 gridPosition, CellType type) {
        this.gridPosition = gridPosition;
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


    public Vector2 getGridPosition() {
        return this.gridPosition;
    }

    public String getTextureKey() {
        return type.getTextureKey();
    }

    public Vector2 getPixelPosition() {
        return new Vector2((this.gridPosition.x + 1/2.f) * LGenGame.cellSize, (this.gridPosition.y + 1/2.f) * LGenGame.cellSize);
    }

    public int getX() {
        return Math.round(this.gridPosition.x);
    }

    public int getY() {
        return Math.round(this.gridPosition.y);
    }

//    //TODO rework with directions
//    public boolean canGrowUp(WorldMap map) {
//        return map.isFreeArea(x - 1, y + 1, x + 1, y + 2);
//    }
//
//    public boolean canGrowDown(WorldMap map) {
//        return map.isFreeArea(x - 1, y - 2, x + 1, y - 1);
//    }
//
//    public boolean canGrowLeft(WorldMap map) {
//        return map.isFreeArea(x - 2, y - 1, x - 1, y + 1);
//    }
//
//    public boolean canGrowRight(WorldMap map) {
//        return map.isFreeArea(x + 1, y - 1, x + 2, y + 1);
//    }
}
