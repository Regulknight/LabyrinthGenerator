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
import com.lon.game.generator.NegativeMemoryPathBuilder;
import com.lon.game.generator.direction.MemoryDirectionChooser;
import com.lon.game.generator.direction.RandomDirectionChooser;
import com.lon.game.tile.Hexagon;
import com.lon.game.utils.*;
import com.lon.game.world.GridType;
import com.lon.game.world.LabyrinthGenerator;
import com.lon.game.world.TileGrid;
import com.lon.game.world.TileGridBuilder;

import java.util.concurrent.TimeUnit;

import static com.lon.game.world.WorldConstants.TILE_SIZE;


public class LGenGame extends ApplicationAdapter {
    public static final String TOTAL_TIMER = "total_timer";
    public static final String LEVEL_TIMER = "level_timer";
    public static int GRID_WIDTH = 30;
    public static int GRID_HEIGHT = 30;
    private static final int GRID_WIDTH_INC = 2;
    private static final int GRID_HEIGHT_INC = 2;
    private static final int LEVEL_CHANGE_SLEEP_TIMEOUT_MS= 3000;
    private static final GridType gridType = GridType.HEX;

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

    LabyrinthGenerator labyrinth;

    boolean generatorFlag = false;

    AverageCounterMap timeInfo = new AverageCounterMap();

    BitmapFont font;

    TileGridBuilder mapBuilder;

    NegativeMemoryPathBuilder builder = new NegativeMemoryPathBuilder(new RandomDirectionChooser());

    @Override
    public void create() {
        timeInfo.put(TOTAL_TIMER, new AverageCounter());
        timeInfo.put(LEVEL_TIMER, new AverageCounter());

        world = new World(new Vector2(0, 0), false);
        createMapBuilder();

        font = new BitmapFont();

        hexTextureMap = HexTextureMap.getInstance();
        textureMap = TextureMap.getInstance();

        batch = new SpriteBatch();
        hudBatch = new SpriteBatch();

        b2dr = new Box2DDebugRenderer();

        camera = new OrthographicCamera(90, 50);

        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera);
        rayHandler.setAmbientLight(1f);

        createLevel(GRID_WIDTH / 2, GRID_HEIGHT / 2);
    }

    private void createMapBuilder() {
        WorldTileFactory factory;

        if (gridType == GridType.SQUARE)
            factory = new SquareTileFactory(world);
        else
            factory = new HexagonTileFactory(world);

        mapBuilder = new TileGridBuilder(factory, GRID_WIDTH, GRID_HEIGHT);
        mapBuilder.setHeightInc(GRID_HEIGHT_INC);
        mapBuilder.setWidthInc(GRID_WIDTH_INC);
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
            boolean isGen = labyrinth.generationStep(map);
            long deltaTime = System.nanoTime() - startTime;

            timeInfo.update(deltaTime);

            if (!isGen) {
                try {
                    TimeUnit.MILLISECONDS.sleep(LEVEL_CHANGE_SLEEP_TIMEOUT_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                timeInfo.reset(LEVEL_TIMER);
                createLevel(mapBuilder.getWidth() / 2, mapBuilder.getWidth() / 2);
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
        font.draw(hudBatch, String.format("grid size: (%d, %d)", mapBuilder.getHeight(), mapBuilder.getWidth()), 5, 425);
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
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.zoom += 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.zoom -= 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.position.add(1, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.position.add(0, 1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.position.add(-1, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.position.add(0, -1, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            generatorFlag = true;
        }
    }

    private void createLevel(int startX, int startY) {
        if (map != null) {
            map.destroy();
        }

        map = mapBuilder.build();

        builder.reset();
        labyrinth = new LabyrinthGenerator(builder, map.getTile(startX, startY));

        recalibrateCamera();
    }


    private void recalibrateCamera() {
        if (GridType.HEX.equals(gridType)) {
            camera.position.set(mapBuilder.getWidth() * TILE_SIZE / 2.f - mapBuilder.getWidth(), mapBuilder.getHeight() * Hexagon.h, 0);
        } else {
            camera.position.set(mapBuilder.getWidth() * TILE_SIZE / 2.f, mapBuilder.getHeight() * TILE_SIZE / 2.f, 0);
        }
        camera.zoom = (float) (0.21 * (Math.max(mapBuilder.getWidth(), mapBuilder.getHeight())));

    }

}
