package org.de.tkunkel.maze;

import org.de.tkunkel.maze.config.Configuration;
import org.de.tkunkel.maze.generator.EmptyPlaneGenerator;
import org.de.tkunkel.maze.generator.MazeGenerator;
import org.de.tkunkel.maze.output.RenderHtml;
import org.de.tkunkel.maze.types.Maze;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackageClasses = Starter.class)
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
        renderer.render(maze);
    }

}
