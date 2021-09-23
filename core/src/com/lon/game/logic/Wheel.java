package com.lon.game.logic;

import com.badlogic.gdx.physics.box2d.Body;

public class Wheel {
    private final Body body;
    private final float radius;
    private float rotateSpeed;

    public Wheel(Body body, float radius) {
        this.body = body;
        this.radius = radius;
    }
}
