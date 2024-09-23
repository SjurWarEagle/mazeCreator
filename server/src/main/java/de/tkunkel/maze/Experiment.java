package de.tkunkel.maze;

import de.tkunkel.maze.generator.MazeGenerator;
import de.tkunkel.maze.generator.RectangleGenerator;
import de.tkunkel.maze.output.RenderImage;
import de.tkunkel.maze.output.RenderSolvingProcess;
import de.tkunkel.maze.solver.DijkstraSolver;
import de.tkunkel.maze.types.Cell;
import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Experiment {
    private final Logger LOG = LoggerFactory.getLogger(Experiment.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        new Experiment().start();
    }

    public void start() throws IOException, InterruptedException {
        LOG.debug("Starting...");
        MazeGenerator mazeGenerator = new MazeGenerator();
        RectangleGenerator generator = new RectangleGenerator(mazeGenerator);
        LOG.debug("Generating Maze...");
        int size = 500;
        Maze maze = generator.generate(new Location(1, 1), new Location((int) (Math.random() * size), (int) (Math.random() * size)), size, size);
        int sizeOfCell = 10;


        LOG.debug("Queing rending...");
        BufferedImage mazeImageWithSolution = new RenderImage().render( maze, sizeOfCell,2);
        ImageIO.write(mazeImageWithSolution, "PNG", new File("/tmp/maze_%05d.png".formatted(new Date().getTime())));
        LOG.debug("Waiting to finish...");
        LOG.debug("Done...");
    }

    private int getMaxDistance(Maze maze) {
        int maxDistance = 0;
        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                Cell cell = maze.getCell(x, y);
                if (cell.getDistanceFromStart() > maxDistance) {
                    maxDistance = cell.getDistanceFromStart();
                }
            }
        }
        return maxDistance;
    }
}
