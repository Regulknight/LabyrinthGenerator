import java.util.*;

public class Branch {
    private LinkedList<Cell> cells;
    private Cell currentCell;
    private LinkedList<Cell> lockedCells;




//    public Branch updateBranch(WorldMap map) {
//        Random r = new Random();
//        List<Direction> directionsToGrow = getGrowDirectionsOrder(map);
//
//        for (int i = 0; i < r.nextInt(directionsToGrow.size()); i++) {
//            grow(map, directionsToGrow.get(i));
//        }
//
//        return new Branch();
//    }

//    private void grow(WorldMap map, Direction direction) {
//        if (direction == Direction.RIGHT) growToCell(map.getCell(currentCell.getX() + 1, currentCell.getY()));
//        if (direction == Direction.LEFT) growToCell(map.getCell(currentCell.getX() - 1, currentCell.getY()));
//        if (direction == Direction.UP) growToCell(map.getCell(currentCell.getX(), currentCell.getY() + 1));
//        if (direction == Direction.DOWN) growToCell(map.getCell(currentCell.getX(), currentCell.getY() - 1));
//    }
//
//    private List<Direction> getGrowDirectionsOrder(WorldMap map) {
//        List<Direction> directionList = new ArrayList<>();
//
//        if (currentCell.canGrowUp(map)) directionList.add(Direction.UP);
//        if (currentCell.canGrowDown(map)) directionList.add(Direction.DOWN);
//        if (currentCell.canGrowLeft(map)) directionList.add(Direction.LEFT);
//        if (currentCell.canGrowRight(map)) directionList.add(Direction.RIGHT);
//
//        Collections.shuffle(directionList);
//
//        return directionList;
//    }

    private void growToCell(Cell cell) {
        currentCell = cell;
        cells.add(currentCell);
    }
}
