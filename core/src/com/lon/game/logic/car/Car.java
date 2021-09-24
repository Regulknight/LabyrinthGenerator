package com.lon.game.logic.car;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.lon.game.logic.TextureMap;
import com.lon.game.logic.angle.Angle;
import com.lon.game.logic.tile.Drawable;
import com.lon.game.logic.utils.BodyBuilder;

import static com.lon.game.logic.car.CarConstants.*;

public class Car implements Drawable {
    private final Body body;

    private final Body frontRightWheel;
    private final Body frontLeftWheel;
    private final Body backRightWheel;
    private final Body backLeftWheel;
    private final Texture wheelTexture;

    private final Texture texture;

    public Car(World world, RayHandler rayHandler, TextureMap textureMap) {
        this.body = BodyBuilder.createCar(world, 0, 0);

        float xWheelPosition = 0.55f * CAR_SIZE;
        float yWheelPosition = 0.35f * CAR_SIZE;
        this.frontRightWheel = createWheel(world, xWheelPosition, -yWheelPosition, true);
        this.frontLeftWheel = createWheel(world, xWheelPosition, yWheelPosition, true);
        this.backRightWheel = createWheel(world, -xWheelPosition, -yWheelPosition, false);
        this.backLeftWheel = createWheel(world, -xWheelPosition, yWheelPosition, false);

        this.wheelTexture = textureMap.get("tyre");

        this.texture = textureMap.get("player");
        this.body.setActive(true);
        this.body.setAwake(true);
        this.body.setLinearDamping(0.0025f);
        this.body.setAngularDamping(3f);

        createLights(rayHandler);
    }

    private Body createWheel(World world, float x, float y, boolean isRotate) {
        Body wheel = BodyBuilder.createWheel(world, x, y);

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
        float halfSize = CAR_SIZE/2.f;

        renderWheel(frontLeftWheel, batch);
        renderWheel(frontRightWheel, batch);
        renderWheel(backLeftWheel, batch);
        renderWheel(backRightWheel, batch);

        batch.draw(texture, getX() - 2 * halfSize, getY() - halfSize, halfSize * 2, halfSize, CAR_SIZE * 2, CAR_SIZE, 1, 1, getAngleDeg(), 1, 1, 860, 478, false, false);
    }

    private void renderWheel(Body wheel, Batch batch) {
        batch.draw(wheelTexture, wheel.getPosition().x - HALF_WHEEL_WIDTH, wheel.getPosition().y - HALF_WHEEL_HEIGHT, HALF_WHEEL_WIDTH, HALF_WHEEL_HEIGHT, 2 * HALF_WHEEL_WIDTH, 2 * HALF_WHEEL_HEIGHT, 1, 1, Angle.convertInDeg(wheel.getAngle()), 1, 1, 500, 100, false, false);
    }

    public void move(float delta) {
        float angle = frontRightWheel.getAngle() - body.getAngle();

        if (angle != 0) {
            body.applyTorque(angle * delta * body.getLinearVelocity().len() * 10, true);
        }

        normalizeWheels();

        body.applyForceToCenter(new Vector2(CAR_FORCE * delta, 0).rotateRad(getAngleRad()), true);
    }

    public void rotate(float delta) {
        frontRightWheel.applyTorque(CAR_ANGLE_SPEED * delta, true);
        frontLeftWheel.applyTorque(CAR_ANGLE_SPEED * delta, true);

        normalizeWheels();
    }

    private void normalizeWheels() {
        float angle = frontRightWheel.getAngle() - body.getAngle();

        if (angle > WHEEL_MAX_ANGLE) {
            frontRightWheel.setTransform(frontRightWheel.getPosition(), body.getAngle() + WHEEL_MAX_ANGLE);
            frontLeftWheel.setTransform(frontLeftWheel.getPosition(), body.getAngle() + WHEEL_MAX_ANGLE);
        }

        if (angle < -WHEEL_MAX_ANGLE) {
            frontRightWheel.setTransform(frontRightWheel.getPosition(), body.getAngle() - WHEEL_MAX_ANGLE);
            frontLeftWheel.setTransform(frontLeftWheel.getPosition(), body.getAngle() - WHEEL_MAX_ANGLE);
        }
    }

    public void setTransform(Vector2 position) {
        body.setTransform(position, getAngleRad());

        frontLeftWheel.setTransform(position, getAngleRad());
        frontRightWheel.setTransform(position, getAngleRad());
        backLeftWheel.setTransform(position, getAngleRad());
        backRightWheel.setTransform(position, getAngleRad());

    }
}
