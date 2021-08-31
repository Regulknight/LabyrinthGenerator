package com.lon.game.logic.tile;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.*;

import static com.lon.game.logic.utils.WorldConstants.TILE_SIZE;

public class Tile implements HasTexture {
    private Vector2 gridPosition;
    private TileType type;
    private TileType visionType;
    private Body body;
    private int remoteness;
    private int visionLevel;
    private boolean lighted;
    private Map<Integer, PointLight> playerLights;
    private List<Vector2> lightPoints;

    public Tile(Vector2 gridPosition, TileType type, Body body) {
        this.gridPosition = gridPosition;
        this.type = type;
        this.body = body;
        lightPoints = new LinkedList<>();
        lightPoints.add(new Vector2(getCenterX() - TILE_SIZE /5.f, getCenterY() + TILE_SIZE /5.f));
        lightPoints.add(new Vector2(getCenterX() + TILE_SIZE /5.f, getCenterY() + TILE_SIZE /5.f));
        lightPoints.add(new Vector2(getCenterX() - TILE_SIZE /5.f, getCenterY() - TILE_SIZE /5.f));
        lightPoints.add(new Vector2(getCenterX() + TILE_SIZE /5.f, getCenterY() - TILE_SIZE /5.f));

        this.playerLights = new HashMap<>();
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public boolean isSolid() {
        return type.isSolid();
    }


    public Vector2 getGridPosition() {
        return this.gridPosition;
    }

    public Texture getTexture() {
        return type.getTexture();
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

    public TileType getVisionType() {
        return visionType;
    }

    public void setVisionType(TileType visionType) {
        this.visionType = visionType;
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
}
