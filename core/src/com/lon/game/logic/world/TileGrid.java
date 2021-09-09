package com.lon.game.logic.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import com.lon.game.logic.area.Area;
import com.lon.game.logic.tile.Drawable;
import com.lon.game.logic.tile.Tile;
import com.lon.game.logic.utils.TileBuilder;

import java.util.LinkedList;
import java.util.List;

import static com.lon.game.logic.utils.WorldConstants.TILE_SIZE;

public class TileGrid implements Drawable {
    private final World world;
    private final int width;
    private final int height;
    private final TileBuilder tileBuilder;

    private final List<List<Tile>> grid;

    public TileGrid(int width, int height, World world, TileBuilder tileBuilder) {
        this.width = width;
        this.height = height;

        this.world = world;
        this.tileBuilder = tileBuilder;

        this.grid = initGrid();

    }

    private List<List<Tile>> initGrid() {
        List<List<Tile>> map = new LinkedList<>();

        for (int i = 0; i < height; i++) {
            List<Tile> row = new LinkedList<>();
            for (int j = 0; j < width; j++) {
                row.add(tileBuilder.createTile(world, new Vector2(j, i)));
            }
            map.add(row);
        }

        return map;
    }

    public List<Tile> getTilesFromArea(Vector2 areaAttachPoint, Area area) {
        List<Tile> result = new LinkedList<>();

        for (List<Tile> row: grid) {
            for (Tile tile : row) {
                if (area.isContainPoint(areaAttachPoint, tile.getPixelPosition()))
                    result.add(tile);
            }
        }

        return result;
    }

    public Tile getTile(int x, int y) {
        return grid.get(y).get(x);
    }

    public void setTile(Tile tile, int x, int y) {
        grid.get(y).set(x, tile);
    }

    public List<List<Tile>> getGrid() {
        return grid;
    }

    public void render(Batch batch) {
        for (List<Tile> row : grid) {
            for (Tile tile : row) {
                tile.render(batch);
            }
        }
    }
}
