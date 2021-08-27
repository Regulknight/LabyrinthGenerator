package com.lon.game.logic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lon.game.logic.cell.Cell;
import com.lon.game.logic.utils.BodyBuilder;

import java.util.LinkedList;
import java.util.List;

import static com.lon.game.logic.utils.Constatns.CELL_SIZE;

public class Level {
    private World world;
    private WorldMap map;
    private List<Body> levelBorders = new LinkedList<>();

    public Level(int gridWidth, int gridHeight, World world) {
        this.world = world;
        this.map = new WorldMap(gridWidth, gridHeight, world);
        
        createBorders(gridWidth, gridHeight);
    }
    
    private void createBorders(int gridWidth, int gridHeight) {
        levelBorders.add(BodyBuilder.createBox(world, -CELL_SIZE/2.f - 5, -CELL_SIZE/2.f + gridHeight/2.f * CELL_SIZE, 10, gridHeight * CELL_SIZE + CELL_SIZE/2, true, true));
        levelBorders.add(BodyBuilder.createBox(world, gridWidth/2.f * CELL_SIZE - CELL_SIZE/2.f, -CELL_SIZE/2.f - 5, gridWidth * CELL_SIZE, 10, true, true));
        levelBorders.add(BodyBuilder.createBox(world, gridWidth * CELL_SIZE - CELL_SIZE/2.f + 5, -CELL_SIZE/2.f + gridHeight/2.f * CELL_SIZE, 10, gridHeight * CELL_SIZE + CELL_SIZE/2, true, true));
        levelBorders.add(BodyBuilder.createBox(world, gridWidth/2.f * CELL_SIZE - CELL_SIZE/2.f, gridHeight * CELL_SIZE - CELL_SIZE/2.f + 5, gridWidth * CELL_SIZE, 10, true, true));
    }

    public void destroy() {
        for (Body body: levelBorders) {
            this.world.destroyBody(body);
        }

        for (List<Cell> row: map.getMap()) {
            for (Cell cell: row) {
                if (cell.getBody() != null) {
                    this.world.destroyBody(cell.getBody());
                }
            }
        }

        this.world = null;
        this.map = null;
    }

    public WorldMap getMap() {
        return map;
    }
}
