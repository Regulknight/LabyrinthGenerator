package com.lon.game.logic.generator;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.logic.world.TileGrid;
import com.lon.game.logic.angle.Directions;
import com.lon.game.logic.area.ConeOfView;
import com.lon.game.logic.area.Sector;
import com.lon.game.logic.tile.Tile;

import java.util.*;

import static com.lon.game.logic.utils.WorldConstants.TILE_SIZE;

public class PathBuilder {
    private final float angleOfView = (float) (Math.PI/2.0);
    private final double coneRadius = TILE_SIZE * 2.5;

    private final Map<Float, ConeOfView> directionConesOfView = new HashMap<>();
    private final TileGrid map;

    public PathBuilder(TileGrid map) {
        this.map = map;

        for (Float baseAngle: Directions.directionList()) {
            directionConesOfView.put(baseAngle, new ConeOfView(coneRadius, new Sector(baseAngle, angleOfView)));
        }
    }

    public List<Tile> getCellsForGrowing(PathTree branch) throws PathBuildException {
        List<Tile> result = new LinkedList<>();

        Tile tail = branch.getTail();

        List<Float> ableDirections = getAbleDirections(tail, map);
        if (ableDirections.isEmpty()) {
            throw new PathBuildException();
        }
        Collections.shuffle(ableDirections);

        Float angle = ableDirections.get(0);
        Vector2 vec = new Vector2(1, 0);
        vec = vec.rotateRad(angle).add(tail.getGridPosition());

        int x = Math.round(vec.x);
        int y = Math.round(vec.y);

        result.add(map.getTile(x, y));

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

    public boolean isAbleToBuild(Tile tile) {
        return !getAbleDirections(tile, map).isEmpty();
    }


}
