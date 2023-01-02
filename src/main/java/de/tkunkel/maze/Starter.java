package de.tkunkel.maze;

import de.tkunkel.maze.config.Configuration;
import de.tkunkel.maze.generator.EmptyPlaneGenerator;
import de.tkunkel.maze.output.RenderHtml;
import de.tkunkel.maze.types.Maze;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
//@EnableScheduling
//@EntityScan(basePackageClasses = Starter.class)
public class Starter {
    @Autowired
    Configuration configuration;

    @Autowired
    EmptyPlaneGenerator generator;

    @Autowired
    RenderHtml renderer;

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }

    //at start and once per hour
    //@Scheduled(fixedRate = 60 * 60 * 1000)
    @PostConstruct
    private void start() {
        Maze maze = generator.generate(10, 10);
        renderer.renderToFile(maze);
    }

}
