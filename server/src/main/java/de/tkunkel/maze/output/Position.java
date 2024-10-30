package de.tkunkel.maze.output;

public enum Position {
    TOP_LEFT(0,0), TOP_CENTER(1,0), TOP_RIGHT(2,0),
    MID_LEFT(0,1), MID_CENTER(1,1), MID_RIGHT(2,1),
    BOTTOM_LEFT(0,2), BOTTOM_CENTER(1,2), BOTTOM_RIGHT(2,2);

    public final int x;
    public final int y;

    Position(int x, int y) {
        this.x=x;
        this.y=y;
    }
}
