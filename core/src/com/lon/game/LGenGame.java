package com.lon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lon.game.logic.TextureMap;
import com.lon.game.logic.WorldMap;
import com.lon.game.logic.angle.Angle;
import com.lon.game.logic.area.ConeOfView;
import com.lon.game.logic.area.Sector;
import com.lon.game.logic.cell.Cell;
import com.lon.game.logic.generator.LabyrinthGenerator;

import java.util.List;

public class LGenGame extends ApplicationAdapter {
	public static int cellSize = 20;
	SpriteBatch batch;
	WorldMap map;
	World world;
	OrthographicCamera camera;
	Vector2 playerPosition = new Vector2(6, 6);
	Angle playerDirection = new Angle(0);

	double rotateAngle = 2 * Math.PI;
	double viewAngle = Math.PI/2;
	double coneRadius = 250;
	private int playerSize = 10;

	TextureMap textureMap;

	LabyrinthGenerator generator;

	@Override
	public void create () {
		map = new WorldMap(60, 60, cellSize);

		world = new World(new Vector2(0,0), false);

		generator = new LabyrinthGenerator(map, map.getCell(30, 30), map.getCell(34, 24));

		batch = new SpriteBatch();
		textureMap = TextureMap.getInstance();

		camera = new OrthographicCamera(1920, 1080);
		camera.translate((new Vector2(playerPosition)).scl(cellSize).add((float) (playerSize/2.f), playerSize/2.f));

		Thread t = new Thread(generator);
		t.start();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		camera.update();

		Vector2 playerPositionChange = processUserInput();

		playerPosition.add(playerPositionChange);
		camera.translate(new Vector2(playerPositionChange.scl(cellSize)));

		renderMap();
		renderPlayerVision(new ConeOfView(coneRadius, new Sector(playerDirection, viewAngle)));
		renderPlayer();
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
		batch.draw(textureMap.get("player"), playerPosition.x * cellSize, playerPosition.y * cellSize, playerSize/2.f, playerSize/2.f, playerSize, playerSize, 1, 1, playerDirection.getAngleDeg(), 1, 1, 50, 50, false, false);
	}

	private void renderPlayerVision(ConeOfView coneOfView) {
		List<Cell> cellsInConeOfView = map.getCellsFromArea(new Vector2(playerPosition.x * cellSize + playerSize/2.f, playerPosition.y * cellSize + playerSize/2.f), coneOfView);

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
		textureMap.dispose();
	}

	private Vector2 processUserInput() {
		Vector2 playerPositionChange = new Vector2(0 , 0);

		float delta = Gdx.graphics.getDeltaTime();

		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			playerDirection = new Angle(Gdx.input.getX() - camera.viewportWidth/2.f, - Gdx.input.getY() + camera.viewportHeight/2.f);
			playerPositionChange.x += 5 * delta;
			playerPositionChange.rotateAroundDeg(new Vector2(0, 0), playerDirection.getAngleDeg());
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			playerDirection = playerDirection.addAngle(new Angle(rotateAngle * delta));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			playerDirection = playerDirection.addAngle(new Angle(-rotateAngle *  delta));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			playerPositionChange.x -= 5 * delta;
			playerPositionChange.rotateAroundDeg(new Vector2(0, 0), playerDirection.getAngleDeg());
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			playerPositionChange.x += 5 * delta;
			playerPositionChange.rotateAroundDeg(new Vector2(0, 0), playerDirection.getAngleDeg());
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			viewAngle += Math.PI/16;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			viewAngle -= Math.PI/16;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			coneRadius += 15;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			coneRadius -= 15;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			rotateAngle += Math.PI/32;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			rotateAngle -= Math.PI/32;
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

		}

		return playerPositionChange;
	}
}
