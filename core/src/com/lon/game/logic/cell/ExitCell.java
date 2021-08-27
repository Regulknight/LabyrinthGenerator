package com.lon.game.logic.cell;

public class ExitCell implements CellType{
    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public String getTextureKey() {
        return "exit";
    }
}
