package de.tkunkel.maze.web;

import de.tkunkel.maze.generator.ImageGenerator;
import de.tkunkel.maze.generator.MazeGenerator;
import de.tkunkel.maze.generator.RectangleGenerator;
import de.tkunkel.maze.output.RenderHtml;
import de.tkunkel.maze.solver.DijkstraSolver;
import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController()
public class GenerateController {
    private final DijkstraSolver dijkstraSolver;
    private final RectangleGenerator generator;
    private final ImageGenerator imageGenerator;
    private final MazeGenerator mazeGenerator;
    private final RenderHtml renderer;

    public GenerateController(DijkstraSolver dijkstraSolver, RectangleGenerator generator, ImageGenerator imageGenerator, MazeGenerator mazeGenerator, RenderHtml renderer) {
        this.dijkstraSolver = dijkstraSolver;
        this.generator = generator;
        this.imageGenerator = imageGenerator;
        this.mazeGenerator = mazeGenerator;
        this.renderer = renderer;
    }

    @RequestMapping(value = "/generate/text",
            method = RequestMethod.GET,
//            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public @ResponseBody String asText() {
        Location start = new Location(0, 0);
        Location finish = new Location(9, 9);
        Maze maze = generator.generate(start, finish, 10, 10);
        dijkstraSolver.solve(maze);
        return renderer.renderToString(maze);
    }

    @RequestMapping(value = "/generate/small",
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

    @RequestMapping(value = "/generate/image",
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

}
