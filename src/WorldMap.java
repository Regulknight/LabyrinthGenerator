import java.util.LinkedList;
import java.util.List;

public class WorldMap {
    private final int width;
    private final int height;

    private final List<List<Cell>> map;

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;

        this.map = createMap();
    }

    private List<List<Cell>> createMap() {
        List<List<Cell>> map = new LinkedList<>();

        for (int i = 0; i < height; i++) {
            List<Cell> row = new LinkedList<>();
            for (int j = 0; j < width; j++) {
                row.add(new Cell(j, i));
            }
            map.add(row);
        }

        return map;
    }

    public boolean isWallCell(int x, int y) {
        if ((x < 0 && x >= width) || (y < 0 && y >= height)) {
            return false;
        }

        return getCell(x, y).isWall();
    }

    public boolean isFreeArea(int aX, int aY, int bX, int bY) {
        for (int i = aX; i < bX; i++) {
            for (int j = aY; j < bY; j++) {
                if (getCell(i, j).isWall()) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isLockedCell(int x, int y) {
        return getCell(x, y).isLocked();
    }

    public Cell getCell(int x, int y) {
        return map.get(y).get(x);
    }

    public List<List<Cell>> getMap() {
        return map;
    }
}
