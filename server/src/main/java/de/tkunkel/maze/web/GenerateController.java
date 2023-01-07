package de.tkunkel.maze.web;

import com.google.gson.Gson;
import de.tkunkel.maze.generator.ImageGenerator;
import de.tkunkel.maze.generator.MazeGenerator;
import de.tkunkel.maze.generator.RectangleGenerator;
import de.tkunkel.maze.output.RenderHtml;
import de.tkunkel.maze.output.RenderImage;
import de.tkunkel.maze.solver.DijkstraSolver;
import de.tkunkel.maze.types.GenerationWithSolution;
import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@RestController()
public class GenerateController {
    private final Gson gson = new Gson();

    private final DijkstraSolver dijkstraSolver;
    private final RectangleGenerator generator;
    private final ImageGenerator imageGenerator;
    private final MazeGenerator mazeGenerator;
    private final RenderHtml renderer;
    private final RenderImage renderImage;

    public GenerateController(DijkstraSolver dijkstraSolver,
                              RectangleGenerator generator,
                              ImageGenerator imageGenerator,
                              MazeGenerator mazeGenerator,
                              RenderHtml renderer,
                              RenderImage renderImage) {
        this.dijkstraSolver = dijkstraSolver;
        this.generator = generator;
        this.imageGenerator = imageGenerator;
        this.mazeGenerator = mazeGenerator;
        this.renderer = renderer;
        this.renderImage = renderImage;
    }

    @RequestMapping(value = "/api/generate/text",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public @ResponseBody String asText() {
        Location start = new Location(0, 0);
        Location finish = new Location(9, 9);
        Maze maze = generator.generate(start, finish, 10, 10);
        dijkstraSolver.solve(maze);
        return renderer.renderToString(maze);
    }

    @RequestMapping(value = "/api/generate/small",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public @ResponseBody String asSmallText() {
        Location start = new Location(0, 0);
        Location finish = new Location(5, 5);
        Maze maze = generator.generate(start, finish, 5, 5);
        dijkstraSolver.solve(maze);
        return renderer.renderToString(maze);
    }

    @RequestMapping(value = "/api/generate/fromImage",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public @ResponseBody String fromImage() throws IOException {
        Path imageFile = Paths.get("E:\\IdeaProjects\\maze\\examples\\maze_round_1.jpg");
        Maze maze = imageGenerator.createFromImage(imageFile);
        mazeGenerator.fill(maze);
        dijkstraSolver.solve(maze);
        return renderer.renderToString(maze);
    }


    @RequestMapping(value = "/api/generate/fromImageToImage",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] fromImageToImage() throws IOException {
        Path imageFile = Paths.get("E:\\IdeaProjects\\maze\\examples\\maze_round_1.jpg");
        Maze maze = imageGenerator.createFromImage(imageFile);
        mazeGenerator.fill(maze);
        dijkstraSolver.solve(maze);

        String format = "JPG";
        BufferedImage bi = renderImage.render(maze, 25, 4, false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);

        return baos.toByteArray();
    }


    public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = File.createTempFile(multipart.getName(), "tmp");
        multipart.transferTo(convFile);
        return convFile;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleException(
            Exception exception
    ) {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            Exception exception,
            String message,
            HttpStatus httpStatus
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus.value(),
                message
        );

        errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @RequestMapping(value = "/api/generate/forWebWithSolution",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody String forWeb(@RequestPart(value = "sourceImage") final MultipartFile aFile) throws IOException {
        Path path = Path.of(multipartToFile(aFile).toURI());
        BufferedImage read = ImageIO.read(path.toFile());
        if ((read.getWidth() > 300) || (read.getHeight() > 300)) {
            throw new IOException("Image too big, max width/height=300 pixels");
        }

        Maze maze = imageGenerator.createFromImage(path);
        mazeGenerator.fill(maze);

        String format = "JPG";
        BufferedImage bi = renderImage.render(maze, 25, 4, false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);

        dijkstraSolver.solve(maze);
        BufferedImage biSolution = renderImage.render(maze, 25, 4, true);
        ByteArrayOutputStream baosSolution = new ByteArrayOutputStream();
        ImageIO.write(biSolution, format, baosSolution);

        return gson.toJson(new GenerationWithSolution(
                Base64.getEncoder().encodeToString(baos.toByteArray()),
                Base64.getEncoder().encodeToString(baosSolution.toByteArray()))
        );
    }

}
