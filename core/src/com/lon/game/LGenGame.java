package com.lon.game;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
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
import com.lon.game.logic.world.Player;
import com.lon.game.logic.world.TileGrid;
import com.lon.game.logic.angle.Angle;
import com.lon.game.logic.tile.Tile;
import com.lon.game.logic.world.LabyrinthGenerator;

import java.util.List;

import static com.lon.game.logic.world.PlayerConstant.PLAYER_SIZE;

public class LGenGame extends ApplicationAdapter {
    private int gridWidth = 15;
    private int gridHeight = 15;

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
    Player player;

    float speedMul = 1;

    BitmapFont font;
    HexTextureMap hexTextureMap;
    TextureMap textureMap;
    float timer = 0;

    RayHandler rayHandler;

    Level level;
    LabyrinthGenerator labyrinth;

    boolean isMapOpen = false;

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

        player = new Player(world, rayHandler, hexTextureMap.get("player"));
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

            //float angle = (float) ((-Math.PI/2 + ((Math.abs(player.getAngleRad() + player.getWheelAngleRad()))/2.f)));

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
//        font.draw(hudBatch, "Speed: " + Math.round(player.getSpeed() * 100) / 100.0, 5, 430);
//        font.draw(hudBatch, "Speed mul: " + speedMul, 5, 410);
//        font.draw(hudBatch, "Linear vel" + player.getBody().getLinearVelocity().len(), 5, 390);
//        font.draw(hudBatch, "Angle vel: " + player.getBody().getAngularVelocity(), 5, 370);
//        font.draw(hudBatch, "Body Angle" + player.getBody().getAngle(), 5, 350);
//        font.draw(hudBatch, "Wheels Angle" + player.getWheelAngleRad(), 5, 330);
//        font.draw(hudBatch, "Angles difference" + (player.getAngleRad() - player.getWheelAngleRad()), 5, 310);

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
            speedMul += 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            speedMul -= 0.1;
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            isMapOpen = !isMapOpen;
        }

        player.update(2, delta);
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
