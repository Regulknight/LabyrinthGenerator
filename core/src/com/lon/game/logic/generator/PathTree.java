package com.lon.game.logic.generator;

import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.logic.cell.Cell;
import com.lon.game.logic.cell.FloorCell;

import java.util.*;
import java.util.stream.Collectors;

public class PathTree {
    private int pathLength;
    private final World world;

    private final List<Cell> cellList = new LinkedList<>();
    private volatile List<PathTree> activeChildList = new LinkedList<>();

    private volatile List<PathTree> closedChildList = new LinkedList<>();

    private PathBuilder builder;

    public PathTree(Cell root, PathBuilder builder, int rootPosition, World world) {
        this.pathLength = rootPosition;
        this.cellList.add(root);
        this.builder = builder;
        this.world = world;
    }
    public Cell getRoot() {
        return this.cellList.get(0);
    }

    public Cell getTail() {
        return this.cellList.get(cellList.size() - 1);
    }

    public List<Cell> getCellList() {
        return cellList;
    }

    public boolean grow() {
        return growCurrentTree() || growActiveChildren() || growNewChildren();
    }

    private boolean growActiveChildren() {
        boolean result = false;

        for (PathTree child: activeChildList) {
            boolean growStatus = child.grow();
            if (!growStatus) {
                closedChildList.add(child);
            }
            result = result || growStatus;
        }

        activeChildList.removeAll(closedChildList);

        return result;
    }

    private boolean growCurrentTree() {
        boolean result = false;
        if (builder.isAbleToBuild(getTail())) {
            List<Cell> cellsForGrowing = new LinkedList<>();

            try {
                cellsForGrowing = builder.getCellsForGrowing(this);
            } catch (PathBuildException e) {
                e.printStackTrace();
            }

            for (Cell cell: cellsForGrowing) {
                cell.setType(new FloorCell());
                world.destroyBody(cell.getBody());
                cell.setBody(null);
                cell.setRemoteness(pathLength + cellList.size());
                cellList.add(cell);
                result = true;
            }
        }

        return result;
    }

    private boolean growNewChildren() {
        List<Cell> cellList = getCellForBranching();

        if (!cellList.isEmpty()) {
            Collections.shuffle(cellList);

            Cell root = cellList.get(0);

            if (root == null) return false;

            activeChildList.add(new PathTree(root, builder, pathLength + this.cellList.indexOf(root), world));

            return true;
        }

        return false;
    }

    private List<Cell> getCellForBranching() {
        List<Cell> result = new LinkedList<>();

        for (Cell cell : this.cellList) {
            if (builder.isAbleToBuild(cell)) {
                result.add(cell);
            }
        }

        for (PathTree activeChild: activeChildList) {
            result.addAll(activeChild.getCellForBranching());
        }

        for (PathTree closedChild: closedChildList) {
            result.addAll(closedChild.getCellForBranching());
        }

        return result;
    }

    public int getSize() {
        int result = 0;

        for (PathTree tree: activeChildList) {
            result += tree.getSize();
        }

        return cellList.size() + result;
    }

    public int getPathLength() {
        return this.pathLength;
    }

    public List<Cell> getTails() {
        List<Cell> tails = new LinkedList<>();

        tails.add(getTail());

        for (PathTree pathTree: activeChildList) {
            tails.addAll(pathTree.getTails());
        }

        for (PathTree pathTree: closedChildList) {
            tails.addAll(pathTree.getTails());
        }

        return tails;
    }

    public Cell generateExit() {
        List<Cell> candidates = new LinkedList<>();

        candidates.add(getTail());

        for (PathTree tree: closedChildList) {
            candidates.add(tree.getTail());
        }

        return candidates.stream().max(new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {
                return Integer.compare(o1.getRemoteness(), o2.getRemoteness());
            }
        }).get();
    }

}
