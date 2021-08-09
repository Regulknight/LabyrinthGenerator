package com.lon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lon.game.logic.*;
import com.lon.game.logic.angle.Angle;
import com.lon.game.logic.area.ConeOfView;
import com.lon.game.logic.area.Sector;
import com.lon.game.logic.cell.Cell;
import com.lon.game.logic.generator.LabyrinthGenerator;

import java.util.List;

public class LGenGame extends ApplicationAdapter {
	public static int cellSize = 40;
	SpriteBatch batch;
	WorldMap map;
	OrthographicCamera camera;
	Vector2 playerPosition = new Vector2(6, 6);
	Angle playerDirection = new Angle(0);

	double rotateAngle = Math.PI/2;
	double viewAngle = Math.PI/2;
	double coneRadius = 250;

	TextureMap textureMap;

	LabyrinthGenerator generator;

	@Override
	public void create () {
		map = new WorldMap(40, 40, cellSize);


		generator = new LabyrinthGenerator(map, map.getCell(0, 0), map.getCell(39, 39));

		batch = new SpriteBatch();
		textureMap = TextureMap.getInstance();

		camera = new OrthographicCamera(1920, 1080);
		camera.translate((new Vector2(playerPosition)).scl(cellSize));
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		camera.update();

		renderMap();

		Vector2 playerPositionChange = new Vector2(0 , 0);

		float delta = Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			playerDirection = playerDirection.addAngle(new Angle(rotateAngle * delta));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			playerDirection = playerDirection.addAngle(new Angle(-rotateAngle *  delta));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			playerPositionChange.x -= 3 * delta;
			playerPositionChange.rotateAroundDeg(new Vector2(0, 0), playerDirection.getAngleDeg());
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			playerPositionChange.x += 3 * delta;
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

		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			generator.generate();
			renderMap();
		}

		playerPosition.add(playerPositionChange);
		camera.translate(new Vector2(playerPositionChange.scl(cellSize)));


		renderPlayerVision(new ConeOfView(coneRadius, new Sector(playerDirection, viewAngle)));
		renderPlayer();

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
		batch.draw(textureMap.get("player"), playerPosition.x * cellSize + 10, playerPosition.y * cellSize + 10, 20/2.f, 20/2.f, 20, 20, 1, 1, playerDirection.getAngleDeg(), 1, 1, 50, 50, false, false);
	}

	private void renderPlayerVision(ConeOfView coneOfView) {
		List<Cell> cellsInConeOfView = map.getCellsFromArea(new Vector2(playerPosition.x * cellSize + cellSize/2.f, playerPosition.y * cellSize + cellSize/2.f), coneOfView);

		for (Cell cell: cellsInConeOfView) {
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
}
