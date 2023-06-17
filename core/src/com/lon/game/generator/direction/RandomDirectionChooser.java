package com.lon.game.generator.direction;

import java.util.Collections;
import java.util.List;

public class RandomDirectionChooser implements DirectionChooser{
	@Override
	public float chooseDirection(List<Float> directions) {
		Collections.shuffle(directions);
		return directions.get(0);
	}
}
