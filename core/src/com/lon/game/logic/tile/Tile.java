package com.lon.game.logic.tile;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.lon.game.logic.TextureMap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.lon.game.logic.utils.WorldConstants.TILE_SIZE;

public abstract class Tile implements HasTexture {
    private Vector2 gridPosition;
    protected TileType type;
    protected Texture texture;
    private Body body;
    private int remoteness;
    private int visionLevel;
    private boolean lighted;
    private Map<Integer, PointLight> playerLights;
    protected List<Vector2> lightPoints;

    public Tile(Vector2 gridPosition, Body body, TileType type, Texture texture) {
        this.gridPosition = gridPosition;
        this.type = type;
        this.texture = texture;
        this.body = body;
        lightPoints = createLightsPositions();

        playerLights = new HashMap<>();
    }

    protected List<Vector2> createLightsPositions() {
        List<Vector2> result = new LinkedList<>();

        result.add(new Vector2(getCenterX() - TILE_SIZE /5.f, getCenterY() + TILE_SIZE /5.f));
        result.add(new Vector2(getCenterX() + TILE_SIZE /5.f, getCenterY() + TILE_SIZE /5.f));
        result.add(new Vector2(getCenterX() - TILE_SIZE /5.f, getCenterY() - TILE_SIZE /5.f));
        result.add(new Vector2(getCenterX() + TILE_SIZE /5.f, getCenterY() - TILE_SIZE /5.f));

        return result;
    };

    public boolean isSolid() {
        return body != null;
    }

    public Vector2 getGridPosition() {
        return this.gridPosition;
    }

    public Texture getTexture() {
        return this.texture;
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

    public int getVisionLevel() {
        return visionLevel;
    }

    public void setVisionLevel(int visionLevel) {
        this.visionLevel = visionLevel;
    }

    public boolean isLighted() {
        return lighted;
    }

    public void setLighted(boolean lighted) {
        this.lighted = lighted;
    }

    public PointLight addLight(int playerId, Color playerColor, RayHandler rayHandler) {
        PointLight result = null;

        if (!playerLights.containsKey(playerId)) {
            if (playerLights.isEmpty()) {
                result  = new PointLight(rayHandler, 30, playerColor, TILE_SIZE *0.8f, getCenterX(), getCenterY());
                playerLights.put(playerId, result);
            } else {
                int i = 0;
                for (Integer id: playerLights.keySet()) {
                    playerLights.get(id).setPosition(lightPoints.get(i));
                    i++;
                }

                result = new PointLight(rayHandler, 30, playerColor, TILE_SIZE *0.8f, lightPoints.get(i).x, lightPoints.get(i).y);
                playerLights.put(playerId, result);
            }
        }

        return result;

    }

    public void setType(TileType type) {
        this.type = type;
        this.texture = TextureMap.getInstance().get(type);
    }

    public TileType getType() {
        return this.type;
    }

    public void render(Batch batch) {
        batch.draw(texture, getX(), getY());
    }

    public boolean containCoord(Vector2 coord) {
        return Vector2.dst(getCenterX(), getCenterY(), coord.x, coord.y) < TILE_SIZE/4.f;
    }
}
