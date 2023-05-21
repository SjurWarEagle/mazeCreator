package de.tkunkel.maze.generator;

import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Generates the shape of the maze based on an image (pixels).
 */
@Service
public class MazeFromImageGenerator {
    private final Logger LOG = LoggerFactory.getLogger(MazeFromImageGenerator.class);

    public Maze createFromImage(BufferedImage image) {
        Color startColor =  Color.RED;
        Color finishColor =  Color.BLUE;
        Color finishColor2 = Color.GREEN;

        int width = image.getWidth();
        int height = image.getHeight();

        Location start = findStartInImage(image, startColor);
        Location finish = findFinishInImage(image, finishColor);
        if (finish == null) {
            finishColor = finishColor2;
            finish = findFinishInImage(image, finishColor);
        }
        Maze maze = new Maze(width, height, start, finish);
        Color spaceColor = Color.WHITE;
        markBlockers(image, maze, spaceColor, startColor, finishColor);
        return maze;
    }

    public Maze createFromImageFile(Path imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile.toFile());
        int width = image.getWidth();
        int height = image.getHeight();
        LOG.info("Reading image " + imageFile.getFileName() + " with width=" + width + ",height=" + height);

        return createFromImage(image);
    }

    private void markBlockers(BufferedImage image, Maze maze, Color spaceColor, Color startColor, Color finishColor) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color pixel = new Color(image.getRGB(x, y));

                if ((!colorsAreSimilar(pixel, spaceColor))
                        && !(colorsAreSimilar(pixel, startColor))
                        && !(colorsAreSimilar(pixel, finishColor))) {
                    maze.getCell(x, y).markBlocker();
                }
            }
        }
    }

    protected boolean colorsAreSimilar(Color color1, Color color2) {
        int tolerance = 100;
        int r1 = color1.getRed();
        int g1 = color1.getGreen();
        int b1 = color1.getBlue();
        int r2 = color2.getRed();
        int g2 = color2.getGreen();
        int b2 = color2.getBlue();
        return ((Math.abs(r1 - r2) < tolerance) && (Math.abs(g1 - g2) < tolerance) && (Math.abs(b1 - b2) < tolerance));
    }

    private Location findStartInImage(BufferedImage image, Color color) {
        return findPixelInImage(image, color);
    }

    private Location findFinishInImage(BufferedImage image, Color color) {
        return findPixelInImage(image, color);
    }

    protected Location findPixelInImage(BufferedImage image, Color color) {
        int wishR = color.getRed();
        int wishG = color.getGreen();
        int wishB = color.getBlue();
        int threshold = 100;

        int width = image.getWidth();
        int height = image.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color pixel = new Color(image.getRGB(x, y));
                int r = pixel.getRed();
                int g = pixel.getGreen();
                int b = pixel.getBlue();
                if ((Math.abs(r - wishR) < threshold) && (Math.abs(g - wishG) < threshold) && (Math.abs(b - wishB) < threshold)) {
                    return new Location(x, y);
                }
            }
        }
        return null;
    }

}
