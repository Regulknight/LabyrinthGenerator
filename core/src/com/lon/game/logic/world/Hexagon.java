package com.lon.game.logic.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.lon.game.logic.HexTextureMap;
import com.lon.game.logic.tile.Tile;
import com.lon.game.logic.tile.TileType;

import static com.lon.game.logic.world.WorldConstants.TILE_SIZE;

public class Hexagon extends Tile {
    private final float h = (float) Math.pow(TILE_SIZE/2.f * TILE_SIZE/2.f - TILE_SIZE/4.f * TILE_SIZE/4.f, 1/2.f);

    public Hexagon(Vector2 gridPosition, Body body, TileType type) {
        super(gridPosition, body, type);
    }

    public boolean containCoord(Vector2 point) {
        return Vector2.dst(point.x, point.y, getCenterX(), getCenterY()) <= h/2.f;
    }

    @Override
    public float getX() {
        return getGridPosition().x % 2 == 0 ? getGridPosition().x * TILE_SIZE * 0.75f : 0.75f * TILE_SIZE + TILE_SIZE * 0.75f * (getGridPosition().x - 1);
    }

    @Override
    public float getCenterX() {
        return getX() + TILE_SIZE /2.f ;
    }

    @Override
    public float getCenterY() {
        return getY() + h;
    }

    @Override
    public float getY() {
        return getGridPosition().x % 2 == 0 ? getGridPosition().y * 2 * h : getGridPosition().y * 2 * h + h;
    }

    @Override
    public void setType(TileType type) {
        this.type = type;
    }

    public void render(Batch batch) {
        HexTextureMap textureMap = HexTextureMap.getInstance();
        batch.draw(textureMap.get(type.getTextureKey()), getX(), getY(), TILE_SIZE,2 * h);
    }
}
