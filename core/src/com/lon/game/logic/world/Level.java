package com.lon.game.logic.world;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.logic.tile.Tile;
import com.lon.game.logic.tile.TileType;
import com.lon.game.logic.utils.HexagonTileBuilder;

import java.util.LinkedList;
import java.util.List;

public class Level {
    private World world;
    private TileGrid map;
    private final List<Body> levelBorders = new LinkedList<>();

    public Level(int gridWidth, int gridHeight, World world) {
        this.world = world;
        this.map = new TileGrid(gridWidth, gridHeight, world, new HexagonTileBuilder(TileType.WALL));
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
