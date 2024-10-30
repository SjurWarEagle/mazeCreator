package de.tkunkel.maze.types;

public class Cell {
    private boolean northWall = true;
    private boolean eastWall = true;
    private boolean southWall = true;
    private boolean westWall = true;
    private boolean visited = false;
    private boolean blocker = false;
    private boolean partOfSolution = false;
    private final Location location;
    private int distanceFromStart = -1;

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

    public void addWall(Direction direction) {
        switch (direction) {
            case NORTH -> this.northWall = true;
            case EAST -> this.eastWall = true;
            case SOUTH -> this.southWall = true;
            case WEST -> this.westWall = true;
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

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

    public void markAsPartOfSolution() {
        this.partOfSolution = true;
    }

    public boolean isPartOfSolution() {
        return partOfSolution;
    }

    public void setDistanceFromStart(int distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }

    public int getDistanceFromStart() {
        return distanceFromStart;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "northWall=" + northWall +
                ", eastWall=" + eastWall +
                ", southWall=" + southWall +
                ", westWall=" + westWall +
                ", visited=" + visited +
                ", blocker=" + blocker +
                ", partOfSolution=" + partOfSolution +
                ", location=" + location +
                ", distanceFromStart=" + distanceFromStart +
                '}';
    }
}
