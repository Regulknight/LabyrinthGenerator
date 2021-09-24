package com.lon.game;

import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lon.game.logic.HexTextureMap;
import com.lon.game.logic.TextureMap;
import com.lon.game.logic.world.Level;
import com.lon.game.logic.car.Car;
import com.lon.game.logic.world.TileGrid;
import com.lon.game.logic.tile.Tile;
import com.lon.game.logic.world.LabyrinthGenerator;

import java.util.List;

public class LGenGame extends ApplicationAdapter {
    private int gridWidth = 7;
    private int gridHeight = 7;

    private int score = 0;

    SpriteBatch batch;
    SpriteBatch hudBatch;
    TileGrid map;
    World world;
    OrthographicCamera camera;
    Box2DDebugRenderer b2dr;

    double rotateAngle = 2 * Math.PI;
    double viewAngle = Math.PI;
    double coneRadius = 80;
    Car player;

    BitmapFont font;
    HexTextureMap hexTextureMap;
    TextureMap textureMap;
    float timer = 0;

    RayHandler rayHandler;

    Level level;
    LabyrinthGenerator labyrinth;

    boolean isMapOpen = false;
    boolean isDebugInfo = false;

    @Override
    public void create() {
        world = new World(new Vector2(0, 0), false);
        World.setVelocityThreshold(10f);
        font = new BitmapFont();
        hexTextureMap = HexTextureMap.getInstance();
        textureMap = TextureMap.getInstance();

        batch = new SpriteBatch();
        hudBatch = new SpriteBatch();

        b2dr = new Box2DDebugRenderer();

        camera = new OrthographicCamera(90, 50);
        camera.zoom = 1f;
        camera.rotate(90);

        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera);
        //rayHandler.setAmbientLight(0.025f);

        player = new Car(world, rayHandler, textureMap);
        camera.translate(new Vector3(player.getCenter(), 10));
        createLevel(1, 1);
    }

    @Override
    public void render() {
        Vector2 playerPosition = player.getCenter();
        float angle = -90 + player.getAngleDeg();

        world.step(60, 1, 1);

        float delta = Gdx.graphics.getDeltaTime();
        timer += delta;

        Gdx.gl20.glClearColor(.25f, .25f, .25f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        processUserInput();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        rayHandler.setCombinedMatrix(camera);

        if (!isMapOpen) {
            batch.begin();
            ScreenUtils.clear(0, 0, 0, 1);

            labyrinth.generationStep(delta);

            camera.position.set(playerPosition, 10);
            camera.up.set(new Vector3(0, 1, 0).rotate(angle, 0, 0, 1));

            map.render(batch);
            player.render(batch);

            batch.end();
            //b2dr.render(world, camera.combined.cpy());
            rayHandler.updateAndRender();
        }

        renderHud();

        if (labyrinth.checkWinCondition(player.getPosition())) {
            createLevel(1, 1);
        }
    }

    private void renderHud() {
        hudBatch.begin();

        font.draw(hudBatch, "Score: " + score, 5, 470);
        font.draw(hudBatch, "Time: " + Math.round(timer * 100) / 100.0, 5, 450);

        if (isDebugInfo) {
            font.draw(hudBatch, "Linear vel" + player.getBody().getLinearVelocity().len(), 5, 200);
            font.draw(hudBatch, "Angle vel: " + player.getBody().getAngularVelocity(), 5, 180);
            font.draw(hudBatch, "Body Angle" + player.getBody().getAngle(), 5, 160);
            font.draw(hudBatch, "Wheels Angle" + player.getWheelAngleRad(), 5, 140);
            font.draw(hudBatch, "Angles difference" + (player.getWheelAngleRad() - player.getAngleRad()), 5, 120);
        }

        hudBatch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        b2dr.dispose();
        hexTextureMap.dispose();
        textureMap.dispose();
        hudBatch.dispose();
        rayHandler.dispose();
    }

    private void processUserInput() {
        float delta = Gdx.graphics.getDeltaTime();

//        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
//            float playerDirection = Angle.getAngle(Gdx.input.getX() - camera.viewportWidth/2.f, camera.viewportHeight/2.f - Gdx.input.getY());
//            System.out.println(playerDirection);
//            player.setAngle(playerDirection);
//
//            player.move(delta);
//        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.rotate(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.rotate(-delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.move(-delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.move(delta);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            viewAngle += Math.PI / 16;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            viewAngle -= Math.PI / 16;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.zoom += 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.zoom -= 0.1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            rotateAngle += Math.PI / 32;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            rotateAngle -= Math.PI / 32;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            System.out.println(player.getBody().getLinearVelocity().angleRad() + " " + player.getBody().getAngle());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.GRAVE)) {
            isDebugInfo = !isDebugInfo;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            isMapOpen = !isMapOpen;
        }

    }

    private void createLevel(int startX, int startY) {
        if (level != null) {
            level.destroy();
            score++;
            gridWidth += 2;
            gridHeight += 2;
        }

        level = new Level(gridWidth, gridHeight, world);
        map = level.getMap();
        labyrinth = new LabyrinthGenerator(map, map.getTile(startX, startY), world);

        Tile start = map.getTile(startX, startY);
        player.setTransform(new Vector2(start.getCenterX(), start.getCenterY()));
    }

    public Tile getFromCoordinate(Vector2 coord) {
        for (List<Tile> row: map.getGrid()) {
            for (Tile tile: row) {
                if (tile.containCoord(coord)) {
                    return tile;
                }
            }
        }
        return null;
    }
}
