package com.lon.game.logic.cell;

public class EmptyCell implements CellType{
    @Override
    public boolean isSolid() {
        return false;
    }
}
