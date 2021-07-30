package com.lon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lon.game.logic.*;
import com.lon.game.logic.cell.Cell;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LGenGame extends ApplicationAdapter {
	public static int cellSize = 40;
	SpriteBatch batch;
	Texture floor;
	Texture light;
	Texture player;
	Texture wall;
	WorldMap map;
	OrthographicCamera camera;
	Vector2 playerPosition = new Vector2(6, 6);
	Angle playerDirection = new Angle(0);

	double viewAngle = Math.PI/2;
	double coneRadius = 250;

	Map<String, Texture> textureMap;

	@Override
	public void create () {
		map = new WorldMap(40, 40, cellSize);

		batch = new SpriteBatch();
		floor = new Texture("floor.png");
		light = new Texture("light.png");
		player = new Texture("player.png");
		wall = new Texture("wall.png");

		textureMap = new HashMap<>();
		textureMap.put("floor", floor);
		textureMap.put("player", player);
		textureMap.put("light", light);
		textureMap.put("wall", wall);

		camera = new OrthographicCamera(1024, 768);
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

		if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
			playerDirection = playerDirection.addAngle(new Angle(Math.PI / 2));
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.D)) {
			playerDirection = playerDirection.addAngle(new Angle(-Math.PI / 2));
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.S)) {
			playerPositionChange.x -= 1;
			playerPositionChange.rotateAroundDeg(new Vector2(0, 0), playerDirection.getAngleDeg());
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
			playerPositionChange.x += 1;
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

		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			System.out.println("x: " + playerPosition.x);
			System.out.println("y: " + playerPosition.y);
			System.out.println("ang: " + playerDirection.getAngle());
		}

		playerPosition.add(playerPositionChange);
		camera.translate(playerPositionChange.scl(cellSize));


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
		batch.draw(player, playerPosition.x * cellSize, playerPosition.y * cellSize, cellSize/2.f, cellSize/2.f, cellSize, cellSize, 1, 1, playerDirection.getAngleDeg(), 1, 1, 50, 50, false, false);
	}

	private void renderPlayerVision(ConeOfView coneOfView) {
		for (List<Cell> row: map.getMap()) {
			for (Cell cell: row) {
				if (coneOfView.isPointContainInCone(new Vector2(playerPosition.x * cellSize + cellSize/2.f, playerPosition.y * cellSize + cellSize/2.f), cell.getPosition())) {
					batch.draw(light, cell.getX(), cell.getY(), cellSize, cellSize);
				}
			}
		}
	}

	private void drawCell(Cell cell) {
		batch.draw(textureMap.get(cell.getTextureKey()), cell.getX(), cell.getY(), cellSize, cellSize);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		floor.dispose();
		light.dispose();
		player.dispose();
		wall.dispose();
	}
}
