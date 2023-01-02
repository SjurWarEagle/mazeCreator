package de.tkunkel.maze.generator;

import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.springframework.stereotype.Service;

/**
 * Generates a rectangular labyrinth without any special forms.
 */
@Service
public class EmptyPlaneGenerator {
    private final MazeGenerator mazeGenerator;

    public EmptyPlaneGenerator(MazeGenerator mazeGenerator) {
        this.mazeGenerator = mazeGenerator;
    }

    public Maze generate(int width, int height) {
        Location start = new Location(0, 0);
        Location finish = new Location(width - 2, height - 2);
        Maze maze = new Maze(width, height, start, finish);
        mazeGenerator.fill(maze);
        return maze;
    }
}
