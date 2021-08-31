package com.lon.game.logic.world;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.logic.tile.Tile;
import com.lon.game.logic.world.TileGrid;
import com.lon.game.logic.utils.BodyBuilder;

import java.util.LinkedList;
import java.util.List;

import static com.lon.game.logic.utils.WorldConstants.TILE_SIZE;

public class Level {
    private World world;
    private TileGrid map;
    private List<Body> levelBorders = new LinkedList<>();

    public Level(int gridWidth, int gridHeight, World world) {
        this.world = world;
        this.map = new TileGrid(gridWidth, gridHeight, world);
        
        createBorders(gridWidth, gridHeight);
    }
    
    private void createBorders(int gridWidth, int gridHeight) {
        levelBorders.add(BodyBuilder.createBox(world, -TILE_SIZE /2.f - 5, -TILE_SIZE /2.f + gridHeight/2.f * TILE_SIZE, 10, gridHeight * TILE_SIZE + TILE_SIZE /2, true, true));
        levelBorders.add(BodyBuilder.createBox(world, gridWidth/2.f * TILE_SIZE - TILE_SIZE /2.f, -TILE_SIZE /2.f - 5, gridWidth * TILE_SIZE, 10, true, true));
        levelBorders.add(BodyBuilder.createBox(world, gridWidth * TILE_SIZE - TILE_SIZE /2.f + 5, -TILE_SIZE /2.f + gridHeight/2.f * TILE_SIZE, 10, gridHeight * TILE_SIZE + TILE_SIZE /2, true, true));
        levelBorders.add(BodyBuilder.createBox(world, gridWidth/2.f * TILE_SIZE - TILE_SIZE /2.f, gridHeight * TILE_SIZE - TILE_SIZE /2.f + 5, gridWidth * TILE_SIZE, 10, true, true));
    }

    public void destroy() {
        for (Body body: levelBorders) {
            this.world.destroyBody(body);
        }

        for (List<Tile> row: map.getGrid()) {
            for (Tile tile : row) {
                if (tile.getBody() != null) {
                    this.world.destroyBody(tile.getBody());
                }
            }
        }

        this.world = null;
        this.map = null;
    }

    public TileGrid getMap() {
        return map;
    }
}
