package com.lon.game.logic.cell;

public class FloorCell implements CellType{
    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public String getTextureKey() {
        return "floor";
    }
}
