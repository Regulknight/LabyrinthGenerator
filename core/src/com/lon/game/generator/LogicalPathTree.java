package com.lon.game.generator;

import com.lon.game.tile.Tile;
import com.lon.game.world.TileGrid;

public class LogicalPathTree extends PathTree{
	public LogicalPathTree(Tile root, PathBuilder builder, int rootPosition) {
		super(root, builder, rootPosition);
	}

	@Override
	public boolean grow(TileGrid map) {
		return growCurrentTree(map) | growActiveChildren(map) | growNewChildren(map);
	}

	@Override
	protected PathTree growNewChild(Tile root, PathBuilder builder, int remoteness) {
		return new LogicalPathTree(root, builder, remoteness);
	}
}
