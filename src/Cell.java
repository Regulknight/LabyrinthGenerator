import java.awt.*;

public class Cell {
    private int x;
    private int y;
    private boolean isWall;
    private boolean isLocked;
    private Color color = Color.LIGHT_GRAY;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.isWall = true;
        this.isLocked = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Color getCellColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean canGrowUp(WorldMap map) {
        return map.isFreeArea(x - 1, y + 1, x + 1, y + 2);
    }

    public boolean canGrowDown(WorldMap map) {
        return map.isFreeArea(x - 1, y - 2, x + 1, y - 1);
    }

    public boolean canGrowLeft(WorldMap map) {
        return map.isFreeArea(x - 2, y - 1, x - 1, y + 1);
    }

    public boolean canGrowRight(WorldMap map) {
        return map.isFreeArea(x + 1, y - 1, x + 2, y + 1);
    }
}
