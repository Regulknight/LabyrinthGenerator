package com.lon.game.logic.generator;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.logic.WorldMap;
import com.lon.game.logic.angle.Angle;
import com.lon.game.logic.angle.Directions;
import com.lon.game.logic.area.ConeOfView;
import com.lon.game.logic.area.Sector;
import com.lon.game.logic.cell.Cell;
import com.lon.game.logic.cell.FloorCell;

import java.util.*;
import java.util.function.Predicate;

public class PathBuilder {
    private final double angleOfView = Math.PI/2.0;
    private final double coneRadius = 100;

    private final Map<Angle, ConeOfView> directionConesOfView = new HashMap<>();

    public PathBuilder() {
        for (Angle baseAngle: Directions.directionList()) {
            directionConesOfView.put(baseAngle, new ConeOfView(coneRadius, new Sector(baseAngle, angleOfView)));
        }
    }

    public PathBranch buildBranch(PathBranch branch, WorldMap map) throws PathBuildException {
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

        Cell newCell = new Cell(new Vector2(x, y), new FloorCell());
        branch.appendCell(newCell);
        map.setCell(newCell, x, y);

        return branch;
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

    public boolean isAbleToBuild(Cell cell, WorldMap map) {
        return !getAbleDirections(cell, map).isEmpty();
    }


}
