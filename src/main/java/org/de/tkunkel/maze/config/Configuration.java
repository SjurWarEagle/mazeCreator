package org.de.tkunkel.maze.config;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@Component
public class Configuration {

    private String pathOutput;

    @PostConstruct
    public void loadConfig() {
        Gson gson = new Gson();

        String configFile = System.getenv("CONFIG_FILE");
        if (Objects.isNull(configFile)) {
            throw new RuntimeException("Environment variable CONFIG_FILE not set");
        }

        try {
            Configuration configuration = gson.fromJson(Files.readString(Paths.get(configFile)), Configuration.class);
            this.pathOutput = configuration.pathOutput;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPathOutput() {
        return pathOutput;
    }
}
