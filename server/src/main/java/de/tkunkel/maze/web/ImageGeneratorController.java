package de.tkunkel.maze.web;

import de.tkunkel.maze.generator.MazeFromImageGenerator;
import de.tkunkel.maze.generator.MazeGenerator;
import de.tkunkel.maze.generator.TextImageGenerator;
import de.tkunkel.maze.output.RenderImage;
import de.tkunkel.maze.solver.DijkstraSolver;
import de.tkunkel.maze.types.Maze;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController()
public class ImageGeneratorController {
    private final String format = "PNG";

    private final TextImageGenerator textImageGenerator;
    private final MazeFromImageGenerator mazeFromImageGenerator;
    private final MazeGenerator mazeGenerator;
    private final RenderImage renderImage;
    private final DijkstraSolver dijkstraSolver;

    public ImageGeneratorController(TextImageGenerator textImageGenerator,
                                    MazeFromImageGenerator mazeFromImageGenerator,
                                    MazeGenerator mazeGenerator,
                                    RenderImage renderImage,
                                    DijkstraSolver dijkstraSolver
    ) {
        this.textImageGenerator = textImageGenerator;
        this.mazeFromImageGenerator = mazeFromImageGenerator;
        this.mazeGenerator = mazeGenerator;
        this.renderImage = renderImage;
        this.dijkstraSolver = dijkstraSolver;
    }

    @RequestMapping(value = "/api/createImage/date",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte[] createDateAsImage() throws IOException {

        BufferedImage bi = textImageGenerator.generateImage("2023", 20);
        bi = addMazeWithSolution(bi);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        return baos.toByteArray();
    }

    private BufferedImage addMazeWithSolution(BufferedImage bi) throws IOException {
        Maze maze = mazeFromImageGenerator.createFromImage(bi);
        mazeGenerator.fill(maze);
        dijkstraSolver.solve(maze);

        int sizeOfCell = 10;
//        bi = scale(bi, sizeOfCell);
        bi = renderImage.render(maze, sizeOfCell, 1);
        BufferedImage biSolution = renderImage.renderSolution(bi, maze, sizeOfCell, 1);
        ByteArrayOutputStream baosSolution = new ByteArrayOutputStream();
        ImageIO.write(biSolution, format, baosSolution);

        return biSolution;
    }

    private BufferedImage scale(BufferedImage before, int factor) {
        int w = before.getWidth();
        int h = before.getHeight();

        BufferedImage after = new BufferedImage(w*factor, h*factor, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(factor, factor);

        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        after = scaleOp.filter(before, after);

        return after;
    }
}
