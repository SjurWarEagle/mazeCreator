package org.de.tkunkel.maze.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Maze {
    private static final int SIZE_OF_CELL = 5;
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
                ).collect(Collectors.toList())
                .get(0);
    }

    @Override
    public String toString() {
        char[][] cellChars = new char[SIZE_OF_CELL * width][SIZE_OF_CELL * height];
        for (int x = 0; x < width * SIZE_OF_CELL; x++) {
            for (int y = 0; y < height * SIZE_OF_CELL; y++) {
                cellChars[x][y] = ' ';
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int line = 0;
                char[][] singleCellChars = getCell(x, y).toCharArray();
                for (char[] singleCellRowChars : singleCellChars) {
                    int i = 0;
                    for (char singleCellRowChar : singleCellRowChars) {
                        cellChars[x * SIZE_OF_CELL + i][y * SIZE_OF_CELL + line] = singleCellRowChar;
                        i++;
                    }
                    line++;
                }
            }
        }

        StringBuilder cellsText = new StringBuilder();
        cellsText.append("\n");

        for (int h = 0; h < SIZE_OF_CELL; h++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    char[][] chars = getCell(x, y).toCharArray();
                    for (int j = 0; j < SIZE_OF_CELL; j++) {
                        cellsText.append(chars[h][j]);
                    }
                }
                cellsText.append("\n");
            }
        }

//        for (char[] cellChar : cellChars) {
//            cellsText.append(new String(cellChar));
//            cellsText.append("\n");
//        }
        cellsText.append("\n");

        return "Maze{" +
                "width=" + width +
                ", height=" + height +
                ", start=" + start +
                ", finish=" + finish +
                ", cells=" + cellsText +
                '}';
    }

    public Location getStart() {
        return start;
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
                .collect(Collectors.toList());
        return rc;
    }

    public void removeWalls(Cell cell1, Cell cell2) {
//        System.out.println(cell1.getLocation());
//        System.out.println(cell2.getLocation());
//        System.out.println();
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
