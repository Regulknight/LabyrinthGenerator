package com.lon.game.tile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.TextureMap;

import static com.lon.game.world.WorldConstants.TILE_SIZE;

public class Tile implements Drawable {
    private final World world;
    private final Vector2 gridPosition;
    protected TileType type;
    private Body body;
    private int remoteness;

    private boolean hasHistory;
    private TileType historyType;


    public Tile(World world, Vector2 gridPosition, Body body, TileType type) {
        this.world = world;
        this.gridPosition = gridPosition;
        this.type = type;
        this.body = body;
    }

    public boolean isSolid() {
        return body != null;
    }

    public Vector2 getGridPosition() {
        return this.gridPosition;
    }

    public Vector2 getPixelPosition() {
        return new Vector2((this.gridPosition.x + 1/2.f) * TILE_SIZE, (this.gridPosition.y + 1/2.f) * TILE_SIZE);
    }

    public float getX() {
        return this.gridPosition.x * TILE_SIZE;
    }

    public float getCenterX() {
        return getX() + TILE_SIZE /2.f;
    }

    public float getY() {
        return this.gridPosition.y * TILE_SIZE;
    }

    public float getCenterY() {
        return getY() + TILE_SIZE /2.f;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public int getRemoteness() {
        return remoteness;
    }

    public void setRemoteness(int remoteness) {
        this.remoteness = remoteness;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return this.type;
    }

    public void render(Batch batch) {
        TextureMap textureMap = TextureMap.getInstance();
        batch.draw(textureMap.get(type.getTextureKey()), getX(), getY(), TILE_SIZE, TILE_SIZE);
    }

    public boolean containCoord(Vector2 coord) {
        return Vector2.dst(getCenterX(), getCenterY(), coord.x, coord.y) < TILE_SIZE/4.f;
    }

    public void behavior(float deltaTime) {

    }

    public void destroyBody() {
        if (body != null) {
            world.destroyBody(body);
            body = null;
        }
    }
}
