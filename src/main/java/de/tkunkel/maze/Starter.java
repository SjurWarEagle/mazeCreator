package de.tkunkel.maze;

import de.tkunkel.maze.config.Configuration;
import de.tkunkel.maze.generator.RectangleGenerator;
import de.tkunkel.maze.output.RenderHtml;
import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Starter {
    @Autowired
    Configuration configuration;

    @Autowired
    RectangleGenerator generator;

    @Autowired
    RenderHtml renderer;

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }

    //at start and once per hour
    //@Scheduled(fixedRate = 60 * 60 * 1000)
    @PostConstruct
    private void start() {
        Location start = new Location(0, 0);
        Location finish = new Location(9, 9);
        Maze maze = generator.generate(start, finish, 10, 10);
        renderer.renderToFile(maze);
    }

}
