package com.lon.game.generator.direction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryDirectionChooser implements DirectionChooser{
	private final Map<Float, Long> memory = new HashMap<>();
	private Float favoriteDirection = 0.f;
	private Long max = 0L;

	@Override
	public float chooseDirection(List<Float> directions) {
		Long localMax = 0L;
		Float localMaxDirection = 0.f;

		for (Float direction: directions) {
			Long directionChoosingRate = memory.getOrDefault(direction, 0L) ;

			if (directionChoosingRate > localMax) {
				localMaxDirection = direction;
				localMax = directionChoosingRate;
			}

			if (directionChoosingRate + 1 > max) {
				memory.put(direction, directionChoosingRate + 1);
				favoriteDirection = direction;
				return favoriteDirection;
			}
		}

		memory.put(localMaxDirection, localMax + 1);
		return localMaxDirection;
	}
}
