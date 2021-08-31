package com.lon.game.logic.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.logic.angle.Angle;
import com.lon.game.logic.utils.BodyBuilder;

import static com.lon.game.logic.world.PlayerConstant.*;

public class Player {
    private final Body body;
    private final Texture texture;

    public Player(World world, Texture texture) {
        this.body = BodyBuilder.createBox(world, 0, 0, PLAYER_SIZE, PLAYER_SIZE, false, false);
        this.texture = texture;
        this.body.setActive(true);
        this.body.setAwake(true);
        this.body.setLinearDamping(20f);
        this.body.setAngularDamping(10f);
    }

    public Vector2 getPosition() {
        return body.getPosition().cpy().add(-PLAYER_SIZE/2.f, -PLAYER_SIZE/2.f);
    }

    public Vector2 getCenter() {
        return getPosition().cpy();
    }

    public float getX() {
        return getPosition().x;
    }

    public float getY() {
        return getPosition().y;
    }

    public void setAngle(float radians) {
        body.setTransform(getPosition(), radians);
    }

    public float getAngleDeg() {
        return Angle.convertInDeg(body.getAngle());
    }

    public float getAngleRad() {
        return body.getAngle();
    }

    public Body getBody() {
        return body;
    }

    public void render(Batch batch) {
        float halfSize = PLAYER_SIZE/2.f;
        batch.draw(texture, getX(), getY(), halfSize, halfSize, PLAYER_SIZE, PLAYER_SIZE, 1, 1, getAngleDeg(), 1, 1, 50, 50, false, false);
    }

    public void move(float delta) {
        body.setLinearVelocity((new Vector2(PLAYER_LINEAR_SPEED * delta, 0)).rotateAroundRad(Vector2.Zero, getAngleRad()));
    }

    public void rotate(float delta) {
        body.setAngularVelocity(PLAYER_ANGLE_SPEED * delta);
    }

    public void setTransform(Vector2 position) {
        body.setTransform(position, getAngleRad());
    }
}
