package de.tkunkel.maze.generator;

import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

class ImageGeneratorTest {


    @Test
    public void createFromImage_mediumImage() throws URISyntaxException, IOException {
        ImageGenerator generator=new ImageGenerator();
        URL resource = getClass().getResource("100x100.jpg");
        Assertions.assertNotNull(resource);
        Path imageFile=Paths.get(resource.toURI());
        Maze mazeFromFile = generator.createFromImage(imageFile);
        Assertions.assertNotNull(mazeFromFile);
        Assertions.assertEquals(100,mazeFromFile.getWidth());
        Assertions.assertEquals(100,mazeFromFile.getWidth());
    }

    @Test
    public void createFromImage() throws URISyntaxException, IOException {
        ImageGenerator generator=new ImageGenerator();
        URL resource = getClass().getResource("maze_round_1.jpg");
        Assertions.assertNotNull(resource);
        Path imageFile=Paths.get(resource.toURI());
        Maze mazeFromFile = generator.createFromImage(imageFile);
        Assertions.assertNotNull(mazeFromFile);
        Assertions.assertEquals(20,mazeFromFile.getWidth());
        Assertions.assertEquals(20,mazeFromFile.getWidth());
    }

    @Test
    public void createFromImage_200x200() throws URISyntaxException, IOException {
        ImageGenerator generator=new ImageGenerator();
        URL resource = getClass().getResource("200x200.jpg");
        Assertions.assertNotNull(resource);
        Path imageFile=Paths.get(resource.toURI());
        Maze mazeFromFile = generator.createFromImage(imageFile);
        Assertions.assertNotNull(mazeFromFile);
        Assertions.assertEquals(200,mazeFromFile.getWidth());
        Assertions.assertEquals(200,mazeFromFile.getWidth());
    }

    @Test
    public void findPixelInImage_simple() throws URISyntaxException, IOException {
        ImageGenerator generator=new ImageGenerator();
        URL resource = getClass().getResource("maze_round_1.jpg");
        Assertions.assertNotNull(resource);
        Path imageFile=Paths.get(resource.toURI());
        BufferedImage image = ImageIO.read(imageFile.toFile());
        Location locationStart = generator.findPixelInImage(image, new Color(0xFF0000));
        Assertions.assertNotNull(locationStart);
        Assertions.assertEquals(5,locationStart.getX());
        Assertions.assertEquals(5,locationStart.getY());
        Location locationFinish = generator.findPixelInImage(image, new Color(0x0000FF));
        Assertions.assertNotNull(locationFinish);
        Assertions.assertEquals(14,locationFinish.getX());
        Assertions.assertEquals(12,locationFinish.getY());
    }

    @Test
    public void coorsAreSimilar_simple(){
        ImageGenerator generator=new ImageGenerator();
        boolean rc =generator.colorsAreSimilar(new Color(0xFF0000),new Color(0xFF0000));
        Assertions.assertTrue(rc);
        rc =generator.colorsAreSimilar(new Color(0xFF00FF),new Color(0xFF00FF));
        Assertions.assertTrue(rc);
        rc =generator.colorsAreSimilar(new Color(0xFF00F0),new Color(0xFF00FF));
        Assertions.assertTrue(rc);

        rc =generator.colorsAreSimilar(new Color(0xFF0000),new Color(0x0000FF));
        Assertions.assertFalse(rc);
    }
}
