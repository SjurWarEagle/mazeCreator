package de.tkunkel.maze.output;

import de.tkunkel.maze.generator.EmptyPlaneGenerator;
import de.tkunkel.maze.generator.MazeGenerator;
import de.tkunkel.maze.solver.DijkstraSolver;
import de.tkunkel.maze.tiles.TilesetCutter;
import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class RenderTiledImageTest {

    @Test
    void render_10x10() throws IOException {
        BufferedImage tileSetImage = ImageIO.read(new File("src/test/resources/de/tkunkel/maze/generator/32x32_outdoor.png"));
        TilesetCutter tilesetCutter = new TilesetCutter();
        tilesetCutter.cutTileSet(tileSetImage, 32);

        RenderTiledImage renderImage = new RenderTiledImage(tilesetCutter);

        DijkstraSolver solver = new DijkstraSolver();
        Location start = new Location(1, 1);
        Location finish = new Location(9, 9);

        EmptyPlaneGenerator generator = new EmptyPlaneGenerator();
        Maze maze = generator.generate(start, finish, 10, 10);
        MazeGenerator mazeGenerator = new MazeGenerator();
        mazeGenerator.fill(maze);
        solver.solve(maze);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        BufferedImage image = renderImage.render(maze, 32);
//        BufferedImage mazeImageWithSolution = renderImage.renderSolution(image, maze, 50, 1);

        Assertions.assertNotNull(maze);
        stopWatch.stop();
//        Assertions.assertTrue(stopWatch.getTotalTimeMillis() < 2_000, "rendering too slow (" + stopWatch.getTotalTimeMillis() + "ms)");

        ImageIO.write(image, "png", new File("D:/IdeaProjects/maze/server/src/test/resources/de/tkunkel/maze/generator/test.png"));
    }
}
