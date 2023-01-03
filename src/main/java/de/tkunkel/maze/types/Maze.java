package de.tkunkel.maze.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Maze {
    private final int width;
    private final int height;
    private final Location start;
    private final Location finish;
    private final Cell[] cells;

    public Maze(int width, int height, Location start, Location finish) {
        this.width = width;
        this.height = height;
        this.start = start;
        this.finish = finish;
        this.cells = new Cell[width * height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x + y * width] = new Cell(new Location(x, y));
            }
        }
    }

    public Cell getCell(Location location) {
        return this.getCell(location.getX(), location.getY());
    }

    public Cell getCell(int x, int y) {
        return Arrays.stream(cells).filter(cell ->
                        cell.getLocation().getY() == y && cell.getLocation().getX() == x
                ).toList()
                .get(0);
    }

    @Override
    public String toString() {
        return "Maze{" +
                "width=" + width +
                ", height=" + height +
                ", start=" + start +
                ", finish=" + finish +
                '}';
    }

    public Location getStart() {
        return start;
    }

    public Location getFinish() {
        return finish;
    }

    public List<Cell> getWalkableOrthogonalNeighbours(Cell current) {
        List<Cell> rc = new ArrayList<>();
        if (current.getLocation().getX() > 0) {
            //get western
            if (!current.isWestWall()) {
                rc.add(getCell(current.getLocation().getX() - 1, current.getLocation().getY()));
            }
        }
        if (current.getLocation().getX() < width - 1) {
            //get eastern
            if (!current.isEastWall()) {
                rc.add(getCell(current.getLocation().getX() + 1, current.getLocation().getY()));
            }
        }
        if (current.getLocation().getY() > 0) {
            //get northern
            if (!current.isNorthWall()) {
                rc.add(getCell(current.getLocation().getX(), current.getLocation().getY() - 1));
            }
        }
        if (current.getLocation().getY() < height - 1) {
            //get southern
            if (!current.isSouthWall()) {
                rc.add(getCell(current.getLocation().getX(), current.getLocation().getY() + 1));
            }
        }

        rc = rc.stream()
                .filter(cell -> !cell.isBlocker())
                .collect(Collectors.toList());
        return rc;
    }

    public List<Cell> getUnvisitedOrthogonalNeighbours(Cell current) {
        List<Cell> rc = new ArrayList<>();
        if (current.getLocation().getX() > 0) {
            // not at border
            rc.add(getCell(current.getLocation().getX() - 1, current.getLocation().getY()));
        }
        if (current.getLocation().getX() < width - 1) {
            // not at border
            rc.add(getCell(current.getLocation().getX() + 1, current.getLocation().getY()));
        }
        if (current.getLocation().getY() > 0) {
            // not at border
            rc.add(getCell(current.getLocation().getX(), current.getLocation().getY() - 1));
        }
        if (current.getLocation().getY() < height - 1) {
            // not at border
            rc.add(getCell(current.getLocation().getX(), current.getLocation().getY() + 1));
        }

        rc = rc.stream()
                .filter(cell -> !cell.wasVisited())
                .filter(cell -> !cell.isBlocker())
                .collect(Collectors.toList());
        return rc;
    }

    public void removeWalls(Cell cell1, Cell cell2) {
        if (cell1.getLocation().getX() < cell2.getLocation().getX()) {
            // cell1 <- cell2
            cell1.removeWall(Direction.EAST);
            cell2.removeWall(Direction.WEST);
        } else if (cell1.getLocation().getX() > cell2.getLocation().getX()) {
            // cell1 -> cell2
            cell1.removeWall(Direction.WEST);
            cell2.removeWall(Direction.EAST);
        } else if (cell1.getLocation().getY() > cell2.getLocation().getY()) {
            // cell1 V cell2
            cell1.removeWall(Direction.NORTH);
            cell2.removeWall(Direction.SOUTH);
        } else if (cell1.getLocation().getY() < cell2.getLocation().getY()) {
            // cell1 ^ cell2
            cell1.removeWall(Direction.SOUTH);
            cell2.removeWall(Direction.NORTH);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
