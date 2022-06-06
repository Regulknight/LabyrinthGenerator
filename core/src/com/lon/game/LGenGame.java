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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lon.game.tile.Hexagon;
import com.lon.game.tile.TileType;
import com.lon.game.utils.AverageCounter;
import com.lon.game.utils.AverageCounterMap;
import com.lon.game.utils.HexagonTileBuilder;
import com.lon.game.utils.SquareTileBuilder;
import com.lon.game.world.GridType;
import com.lon.game.world.Level;
import com.lon.game.world.TileGrid;
import com.lon.game.world.LabyrinthGenerator;

import java.util.concurrent.TimeUnit;

import static com.lon.game.world.WorldConstants.TILE_SIZE;


public class LGenGame extends ApplicationAdapter {
    public static final String TOTAL_TIMER = "total_timer";
    public static final String LEVEL_TIMER = "level_timer";
    public static int GRID_WIDTH = 10;
    public static int GRID_HEIGHT = 10;
    private static int GRID_WIDTH_INC = 2;
    private static int GRID_HEIGHT_INC = 2;
    private static int LEVEL_CHANGE_SLEEP_TIMEOUT_MS= 1500;
    private static GridType gridType = GridType.SQUARE;

    SpriteBatch batch;
    SpriteBatch hudBatch;
    TileGrid map;
    World world;
    OrthographicCamera camera;
    Box2DDebugRenderer b2dr;

    HexTextureMap hexTextureMap;
    TextureMap textureMap;
    float timer = 0;

    RayHandler rayHandler;

    Level level;
    LabyrinthGenerator labyrinth;

    boolean generatorFlag = false;

    AverageCounterMap timeInfo = new AverageCounterMap();

    BitmapFont font;


    @Override
    public void create() {
        timeInfo.put(TOTAL_TIMER, new AverageCounter());
        timeInfo.put(LEVEL_TIMER, new AverageCounter());

        world = new World(new Vector2(0, 0), false);
        World.setVelocityThreshold(10f);

        font = new BitmapFont();

        hexTextureMap = HexTextureMap.getInstance();
        textureMap = TextureMap.getInstance();

        batch = new SpriteBatch();
        hudBatch = new SpriteBatch();

        b2dr = new Box2DDebugRenderer();

        camera = new OrthographicCamera(90, 50);

        camera.rotate(90);

        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera);
        rayHandler.setAmbientLight(1f);

        createLevel(GRID_WIDTH / 2, GRID_HEIGHT / 2);
        recalibrateCamera(camera);
    }

    @Override
    public void render() {
        world.step(60, 1, 1);

        float delta = Gdx.graphics.getDeltaTime();
        timer += delta;

        Gdx.gl20.glClearColor(.25f, .25f, .25f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        processUserInput();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        rayHandler.setCombinedMatrix(camera);

        batch.begin();
        ScreenUtils.clear(0, 0, 0, 1);
        if (generatorFlag) {
            long startTime = System.nanoTime();
            boolean isGen = labyrinth.generationStep();
            long deltaTime = System.nanoTime() - startTime;

            timeInfo.update(deltaTime);

            if (!isGen) {
                try {
                    TimeUnit.MILLISECONDS.sleep(LEVEL_CHANGE_SLEEP_TIMEOUT_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                timeInfo.reset(LEVEL_TIMER);
                createLevel(GRID_WIDTH / 2, GRID_HEIGHT / 2);
                recalibrateCamera(camera);
                camera.update();
            }
        }

        map.render(batch);

        batch.end();

        rayHandler.updateAndRender();

        renderHud();
    }

    private void renderHud() {
        hudBatch.begin();

        int branchCount = 0;
        int labyrinthCellSize = 0;

        font.draw(hudBatch, "total time: " + Math.round(timer * 100) / 100.0, 5, 450);
        font.draw(hudBatch, String.format("grid size: (%d, %d)", GRID_HEIGHT, GRID_WIDTH), 5, 425);
        font.draw(hudBatch, String.format("branch count: %d", branchCount), 5, 400);
        font.draw(hudBatch, String.format("cell count: %d", labyrinthCellSize), 5, 375);
        font.draw(hudBatch, String.format("steps total: %d", timeInfo.getCounter(TOTAL_TIMER)), 5, 350);
        font.draw(hudBatch, String.format("average step time: %f ms", timeInfo.getAverage(TOTAL_TIMER)/1000/1000f), 5, 325);
        font.draw(hudBatch, String.format("steps level: %d", timeInfo.getCounter(LEVEL_TIMER)), 5, 300);
        font.draw(hudBatch, String.format("level step time: %f ms", timeInfo.getAverage(LEVEL_TIMER)/1000/1000f), 5, 275);

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

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.zoom += 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.zoom -= 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.position.add(1, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.position.add(0, 1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.position.add(-1, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.position.add(0, -1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            generatorFlag = true;
        }

    }

    private void createLevel(int startX, int startY) {
        if (level != null) {
            level.destroy();

            GRID_WIDTH += GRID_WIDTH_INC;
            GRID_HEIGHT += GRID_HEIGHT_INC;
        }

        if (GridType.HEX.equals(gridType)) {
            level = new Level(GRID_WIDTH, GRID_HEIGHT, new HexagonTileBuilder(world, TileType.WALL));
        } else {
            level = new Level(GRID_WIDTH, GRID_HEIGHT, new SquareTileBuilder(world, TileType.WALL));
        }

        map = level.getMap();
        labyrinth = new LabyrinthGenerator(map, map.getTile(startX, startY));
    }


    private void recalibrateCamera(OrthographicCamera camera) {

        if (GridType.HEX.equals(gridType)) {
            camera.position.set(GRID_WIDTH * TILE_SIZE / 2.f - GRID_WIDTH, GRID_HEIGHT * Hexagon.h, 0);
            camera.zoom = (float) (0.19 * (Math.max(GRID_WIDTH, GRID_HEIGHT)));
        } else {
            camera.position.set(GRID_WIDTH * TILE_SIZE / 2.f, GRID_HEIGHT * TILE_SIZE / 2.f, 0);
            camera.zoom = (float) (0.21 * (Math.max(GRID_WIDTH, GRID_HEIGHT)));
        }

    }
}
