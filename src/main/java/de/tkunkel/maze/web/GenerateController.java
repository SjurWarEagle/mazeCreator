package de.tkunkel.maze.web;

import de.tkunkel.maze.config.Configuration;
import de.tkunkel.maze.generator.EmptyPlaneGenerator;
import de.tkunkel.maze.generator.ImageGenerator;
import de.tkunkel.maze.generator.MazeGenerator;
import de.tkunkel.maze.output.RenderHtml;
import de.tkunkel.maze.types.Maze;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController()
public class GenerateController {
    @Autowired
    Configuration configuration;

    @Autowired
    EmptyPlaneGenerator generator;

    @Autowired
    ImageGenerator imageGenerator;

    @Autowired
    MazeGenerator mazeGenerator;

    @Autowired
    RenderHtml renderer;

    @RequestMapping(value = "/generate/text",
            method = RequestMethod.GET,
//            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public @ResponseBody String asText() {
        Maze maze = generator.generate(10, 10);
        return renderer.renderToString(maze);
    }

    @RequestMapping(value = "/generate/small",
            method = RequestMethod.GET,
//            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public @ResponseBody String asSmallText() {
        Maze maze = generator.generate(5, 5);
        return renderer.renderToString(maze);
    }

    @RequestMapping(value = "/generate/image",
            method = RequestMethod.GET,
//            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public @ResponseBody String fromImage() throws IOException {
        Path imageFile= Paths.get("E:\\IdeaProjects\\maze\\examples\\maze_round_1.jpg");
        Maze maze = imageGenerator.createFromImage(imageFile);
        mazeGenerator.fill(maze);
        return renderer.renderToString(maze);
    }

}
