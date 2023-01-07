package de.tkunkel.maze.output;

import de.tkunkel.maze.generator.EmptyPlaneGenerator;
import de.tkunkel.maze.solver.DijkstraSolver;
import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

class RenderImageTest {

    @Test
    void render_100x100() {
        RenderImage renderImage=new RenderImage();

        DijkstraSolver solver = new DijkstraSolver();
        Location start = new Location(0, 0);
        Location finish = new Location(1, 0);

        EmptyPlaneGenerator emptyPlaneGenerator = new EmptyPlaneGenerator();
        Maze maze = emptyPlaneGenerator.generate(start, finish, 100, 100);
        solver.solve(maze);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        renderImage.render(maze,50,1,false);

        Assertions.assertNotNull(maze);
        stopWatch.stop();
        Assertions.assertTrue(stopWatch.getTotalTimeMillis() < 2_000, "rendering too slow (" + stopWatch.getTotalTimeMillis() + "ms)");
    }
}
