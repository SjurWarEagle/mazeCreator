package de.tkunkel.maze;

import de.tkunkel.maze.generator.RectangleGenerator;
import de.tkunkel.maze.output.RenderHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Starter {

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
    }

}
