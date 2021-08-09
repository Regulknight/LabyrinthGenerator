package com.lon.game.logic.generator;

import com.badlogic.gdx.math.Vector2;
import com.lon.game.logic.WorldMap;
import com.lon.game.logic.cell.Cell;
import com.lon.game.logic.cell.FloorCell;

import java.util.LinkedList;
import java.util.List;

public class PathTree {
    private final List<PathBranch> branches = new LinkedList<>();
    private final List<PathBranch> activeBranches = new LinkedList<>();
    private final WorldMap map;

    public PathTree(Cell root, WorldMap map) {
        this.map = map;
        PathBranch initBranch = new PathBranch(root);
        this.activeBranches.add(initBranch);
    }

    public boolean itHasActiveBranches() {
        return !activeBranches.isEmpty();
    }

    public void createBranch(Vector2 rootPosition) {
        Cell root = new Cell(rootPosition, new FloorCell());
        map.setCell(root, root.getX(), root.getY());
        activeBranches.add(new PathBranch(root));
    }

    public void closeBranch(PathBranch branch) {
        activeBranches.remove(branch);
        branches.add(branch);
    }

    public List<PathBranch> getBranches() {
        return branches;
    }

    public List<PathBranch> getActiveBranches() {
        return activeBranches;
    }
}
