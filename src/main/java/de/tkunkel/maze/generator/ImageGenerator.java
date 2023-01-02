package de.tkunkel.maze.generator;

import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Generates the shape of the maze based on an image (pixels).
 */
@Service
public class ImageGenerator {
    Logger LOG = LoggerFactory.getLogger(ImageGenerator.class);

    public Maze createFromImage(Path imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile.toFile());
        int width = image.getWidth();
        int height = image.getHeight();
        LOG.info("Reading image " + imageFile.getFileName() + " with width=" + width + ",height=" + height);

        Location start = findStartInImage(image,0xFF0000);
        Location finish = findFinishInImage(image,0x0000FF);
        Maze maze = new Maze(width, height, start, finish);

        return maze;
    }

    private Location findStartInImage(BufferedImage image, int color) {
        return null;
    }
    private Location findFinishInImage(BufferedImage image, int color) {
        return null;
    }
}
