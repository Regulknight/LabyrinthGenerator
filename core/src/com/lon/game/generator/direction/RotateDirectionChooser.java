package com.lon.game.generator.direction;

import java.util.List;

public class RotateDirectionChooser implements DirectionChooser{
	@Override
	public float chooseDirection(List<Float> directions) {
		return directions.get(0);
	}
}
