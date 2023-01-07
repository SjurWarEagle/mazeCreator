package de.tkunkel.maze.solver;

import de.tkunkel.maze.generator.EmptyPlaneGenerator;
import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

class DijkstraSolverTest {

    @Test
    void solve_500x500() {
        DijkstraSolver solver = new DijkstraSolver();
        Location start = new Location(0, 0);
        Location finish = new Location(1, 0);

        EmptyPlaneGenerator emptyPlaneGenerator = new EmptyPlaneGenerator();
        Maze maze = emptyPlaneGenerator.generate(start, finish, 500, 500);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        solver.solve(maze);

        Assertions.assertNotNull(maze);
        stopWatch.stop();
        Assertions.assertTrue(stopWatch.getTotalTimeMillis() < 2_000, "generation too slow (" + stopWatch.getTotalTimeMillis() + "ms)");
//        Assertions.assertEquals(0, maze.getCell(0, 0).getDistanceFromStart());
//        Assertions.assertEquals(1, maze.getCell(1, 0).getDistanceFromStart());
//        Assertions.assertEquals(200, maze.getCell(2, 0).getDistanceFromStart());
//        Assertions.assertFalse(maze.getCell(2, 1).isNorthWall());
//        Assertions.assertFalse(maze.getCell(2, 1).isWestWall());
//        Assertions.assertTrue(maze.getCell(2, 1).isEastWall());
//        Assertions.assertFalse(maze.getCell(2, 1).isSouthWall());
//        Assertions.assertEquals(5, maze.getCell(2, 1).getDistanceFromStart());
//        Assertions.assertEquals(4, maze.getCell(2, 2).getDistanceFromStart());
    }
    @Test
    void solve_empty_3x3() {
        DijkstraSolver solver = new DijkstraSolver();
        Location start = new Location(0, 0);
        Location finish = new Location(1, 0);

        EmptyPlaneGenerator emptyPlaneGenerator = new EmptyPlaneGenerator();
        Maze maze = emptyPlaneGenerator.generate(start, finish, 3, 3);

        solver.solve(maze);

        Assertions.assertNotNull(maze);
        Assertions.assertEquals(0, maze.getCell(0, 0).getDistanceFromStart());
        Assertions.assertEquals(1, maze.getCell(1, 0).getDistanceFromStart());
        Assertions.assertEquals(6, maze.getCell(2, 0).getDistanceFromStart());
        Assertions.assertFalse(maze.getCell(2, 1).isNorthWall());
        Assertions.assertFalse(maze.getCell(2, 1).isWestWall());
        Assertions.assertTrue(maze.getCell(2, 1).isEastWall());
        Assertions.assertFalse(maze.getCell(2, 1).isSouthWall());
        Assertions.assertEquals(5, maze.getCell(2, 1).getDistanceFromStart());
        Assertions.assertEquals(4, maze.getCell(2, 2).getDistanceFromStart());
    }
}
