package com.lon.game.logic.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.lon.game.logic.tile.Tile;
import com.lon.game.logic.tile.TileType;

public class Cell extends Tile {
    public Cell(Vector2 gridPosition, Body body, TileType type, Texture texture) {
        super(gridPosition, body, type, texture);
    }
}
