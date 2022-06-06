package com.lon.game.angle;

import java.util.ArrayList;
import java.util.List;

public class Directions {
    public static final Float RIGHT = 0.f;
    public static final Float UP = (float) (Math.PI / 2.f);
    public static final Float LEFT = (float) Math.PI;
    public static final Float DOWN = (float) Math.PI * 1.5f;

    public static List<Float> directionList() {
        List<Float> directionList = new ArrayList<>();
        directionList.add(RIGHT);
        directionList.add(UP);
        directionList.add(LEFT);
        directionList.add(DOWN);

        return directionList;
    }
}
