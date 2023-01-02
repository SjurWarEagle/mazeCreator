package de.tkunkel.maze.output;

import de.tkunkel.maze.config.Configuration;
import de.tkunkel.maze.types.Cell;
import de.tkunkel.maze.types.Maze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class RenderHtml {
    @Autowired
    Configuration configuration;

    Logger LOG = LoggerFactory.getLogger(RenderHtml.class);

    public void render(Maze maze) {
        Path output = Paths.get(configuration.getPathOutput(), "index.html");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(output.toFile()))) {
            bw.write("<html>");
            bw.newLine();
            bw.write("<title>Maze</title>");
            bw.newLine();
            bw.write("<style>");
            bw.newLine();
            bw.write(".cell {");
            bw.newLine();
            bw.write("width: 2em;");
            bw.write("height: 2em;");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.write(".north {");
            bw.newLine();
            bw.write("border-top: 4px solid red;");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.write(".east {");
            bw.newLine();
            bw.write("border-right: 4px solid red;");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.write(".south {");
            bw.newLine();
            bw.write("border-bottom: 4px solid red;");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.write(".west {");
            bw.newLine();
            bw.write("border-left: 4px solid red;");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.write("</style>");
            bw.newLine();
            bw.write("<body>");
            bw.newLine();
            bw.write("\t<table border=\"1\" cellspacing=\"0\" style=\"margin=0;padding=0\">");
            bw.newLine();
            for (int y = 0; y < maze.getHeight(); y++) {
                bw.write("\t\t<tr>");
                bw.newLine();
                for (int x = 0; x < maze.getWidth(); x++) {
                    Cell cell = maze.getCell(x, y);
                    bw.write("\t\t\t<td class=\"cell ");
                    String classes = "";
                    if (cell.isNorthWall()) {
                        classes += "north ";
                    }
                    if (cell.isEastWall()) {
                        classes += "east ";
                    }
                    if (cell.isSouthWall()) {
                        classes += "south ";
                    }
                    if (cell.isWestWall()) {
                        classes += "west ";
                    }
                    bw.write(classes);
                    bw.write("\">");
                    bw.write("&nbsp;");
                    //bw.write(cell.getLocation().toString());
                    bw.write("</td>");
                    bw.newLine();
                }
                bw.write("\t\t</tr>");
                bw.newLine();
            }
            bw.write("\t</table>");
            bw.newLine();
            bw.write("</body>");
            bw.newLine();
            bw.write("</html>");
            bw.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
