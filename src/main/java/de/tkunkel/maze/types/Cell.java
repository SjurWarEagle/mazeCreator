package de.tkunkel.maze.types;

public class Cell {
//    private static final char CELL_CHAR = 'o';
//    private static final int CELL_HEIGHT = 5;
//    private static final int CELL_WIDTH = 5;
    private boolean northWall = true;
    private boolean eastWall = true;
    private boolean southWall = true;
    private boolean westWall = true;
    private boolean visited = false;
    private boolean blocker = false;
    private final Location location;

    public Cell(Location location) {
        this.location = location;
    }

    public void markVisited() {
        this.visited = true;
    }

    public void markBlocker() {
        this.blocker = true;
    }

    public boolean isBlocker() {
        return blocker;
    }

    public void removeWall(Direction direction) {
        switch (direction) {
            case NORTH -> this.northWall = false;
            case EAST -> this.eastWall = false;
            case SOUTH -> this.southWall = false;
            case WEST -> this.westWall = false;
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

//    @Override
//    public String toString() {
//        String rc = "";
//        if (northWall) {
//            rc += "N";
//        } else {
//            rc += " ";
//        }
//
//        if (eastWall) {
//            rc += "E";
//        } else {
//            rc += " ";
//        }
//
//        if (southWall) {
//            rc += "S";
//        } else {
//            rc += " ";
//        }
//
//        if (westWall) {
//            rc += "W";
//        } else {
//            rc += " ";
//        }
//        return rc;
//    }

//    public char[][] toCharArray() {
//        char[][] text = new char[CELL_HEIGHT][CELL_WIDTH];
//        text[0] = "     ".toCharArray();
//        text[1] = "     ".toCharArray();
//        text[2] = "     ".toCharArray();
//        text[3] = "     ".toCharArray();
//        text[4] = "     ".toCharArray();
//
//        if (northWall) {
//            text[0][0] = CELL_CHAR;
//            text[1][0] = CELL_CHAR;
//            text[2][0] = CELL_CHAR;
//            text[3][0] = CELL_CHAR;
//            text[4][0] = CELL_CHAR;
//        }
//        if (eastWall) {
//            text[CELL_WIDTH - 1][0] = CELL_CHAR;
//            text[CELL_WIDTH - 1][1] = CELL_CHAR;
//            text[CELL_WIDTH - 1][2] = CELL_CHAR;
//            text[CELL_WIDTH - 1][3] = CELL_CHAR;
//            text[CELL_WIDTH - 1][4] = CELL_CHAR;
//        }
//        if (southWall) {
//            text[0][CELL_HEIGHT - 1] = CELL_CHAR;
//            text[1][CELL_HEIGHT - 1] = CELL_CHAR;
//            text[2][CELL_HEIGHT - 1] = CELL_CHAR;
//            text[3][CELL_HEIGHT - 1] = CELL_CHAR;
//            text[4][CELL_HEIGHT - 1] = CELL_CHAR;
//        }
//        if (westWall) {
//            text[0][0] = CELL_CHAR;
//            text[0][1] = CELL_CHAR;
//            text[0][2] = CELL_CHAR;
//            text[0][3] = CELL_CHAR;
//            text[0][4] = CELL_CHAR;
//        }
//
//        text[2][1] = ("" + getLocation().getX()).toCharArray()[0];
//        text[2][3] = ("" + getLocation().getY()).toCharArray()[0];
//
//        if (location.getX() == 0 && location.getY() == 0) {
//            text[2][2] = 'S';
//        }
//
//        return text;
//    }

    public boolean wasVisited() {
        return visited;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isNorthWall() {
        return northWall;
    }

    public boolean isEastWall() {
        return eastWall;
    }

    public boolean isSouthWall() {
        return southWall;
    }

    public boolean isWestWall() {
        return westWall;
    }
}
