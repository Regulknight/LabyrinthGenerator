package com.lon.game.logic.generator;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.logic.cell.Cell;
import com.lon.game.logic.cell.CellType;

import java.util.List;

public class Path extends Cell{
    List<Path> paths;

    public Path(Vector2 gridPosition, CellType type) {
        super(gridPosition, type);
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void addPath(Path path) {
        paths.add(path);
    }
}
