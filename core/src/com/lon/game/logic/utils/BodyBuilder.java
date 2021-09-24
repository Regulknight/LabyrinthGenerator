
package com.lon.game.logic.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.lon.game.logic.car.CarConstants.*;
import static com.lon.game.logic.world.WorldConstants.TILE_SIZE;

public class BodyBuilder {
    public static Body createSquareTile(World world, Vector2 gridPosition) {
        return createRectangle(world, gridPosition.x * TILE_SIZE, gridPosition.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, true, true);
    }

    public static Body createHexagonTile(World world, Vector2 position) {
        Body pBody;
        BodyDef def = new BodyDef();

        double h = Math.pow(TILE_SIZE/2d * TILE_SIZE/2d - TILE_SIZE/4d * TILE_SIZE/4d, 1/2d);

        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(position.x, position.y);
        def.fixedRotation = true;

        pBody = world.createBody(def);
        pBody.setUserData("wall");

        PolygonShape shape = new PolygonShape();


        shape.set(getHexagonVertices(position));

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1.0f;
        fd.filter.categoryBits = 1;
        fd.filter.maskBits = 1;
        fd.filter.groupIndex = 0;
        pBody.createFixture(fd);
        shape.dispose();

        return pBody;
    }

    private static Vector2[] getHexagonVertices(Vector2 position) {
        double h = Math.pow(TILE_SIZE/2d * TILE_SIZE/2d - TILE_SIZE/4d * TILE_SIZE/4d, 1/2d);

        double startX = position.x % 2 == 0 ? position.x * TILE_SIZE * 0.75 : 0.75 * TILE_SIZE + TILE_SIZE * 0.75 * (position.x - 1);
        startX -= position.x * 1;
        double startY = position.x % 2 == 0 ? position.y * 2 * h : position.y * 2 * h + h;
        startY -= position.y * 1;

        Vector2[] result = new Vector2[7];

        Vector2 first = new Vector2((float) (startX + TILE_SIZE * 0.25d),(float) startY);
        result[0] = first;
        result[1] = new Vector2((float) (startX + TILE_SIZE * 0.75d),(float)  startY);


        result[2] = new Vector2((float) (startX + TILE_SIZE),(float)  (startY + h));
        result[3] = new Vector2((float) (startX + TILE_SIZE * 0.75d), (float) (startY + 2 * h));
        result[4] = new Vector2((float) (startX + TILE_SIZE * 0.25d), (float) (startY + 2 * h));
        result[5] = new Vector2((float) startX, (float) (startY + h));
        result[6] = first;

        return result;
    }

    public static Body createRectangle(World world, float x, float y, int width, int height, boolean isStatic, boolean fixedRotation) {
        Body pBody;
        BodyDef def = new BodyDef();

        if (isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x + TILE_SIZE / 2.f, y + TILE_SIZE / 2.f);
        def.fixedRotation = fixedRotation;
        pBody = world.createBody(def);
        pBody.setUserData("wall");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2.f, height / 2.f);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1.0f;
        fd.filter.categoryBits = 1;
        fd.filter.maskBits = 1;
        fd.filter.groupIndex = 0;
        pBody.createFixture(fd);
        shape.dispose();
        return pBody;
    }

    public static Body createCar(World world, float x, float y) {
        Body pBody;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x + TILE_SIZE / 2.f, y + TILE_SIZE / 2.f);

        pBody = world.createBody(def);
        pBody.setUserData("car");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(CAR_SIZE * 0.925f, CAR_SIZE * 0.35f);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1f;
        fd.filter.categoryBits = 1;
        fd.filter.maskBits = 1;
        fd.filter.groupIndex = 0;
        pBody.createFixture(fd);
        shape.dispose();

        MassData massData = pBody.getMassData();
        massData.mass *= CAR_MASS_MULTIPLIER;
        massData.I *= CAR_MASS_MULTIPLIER;

        pBody.setLinearDamping(CAR_LINEAR_DAMPING);
        pBody.setAngularDamping(CAR_ANGULAR_DAMPING);

        return pBody;
    }

    public static Body createWheel(World world, float x, float y) {
        Body pBody;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x + TILE_SIZE / 2.f, y + TILE_SIZE / 2.f);
        def.fixedRotation = false;
        pBody = world.createBody(def);
        pBody.setUserData("wall");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(HALF_WHEEL_WIDTH, HALF_WHEEL_HEIGHT);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1f;
        fd.filter.categoryBits = 2;
        fd.filter.maskBits = 1;
        fd.filter.groupIndex = 2;
        pBody.createFixture(fd);
        shape.dispose();

        return pBody;
    }
}
