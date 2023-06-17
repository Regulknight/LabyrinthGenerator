package com.lon.game.generator;

import com.lon.game.tile.Tile;
import com.lon.game.world.TileGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CounterPathTree extends PathTree{
	private int growCounter = 0;
	private int mainBranchCounter;
	private int newBranchCounter;
	private int activeGrowCounter;

	private List<Boolean> growStatusHistory;
	private int historySize;
	public CounterPathTree(
			Tile root,
			PathBuilder builder,
			int rootPosition,
			int mainBranchCounter,
			int newBranchCounter,
			int activeGrowCounter
	) {
		super(root, builder, rootPosition);

		this.mainBranchCounter = mainBranchCounter;
		this.newBranchCounter = newBranchCounter;
		this.activeGrowCounter = activeGrowCounter;

		historySize = Integer.max(Integer.max(mainBranchCounter, newBranchCounter), activeGrowCounter);

		growStatusHistory = new ArrayList<>(historySize);
		for (int i = 0; i < historySize; i++) {
			growStatusHistory.add(true);
		}
	}

	@Override
	public boolean grow(TileGrid map) {
		growCounter++;

		boolean status = false;

		if (growCounter % mainBranchCounter == 0)
			status |= growCurrentTree(map);
		if (growCounter % newBranchCounter == 0)
			status |= growNewChildren(map);
		if (growCounter % activeGrowCounter == 0)
			status |= growActiveChildren(map);

		growStatusHistory.set(growCounter % historySize, status);


		return growStatusHistory.stream().anyMatch(it -> it);
	}

	@Override
	protected PathTree growNewChild(Tile root, PathBuilder builder, int remoteness) {
		return new CounterPathTree(root, builder, remoteness, mainBranchCounter, newBranchCounter, activeGrowCounter);
	}
}
