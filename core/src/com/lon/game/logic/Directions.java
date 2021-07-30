package com.lon.game.logic;

import java.util.ArrayList;
import java.util.List;

public class Directions {
    public static final Angle RIGHT = new Angle(0);
    public static final Angle UP = new Angle(Math.PI / 2);
    public static final Angle LEFT = new Angle(Math.PI);
    public static final Angle DOWN = new Angle(- Math.PI / 2);

    public static List<Angle> directionList() {
        List<Angle> directionList = new ArrayList<>();
        directionList.add(RIGHT);
        directionList.add(UP);
        directionList.add(LEFT);
        directionList.add(DOWN);

        return directionList;
    }
}
