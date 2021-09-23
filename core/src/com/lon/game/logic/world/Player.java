package com.lon.game.logic.world;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.lon.game.logic.HexTextureMap;
import com.lon.game.logic.angle.Angle;
import com.lon.game.logic.tile.Drawable;
import com.lon.game.logic.utils.BodyBuilder;

import static com.lon.game.logic.world.PlayerConstant.*;

public class Player implements Drawable {
    private final Body body;

    private final Body frontRightWheel;
    private final Body frontLeftWheel;
    private final Body backRightWheel;
    private final Body backLeftWheel;

    private final Texture texture;

    private float speed = 0;

    public Player(World world, RayHandler rayHandler, Texture texture) {
        this.body = BodyBuilder.createCar(world, 0, 0, 2 * PLAYER_SIZE - 0.15f, PLAYER_SIZE - 0.3f);
        MassData massData = this.body.getMassData();
        massData.mass *= 100;
        massData.I *= 100;

        frontRightWheel = createWheel(world, 11/20f, -7/20f, true);
        frontLeftWheel = createWheel(world, 11/20f, 7/20f, true);

        backRightWheel = createWheel(world, -11/20f, -7/20f, false);
        backLeftWheel = createWheel(world, -11/20f, 7/20f, false);

        this.texture = texture;
        this.body.setActive(true);
        this.body.setAwake(true);
        this.body.setLinearDamping(0.0025f);
        this.body.setAngularDamping(3f);

        createLights(rayHandler);
    }

    private Body createWheel(World world, float x, float y, boolean isRotate) {
        Body wheel = BodyBuilder.createWheel(world, x, y, 0.35f, 0.12f);

        if (isRotate) {
            RevoluteJointDef joint = new RevoluteJointDef();
            joint.collideConnected = false;
            joint.type = JointDef.JointType.RevoluteJoint;
            joint.initialize(this.body, wheel, wheel.getPosition());
            joint.localAnchorA.add(0, 0);

            world.createJoint(joint);
        } else {
            WeldJointDef joint = new WeldJointDef();
            joint.collideConnected = false;
            joint.type = JointDef.JointType.WeldJoint;
            joint.initialize(this.body, wheel, wheel.getPosition());
            joint.localAnchorA.add(0, 0);

            world.createJoint(joint);
        }

        wheel.setAngularDamping(10f);

        return wheel;
    }

    private void createLights(RayHandler rayHandler) {
        Color color = new Color(0.3f, 0.3f, 0.25f, 1f);

        ConeLight forwardLight = new ConeLight(rayHandler, 1000, color, 30, 0, 0, 0, 45);
        forwardLight.attachToBody(this.body, 0, 0);
        forwardLight.setIgnoreAttachedBody(true);
        forwardLight.setSoft(true);
        short categoryBits = 1;
        short groupIndex = 1;
        short maskBits = 1;
        forwardLight.setContactFilter(categoryBits, groupIndex, maskBits);

        ConeLight backwardLight = new ConeLight(rayHandler, 1000, color, 15, 0, 0, 90, 135);
        backwardLight.attachToBody(this.body, 0, 0, 180);
        backwardLight.setIgnoreAttachedBody(true);
        backwardLight.setSoft(false);
        backwardLight.setContactFilter(categoryBits, groupIndex, maskBits);
    }

    public Vector2 getPosition() {
        return body.getPosition().cpy();
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

    public float getWheelAngleRad() {
        return frontLeftWheel.getAngle();
    }

    public Body getBody() {
        return body;
    }

    public void render(Batch batch) {
        float halfSize = PLAYER_SIZE/2.f;

        HexTextureMap textureMap = HexTextureMap.getInstance();

        batch.draw(textureMap.get("tyre"), frontLeftWheel.getPosition().x - 3.5f/20f, frontLeftWheel.getPosition().y - 1.5f/20f, 3.5f/20f, 1.5f/20f, 7/20f, 3/20f, 1, 1, Angle.convertInDeg(frontLeftWheel.getAngle()), 1, 1, 500, 100, false, false);
        batch.draw(textureMap.get("tyre"), frontRightWheel.getPosition().x - 3.5f/20f, frontRightWheel.getPosition().y - 1.5f/20f, 3.5f/20f, 1.5f/20f, 7/20f, 3/20f, 1, 1, Angle.convertInDeg(frontRightWheel.getAngle()), 1, 1, 500, 100, false, false);
        batch.draw(textureMap.get("tyre"), backLeftWheel.getPosition().x - 3.5f/20f, backLeftWheel.getPosition().y - 1.5f/20f, 3.5f/20f, 1.5f/20f, 7/20f, 3/20f, 1, 1, Angle.convertInDeg(backLeftWheel.getAngle()), 1, 1, 500, 100, false, false);
        batch.draw(textureMap.get("tyre"), backRightWheel.getPosition().x - 3.5f/20f, backRightWheel.getPosition().y - 1.5f/20f, 3.5f/20f, 1.5f/20f, 7/20f, 3/20f, 1, 1, Angle.convertInDeg(backRightWheel.getAngle()), 1, 1, 500, 100, false, false);

        batch.draw(texture, getX() - 2 * halfSize, getY() - halfSize, halfSize * 2, halfSize, PLAYER_SIZE * 2, PLAYER_SIZE, 1, 1, getAngleDeg(), 1, 1, 860, 478, false, false);
    }

    public void move(float delta) {
        float angle = frontRightWheel.getAngle() - body.getAngle();

        if (angle != 0) {
            body.applyTorque(angle * delta * body.getLinearVelocity().len() * 10, true);
        }

        body.applyForceToCenter(new Vector2(0.0005f * delta, 0).rotateRad(getAngleRad()), true);
    }

    public void rotate(float delta) {
        frontRightWheel.applyTorque(PLAYER_ANGLE_SPEED * delta * 0.25f, true);
        frontLeftWheel.applyTorque(PLAYER_ANGLE_SPEED * delta * 0.25f, true);

        float angle = frontRightWheel.getAngle() - body.getAngle();

        if (angle > Math.PI/4) {
            frontRightWheel.setTransform(frontRightWheel.getPosition(), body.getAngle() + (float) (Math.PI / 4.f));
            frontLeftWheel.setTransform(frontLeftWheel.getPosition(), body.getAngle() + (float) (Math.PI / 4.f));
        }

        if (angle < -Math.PI/4) {
            frontRightWheel.setTransform(frontRightWheel.getPosition(), body.getAngle() - (float) (Math.PI / 4.f));
            frontLeftWheel.setTransform(frontLeftWheel.getPosition(), body.getAngle() - (float) (Math.PI / 4.f));
        }
    }

    public void update(float speedMul, float delta) {
        float angle = frontRightWheel.getAngle() - body.getAngle();


    }

    public void setTransform(Vector2 position) {
        body.setTransform(position, getAngleRad());

        frontLeftWheel.setTransform(position, getAngleRad());
        frontRightWheel.setTransform(position, getAngleRad());
        backLeftWheel.setTransform(position, getAngleRad());
        backRightWheel.setTransform(position, getAngleRad());

    }

    public float getSpeed() {
        return speed;
    }
}
