package com.lon.game.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import com.lon.game.area.Area;
import com.lon.game.tile.Drawable;
import com.lon.game.tile.Tile;
import com.lon.game.tile.TileType;
import com.lon.game.utils.TileFactory;

import java.util.LinkedList;
import java.util.List;

public class TileGrid implements Drawable {
    private final int width;
    private final int height;
    private final TileFactory tileFactory;

    private List<List<Tile>> grid;

    public TileGrid(int width, int height, TileFactory tileFactory) {
        this.width = width;
        this.height = height;

        this.tileFactory = tileFactory;

        this.grid = initGrid();

    }

    private List<List<Tile>> initGrid() {
        List<List<Tile>> map = new LinkedList<>();

        for (int i = 0; i < height; i++) {
            List<Tile> row = new LinkedList<>();
            for (int j = 0; j < width; j++) {
                if(isCircle(i, j))
                    row.add(tileFactory.createTile(new Vector2(j, i), TileType.WALL));
                else
                    row.add(tileFactory.createTile(new Vector2(j, i), TileType.SKYBOX));
            }
            map.add(row);
        }

        return map;
    }

    private boolean isCircle(int i, int j) {
        float r  = Math.min(width, height) / 2.f;
        return Math.sqrt((height/2.f-i)*(height/2.f-i) + (width/2.f-j)*(width/2.f-j)) < 0.8*r;
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

    public void render(Batch batch) {
        for (List<Tile> row : grid) {
            for (Tile tile : row) {
                tile.render(batch);
            }
        }
    }

    public void destroy() {
        for (List<Tile> row: grid) {
            for (Tile tile : row) {
                tile.destroyBody();
            }
        }

        this.grid = null;
    }
}
