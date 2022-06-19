
package com.lon.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.lon.game.world.WorldConstants.TILE_SIZE;

public class BodyFactory {
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

    public static Body createRectangle(final World world, float x, float y, float w, float h,
                                       boolean isStatic, boolean canRotate, short cBits, short mBits, short gIndex) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = canRotate;
        bodyDef.position.set(x, y);

        if (isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w / 2.f, h / 2.f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = cBits; // Is a
        fixtureDef.filter.maskBits = mBits; // Collides with
        fixtureDef.filter.groupIndex = gIndex;

        return world.createBody(bodyDef).createFixture(fixtureDef).getBody();
    }

    public static Body createCircle(final World world, float x, float y, float r,
                                    boolean isStatic, boolean canRotate, short cBits, short mBits, short gIndex) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = canRotate;
        bodyDef.position.set(x, y);

        if (isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        CircleShape shape = new CircleShape();
        shape.setRadius(r);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = cBits; // Is a
        fixtureDef.filter.maskBits = mBits; // Collides with
        fixtureDef.filter.groupIndex = gIndex;

        return world.createBody(bodyDef).createFixture(fixtureDef).getBody();
    }
}
