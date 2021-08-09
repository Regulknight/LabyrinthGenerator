package com.lon.game.logic.cell;

public class WallCell implements CellType{
    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public String getTextureKey() {
        return "wall";
    }
}
