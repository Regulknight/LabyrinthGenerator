package com.lon.game.logic.generator;

import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.logic.tile.Tile;
import com.lon.game.logic.tile.FloorTile;

import java.util.*;

public class PathTree {
    private final int pathLength;
    private final World world;

    private final List<Tile> tileList = new LinkedList<>();
    private volatile List<PathTree> activeChildList = new LinkedList<>();

    private volatile List<PathTree> closedChildList = new LinkedList<>();

    private PathBuilder builder;

    public PathTree(Tile root, PathBuilder builder, int rootPosition, World world) {
        this.world = world;
        this.pathLength = rootPosition;

        add(root);

        this.builder = builder;
    }
    public Tile getRoot() {
        return this.tileList.get(0);
    }

    public Tile getTail() {
        return this.tileList.get(tileList.size() - 1);
    }

    public List<Tile> getTileList() {
        return tileList;
    }

    public void add(Tile tile) {
        tile.setType(new FloorTile());

        if (tile.getBody() != null) {
            world.destroyBody(tile.getBody());
        }

        tile.setBody(null);

        this.tileList.add(tile);
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
            List<Tile> cellsForGrowing = new LinkedList<>();

            try {
                cellsForGrowing = builder.getCellsForGrowing(this);
            } catch (PathBuildException e) {
                e.printStackTrace();
            }

            for (Tile tile : cellsForGrowing) {
                tile.setRemoteness(pathLength + tileList.size());
                add(tile);
                result = true;
            }
        }

        return result;
    }

    private boolean growNewChildren() {
        List<Tile> tileList = getTileForBranching();

        if (!tileList.isEmpty()) {
            Collections.shuffle(tileList);

            Tile root = tileList.get(0);

            if (root == null) return false;

            activeChildList.add(new PathTree(root, builder, root.getRemoteness(), world));

            return true;
        }

        return false;
    }

    private List<Tile> getTileForBranching() {
        List<Tile> result = new LinkedList<>();

        for (Tile tile : this.tileList) {
            if (builder.isAbleToBuild(tile)) {
                result.add(tile);
            }
        }

        for (PathTree activeChild: activeChildList) {
            result.addAll(activeChild.getTileForBranching());
        }

        for (PathTree closedChild: closedChildList) {
            result.addAll(closedChild.getTileForBranching());
        }

        return result;
    }

    public int getSize() {
        int result = 0;

        for (PathTree tree: activeChildList) {
            result += tree.getSize();
        }

        return tileList.size() + result;
    }

    public int getPathLength() {
        return this.pathLength;
    }


    public Tile generateExit() {
        List<Tile> candidates = new LinkedList<>();

        candidates.add(getTail());

        for (PathTree tree: closedChildList) {
            candidates.add(tree.getTail());
        }

        return candidates.stream().max(Comparator.comparingInt(Tile::getRemoteness)).get();
    }

}
