package com.lon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lon.game.logic.Level;
import com.lon.game.logic.TextureMap;
import com.lon.game.logic.WorldMap;
import com.lon.game.logic.angle.Angle;
import com.lon.game.logic.area.ConeOfView;
import com.lon.game.logic.cell.Cell;
import com.lon.game.logic.generator.LabyrinthGenerator;
import com.lon.game.logic.utils.BodyBuilder;

import java.util.List;

import static com.lon.game.logic.utils.Constatns.CELL_SIZE;

public class LGenGame extends ApplicationAdapter {
	public static int cellSize = 40;
	private static final int halfCellSize = cellSize/2;

	private final int gridWidth = 8;
	private final int gridHeight = 8;

	private int score = 0;

	SpriteBatch batch;
	WorldMap map;
	World world;
	OrthographicCamera camera;
	Angle playerDirection = new Angle(0);
	Box2DDebugRenderer b2dr;

	double rotateAngle = 2 * Math.PI;
	double viewAngle = Math.PI/2;
	double coneRadius = 250;
	private final int playerSize = 10;
	private final int halfPlayerSize = playerSize/2;
	Body player;
	float playerLinearSpeed = 2000;
	float playerAngleSpeed = 50;
	BitmapFont font;
	TextureMap textureMap;

	Level level;
	LabyrinthGenerator generator;

	@Override
	public void create () {
		world = new World(new Vector2(0,0), false);
		font = new BitmapFont();
		level = new Level(gridWidth * (score + 1)/2, gridHeight * (score + 1)/2, world);
		map = level.getMap();
		generator = new LabyrinthGenerator(map, map.getCell(0, 0), world);

		player = BodyBuilder.createBox(world, CELL_SIZE/2.f - playerSize/2.f, CELL_SIZE/2.f - playerSize/2.f, playerSize, playerSize, false, false);
		player.setActive(true);
		player.setAwake(true);
		player.setLinearDamping(20f);
		player.setAngularDamping(10f);

		batch = new SpriteBatch();
		textureMap = TextureMap.getInstance();

		b2dr = new Box2DDebugRenderer();

		camera = new OrthographicCamera(1920/4, 1080/4);
		camera.translate((new Vector2(player.getPosition())).scl(cellSize).add((float) (halfPlayerSize), halfPlayerSize));
	}

	@Override
	public void render () {
		world.step(60, 1, 1);

		Gdx.gl20.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);


		batch.begin();
		camera.update();

		generator.step();

		if (generator.checkWinCondition(player.getPosition())) {
			createLevel();
		}

		processUserInput();

		camera.position.set(player.getPosition().x, player.getPosition().y, 0);

		renderMap();
		//renderPlayerVision(new ConeOfView(coneRadius, new Sector(new Angle(player.getAngle()), viewAngle)));
		font.draw(batch, String.valueOf(score), camera.position.x, camera.position.y + CELL_SIZE);
		renderPlayer();
		//b2dr.render(world, camera.combined.cpy());
		//renderTails();



		batch.end();
	}

	private void renderMap() {
		for (List<Cell> row: map.getMap()) {
			for (Cell cell : row) {
				drawCell(cell);
			}
		}
	}

	private void renderPlayer() {
		batch.draw(textureMap.get("player"), player.getPosition().x - halfPlayerSize, player.getPosition().y - halfPlayerSize, halfPlayerSize, halfPlayerSize, playerSize, playerSize, 1, 1, (new Angle(player.getAngle())).getAngleDeg(), 1, 1, 50, 50, false, false);
	}

	private void renderPlayerVision(ConeOfView coneOfView) {


		List<Cell> cellsInConeOfView = map.getCellsFromArea(new Vector2(player.getPosition().x + halfPlayerSize, player.getPosition().y + halfPlayerSize), coneOfView);

		for (Cell cell: cellsInConeOfView) {
			Vector2 cellPosition = new Vector2(cell.getGridPosition()).scl(cellSize);
			batch.draw(textureMap.get("light"), cellPosition.x, cellPosition.y, cellSize, cellSize);
		}
	}

	private void renderTails() {
		for (Cell cell: generator.getPathThree().getTails()) {
			Vector2 cellPosition = new Vector2(cell.getGridPosition()).scl(cellSize);
			batch.draw(textureMap.get("light"), cellPosition.x, cellPosition.y, cellSize, cellSize);
		}
	}

	private void drawCell(Cell cell) {
		Vector2 cellPosition = new Vector2(cell.getGridPosition()).scl(cellSize);
		batch.draw(textureMap.get(cell.getTextureKey()), cellPosition.x, cellPosition.y, cellSize, cellSize);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		b2dr.dispose();
		textureMap.dispose();
	}

	private Vector2 processUserInput() {
		Vector2 playerPositionChange = new Vector2(0 , 0);

		float delta = Gdx.graphics.getDeltaTime();

		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			playerDirection = new Angle(Gdx.input.getX() - camera.viewportWidth/2.f, - Gdx.input.getY() + camera.viewportHeight/2.f);
			player.getTransform().setRotation((float) playerDirection.getRadians());

			player.setLinearVelocity((new Vector2(5 * delta, 0)).rotateAroundDeg(new Vector2(0, 0), player.getAngle()));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.setAngularVelocity((float) (playerAngleSpeed *  delta));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.setAngularVelocity((float) (-playerAngleSpeed *  delta));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.setLinearVelocity((new Vector2(-playerLinearSpeed * delta, 0)).rotateAroundRad(new Vector2(0, 0), player.getAngle()));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.setLinearVelocity((new Vector2(playerLinearSpeed * delta, 0)).rotateAroundRad(new Vector2(0, 0), player.getAngle()));
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			viewAngle += Math.PI/16;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			viewAngle -= Math.PI/16;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.zoom += 0.1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.zoom -= 0.1;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			rotateAngle += Math.PI/32;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			rotateAngle -= Math.PI/32;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {

		}

		return playerPositionChange;
	}

	private void createLevel() {
		level.destroy();

		level = new Level(gridWidth * (score + 1)/2, gridHeight * (score + 1)/2, world);
		map = level.getMap();
		generator = new LabyrinthGenerator(map, map.getCell(0, 0), world);
		score++;
		player.setTransform(CELL_SIZE/2.f - playerSize/2.f, CELL_SIZE/2.f - playerSize/2.f, 0);
	}
}
