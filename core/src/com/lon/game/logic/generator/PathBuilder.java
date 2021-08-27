package com.lon.game.logic.generator;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.LGenGame;
import com.lon.game.logic.WorldMap;
import com.lon.game.logic.angle.Angle;
import com.lon.game.logic.angle.Directions;
import com.lon.game.logic.area.ConeOfView;
import com.lon.game.logic.area.Sector;
import com.lon.game.logic.cell.Cell;

import java.util.*;
import java.util.function.Predicate;

import static com.lon.game.logic.utils.Constatns.CELL_SIZE;

public class PathBuilder {
    private final double angleOfView = Math.PI/2.0;
    private final double coneRadius = CELL_SIZE * 2.5;

    private final Map<Angle, ConeOfView> directionConesOfView = new HashMap<>();
    private final WorldMap map;

    public PathBuilder(WorldMap map) {
        this.map = map;

        for (Angle baseAngle: Directions.directionList()) {
            directionConesOfView.put(baseAngle, new ConeOfView(coneRadius, new Sector(baseAngle, angleOfView)));
        }
    }

    public List<Cell> getCellsForGrowing(PathTree branch) throws PathBuildException {
        List<Cell> result = new LinkedList<>();

        Cell tail = branch.getTail();

        List<Angle> ableDirections = getAbleDirections(tail, map);
        if (ableDirections.isEmpty()) {
            throw new PathBuildException();
        }
        Collections.shuffle(ableDirections);

        Angle angle = ableDirections.get(0);
        Vector2 vec = new Vector2(1, 0);
        vec = vec.rotateRad((float) angle.getRadians()).add(tail.getGridPosition());

        int x = Math.round(vec.x);
        int y = Math.round(vec.y);

        result.add(map.getCell(x, y));

        return result;
    }

    private List<Angle> getAbleDirections(Cell cell, WorldMap map) {
        List<Angle> result = new LinkedList<>();

        for(Map.Entry<Angle, ConeOfView> entry: directionConesOfView.entrySet()) {
            List<Cell> cells = map.getCellsFromArea(cell.getPixelPosition(), entry.getValue());
            cells.remove(cell);
            boolean isAbleDirection = cells.size() > 1 && cells.stream().noneMatch(new Predicate<Cell>() {
                @Override
                public boolean test(Cell cell) {
                    return !cell.isSolid();
                }
            });
            if (isAbleDirection) result.add(entry.getKey());
        }

        return result;
    }

    public boolean isAbleToBuild(Cell cell) {
        return !getAbleDirections(cell, map).isEmpty();
    }


}
