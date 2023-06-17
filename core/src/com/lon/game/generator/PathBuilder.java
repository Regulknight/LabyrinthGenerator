package com.lon.game.generator;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.angle.Directions;
import com.lon.game.area.ConeOfView;
import com.lon.game.area.Sector;
import com.lon.game.generator.direction.DirectionChooser;
import com.lon.game.generator.direction.RotateDirectionChooser;
import com.lon.game.tile.Tile;
import com.lon.game.world.TileGrid;

import java.util.*;

import static com.lon.game.world.WorldConstants.TILE_SIZE;

public class PathBuilder {
    private final float angleOfView = (float) (Math.PI * 0.5);
    private final double coneRadius = TILE_SIZE * 2.5;

    private final Map<Float, ConeOfView> directionConesOfView = new HashMap<>();

    private final DirectionChooser chooser;

    public PathBuilder(DirectionChooser chooser) {
        this.chooser = chooser;

        for (Float baseAngle: Directions.directionList()) {
            directionConesOfView.put(baseAngle, new ConeOfView(coneRadius, new Sector(baseAngle, angleOfView)));
        }
    }

    public List<Tile> getCellsForGrowing(TileGrid map, PathTree branch) {
        List<Tile> result = new LinkedList<>();

        Tile tail = branch.getTail();
        List<Float> ableDirections = getAbleDirections(tail, map);

        if (!ableDirections.isEmpty()) {
            result.add(getNextCell(map, tail, chooser.chooseDirection(ableDirections)));
        }

        return result;
    }

    private List<Float> getAbleDirections(Tile tile, TileGrid map) {
        List<Float> result = new LinkedList<>();

        for(Map.Entry<Float, ConeOfView> entry: directionConesOfView.entrySet()) {
            List<Tile> tiles = map.getTilesFromArea(tile.getPixelPosition(), entry.getValue());
            tiles.remove(tile);
            boolean isAbleDirection = tiles.size() > 4 && tiles.stream().allMatch(Tile::isSolid);
            if (isAbleDirection) result.add(entry.getKey());
        }

        return result;
    }

    public boolean isAbleToBuild(TileGrid map, Tile tile) {
        return !getAbleDirections(tile, map).isEmpty();
    }

    private Tile getNextCell(TileGrid map, Tile tail, float direction) {
        Vector2 vec = new Vector2(1, 0);
        vec = vec.rotateRad(direction).add(tail.getGridPosition());

        int x = Math.round(vec.x);
        int y = Math.round(vec.y);

        return map.getTile(x, y);
    }
}
