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
	int cellSize = 30;
	SpriteBatch batch;
	Texture floor;
	Texture light;
	Texture player;
	Texture wall;
	WorldMap map;
	OrthographicCamera camera;
	Vector2 playerPosition = new Vector2(210, 210);
	Angle playerDirection = new Angle(0);

	Map<String, Texture> textureMap;

	@Override
	public void create () {
		map = new WorldMap(30, 30, cellSize);

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

		camera = new OrthographicCamera(1000, 1000);
		camera.translate(playerPosition);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		camera.update();

		renderMap();

		Vector2 playerPositionChange = new Vector2(0 , 0);

		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			playerDirection = playerDirection.addAngle(new Angle(Math.PI / 2));
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			playerDirection = playerDirection.addAngle(new Angle(-Math.PI / 2));
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			playerPositionChange.x -= 30;
			playerPositionChange.rotateAroundDeg(new Vector2(0, 0), playerDirection.getAngleDeg());
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			playerPositionChange.x += 30;
			playerPositionChange.rotateAroundDeg(new Vector2(0, 0), playerDirection.getAngleDeg());
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			System.out.println(playerPosition.x);
			System.out.println(playerPosition.y);
			System.out.println(playerDirection.getAngle());
		}
		
		camera.translate(playerPositionChange);

		playerPosition.add(playerPositionChange);

		renderPlayerVision(new ConeOfView(250, new Sector(playerDirection, Math.PI/2)));
		renderPlayer();

		batch.end();
	}

	private void renderMap() {
		for (List<Cell> row: map.getMap()) {
			for (Cell cell : row) {
				batch.draw(textureMap.get(cell.getTextureKey()), cell.getX(), cell.getY(), cellSize, cellSize);
			}
		}
	}

	private void renderPlayer() {
		batch.draw(player, playerPosition.x, playerPosition.y, 15, 15, 30, 30, 1, 1, playerDirection.getAngleDeg(), 1, 1, 50, 50, false, false);
	}

	private void renderPlayerVision(ConeOfView coneOfView) {
		for (List<Cell> row: map.getMap()) {
			for (Cell cell: row) {
				if (coneOfView.isPointContainInCone(new Vector2(playerPosition.x + 15, playerPosition.y + 15), cell.getPosition())) {
					batch.draw(light, cell.getX(), cell.getY(), cellSize, cellSize);
				}
			}
		}
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
