package de.tkunkel.maze.generator;

import de.tkunkel.maze.types.Cell;
import de.tkunkel.maze.types.Direction;
import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.springframework.stereotype.Service;

/**
 * Generates a rectangular labyrinth without any special forms.
 */
@Service
public class EmptyPlaneGenerator {

    public EmptyPlaneGenerator() {
    }

    public Maze generate(Location start, Location finish, int width, int height) {
        Maze maze = new Maze(width, height, start, finish);

        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                Cell cell = maze.getCell(x, y);
                if (y != 0) {
                    cell.removeWall(Direction.NORTH);
                }
                if (x != maze.getWidth() - 1) {
                    cell.removeWall(Direction.EAST);
                }
                if (y != maze.getHeight() - 1) {
                    cell.removeWall(Direction.SOUTH);
                }
                if (x != 0) {
                    cell.removeWall(Direction.WEST);
                }
            }
        }
        return maze;
    }
}
