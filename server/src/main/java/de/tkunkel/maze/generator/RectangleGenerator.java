package de.tkunkel.maze.generator;

import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.springframework.stereotype.Service;

/**
 * Generates a rectangular labyrinth without any special forms.
 */
@Service
public class RectangleGenerator {
    private final MazeGenerator mazeGenerator;

    public RectangleGenerator(MazeGenerator mazeGenerator) {
        this.mazeGenerator = mazeGenerator;
    }

    public Maze generate(Location start, Location finish, int width, int height) {
        Maze maze = new Maze(width, height, start, finish);
        mazeGenerator.fill(maze);
        return maze;
    }
}
