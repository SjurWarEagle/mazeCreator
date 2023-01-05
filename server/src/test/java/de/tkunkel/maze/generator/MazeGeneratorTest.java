package de.tkunkel.maze.generator;

import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

class MazeGeneratorTest {

    @Test
    public void test_100() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        EmptyPlaneGenerator generator = new EmptyPlaneGenerator();
        Location start = new Location(0, 0);
        Location finish = new Location(90, 90);
        Maze maze = generator.generate(start, finish, 100, 100);
        MazeGenerator mazeGenerator = new MazeGenerator();
        mazeGenerator.fill(maze);
        stopWatch.stop();
        Assertions.assertTrue(stopWatch.getTotalTimeMillis() < 2_000, "generation too slow (" + stopWatch.getTotalTimeMillis() + "ms)");
    }

    @Test
    public void test_200() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        EmptyPlaneGenerator generator = new EmptyPlaneGenerator();
        Location start = new Location(0, 0);
        Location finish = new Location(90, 90);
        Maze maze = generator.generate(start, finish, 200, 200);
        MazeGenerator mazeGenerator = new MazeGenerator();
        mazeGenerator.fill(maze);
        stopWatch.stop();
        Assertions.assertTrue(stopWatch.getTotalTimeMillis() < 10_000, "generation too slow (" + stopWatch.getTotalTimeMillis() + "ms)");
    }

}
