package com.lon.game;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lon.game.logic.HexTextureMap;
import com.lon.game.logic.world.Level;
import com.lon.game.logic.world.Player;
import com.lon.game.logic.world.TileGrid;
import com.lon.game.logic.angle.Angle;
import com.lon.game.logic.area.ConeOfView;
import com.lon.game.logic.area.Sector;
import com.lon.game.logic.tile.Tile;
import com.lon.game.logic.world.LabyrinthGenerator;

import java.util.LinkedList;
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
    Player player;

    BitmapFont font;
    HexTextureMap textureMap;
    float timer = 0;

    int playerId = 0;
    List<Color> playersColors;

    List<PointLight> lightList;

    RayHandler rayHandler;

    ConeLight forwardLight;
    ConeLight backwardLight;

    Level level;
    LabyrinthGenerator labyrinth;

    boolean isMapOpen = false;

    @Override
    public void create() {
        world = new World(new Vector2(0, 0), false);
        font = new BitmapFont();
        textureMap = HexTextureMap.getInstance();

        lightList = new LinkedList<>();

        player = new Player(world, textureMap.get("player"));

        createLevel(1, 1);

        batch = new SpriteBatch();
        hudBatch = new SpriteBatch();

        b2dr = new Box2DDebugRenderer();

        camera = new OrthographicCamera(1920, 1080);
        camera.zoom = 0.1f;
        camera.translate(player.getCenter());

        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera.combined.cpy());
        rayHandler.setAmbientLight(0.225f);

        Color color = new Color(0.3f, 0.3f, 0.25f, 1f);

        forwardLight = new ConeLight(rayHandler, 1000, color, 400, 0, 0, 0, 45);
        forwardLight.attachToBody(player.getBody(), 5, 0);
        forwardLight.setIgnoreAttachedBody(true);
        forwardLight.setSoft(true);

        backwardLight = new ConeLight(rayHandler, 1000, color, 200, 0, 0, 90, 135);
        backwardLight.attachToBody(player.getBody(), 5, 0, 180);
        backwardLight.setIgnoreAttachedBody(true);
        backwardLight.setSoft(false);

        playersColors = new LinkedList<>();
        playersColors.add(new Color(0.2f, 1.0f, 0.2f, 0.8f));
        playersColors.add(new Color(0.2f, 1.0f, 1.0f, 0.8f));
        playersColors.add(new Color(0.2f, 0.2f, 1.0f, 0.8f));
        playersColors.add(new Color(1.f, 0.2f, 0.2f, 0.8f));
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
        rayHandler.setCombinedMatrix(camera.combined);

        if (!isMapOpen) {
            batch.begin();
            ScreenUtils.clear(0, 0, 0, 1);

            labyrinth.generationStep(delta);

            //light();

            camera.position.set(player.getCenter(), 0);

            calculatePlayerVision();
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

        if (isMapOpen) {
            renderHistory(hudBatch);
        }

        hudBatch.end();
    }

    private void calculatePlayerVision() {
        for (Tile tile : map.getTilesFromArea(player.getPosition(), new ConeOfView(300, new Sector(0, (float) (2 * Math.PI))))) {
            Vector2 position = tile.getPixelPosition();

            if (rayHandler.pointAtLight(position.x, position.y) && inViewPort(position)) {
                tile.setVisionLevel(1);
//                tile.setVisionType(tile.getType());
            }
        }
    }

    private boolean inViewPort(Vector2 point) {
        boolean inArea = player.getPosition().x - camera.viewportWidth * camera.zoom / 2 < point.x;
        inArea &= point.x < player.getPosition().x + camera.viewportWidth * camera.zoom / 2;
        inArea &= player.getPosition().y - camera.viewportHeight * camera.zoom / 2 < point.y;
        inArea &= point.y < player.getPosition().y + camera.viewportHeight * camera.zoom / 2;

        return inArea;
    }

    private void renderHistory(Batch batch) {
        for (List<Tile> row : map.getGrid()) {
            for (Tile tile : row) {
                if (tile.getVisionLevel() > 0)
                    drawHistoryCell(tile, batch);
            }
        }
    }

    private void light() {
        Tile tile = getFromCoordinate(player.getCenter());

        PointLight pl = tile.addLight(playerId, playersColors.get(playerId), rayHandler);
        if (pl != null) {
            lightList.add(pl);
        }

    }

    private void drawHistoryCell(Tile tile, Batch batch) {
        int size = 460 / gridHeight;
        int mapSize = size * gridHeight;
        int xOffset = (640 - mapSize) / 2;
        int yOffset = 10;

        Vector2 cellPosition = new Vector2(tile.getGridPosition()).scl(size);
//        batch.draw(tile.getVisionType().getTexture(), cellPosition.x + xOffset, cellPosition.y + yOffset, size, size);
    }

    @Override
    public void dispose() {
        batch.dispose();
        b2dr.dispose();
        textureMap.dispose();
        hudBatch.dispose();
        rayHandler.dispose();
    }

    private void processUserInput() {
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            float playerDirection = Angle.getAngle(Gdx.input.getX() - camera.viewportWidth/2.f, camera.viewportHeight/2.f - Gdx.input.getY());
            System.out.println(playerDirection);
            player.setAngle(playerDirection);

            player.move(delta);
        }
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
            camera.zoom += 0.01;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.zoom -= 0.01;
            if (camera.zoom <= 0.1) {
                camera.zoom = 0.1f;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            rotateAngle += Math.PI / 32;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            rotateAngle -= Math.PI / 32;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
           if (playerId < 3) {
               playerId++;
           } else {
               playerId = 0;
           }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            isMapOpen = !isMapOpen;
        }
    }

    private void createLevel(int startX, int startY) {
        if (level != null) {
            for (PointLight light : lightList) {
                light.remove();
            }
            lightList.clear();

            level.destroy();
            score++;
            camera.zoom += 0.05;
            gridWidth += 2;
            gridHeight += 2;
        }

        level = new Level(gridWidth, gridHeight, world);
        map = level.getMap();
        labyrinth = new LabyrinthGenerator(map, map.getTile(startX, startY), world);

        Tile start = map.getTile(1, 1);
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
