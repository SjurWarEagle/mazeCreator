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

    private final Logger LOG = LoggerFactory.getLogger(RenderHtml.class);

    public void renderToFile(Maze maze) {
        Path output = Paths.get(configuration.getPathOutput(), "index.html");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(output.toFile()))) {
            bw.write(renderToString(maze));
            bw.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public String renderToString(Maze maze) {
        try (StringWriter sw = new StringWriter();
             BufferedWriter bw = new BufferedWriter(sw)
        ) {
            bw.write("<html>");
            bw.newLine();
            bw.write("<title>Maze</title>");
            bw.newLine();
            bw.write("<style>");
            bw.newLine();
            bw.write(".cell {");
            bw.newLine();
            bw.write("width: 25px;");
            bw.newLine();
            bw.write("height: 25px;");
            bw.newLine();
            bw.write("font-size: 25px;");
            bw.newLine();
            bw.write("border: 4px solid white;");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.write("table {");
            bw.newLine();
            bw.write("margin: 10%");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.write(".solution {");
            bw.newLine();
            bw.write("background-color: lightblue;");
            bw.newLine();
            bw.write("}");
           bw.newLine();
            bw.write(".finish {");
            bw.newLine();
            bw.write("background-color: green;");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.write(".start {");
            bw.newLine();
            bw.write("background-color: red;");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.write(".finish {");
            bw.newLine();
            bw.write("background-color: green;");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.write(".blocker {");
            bw.newLine();
            bw.write("background-color: black;");
            bw.newLine();
            bw.write("border: unset;");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.write(".north {");
            bw.newLine();
            bw.write("border-top: 4px solid #FF0000;");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.write(".east {");
            bw.newLine();
            bw.write("border-right: 4px solid #F00000;");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.write(".south {");
            bw.newLine();
            bw.write("border-bottom: 4px solid #EE0000;");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.write(".west {");
            bw.newLine();
            bw.write("border-left: 4px solid #F00000;");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.write("</style>");
            bw.newLine();
            bw.write("<body>");
            bw.newLine();
            bw.write("\t<table border=\"0\" cellspacing=\"0\" style=\"margin:0; padding:0\">");
            bw.newLine();
            for (int y = 0; y < maze.getHeight(); y++) {
                bw.write("\t\t<tr>");
                bw.newLine();
                for (int x = 0; x < maze.getWidth(); x++) {
                    Cell cell = maze.getCell(x, y);
                    bw.write("\t\t\t<td class=\"cell ");
                    String classes = "";
                    if (cell.isBlocker()) {
                        classes += "blocker ";
                    } else {
                        if (cell.getLocation().getX() == maze.getStart().getX()
                                && cell.getLocation().getY() == maze.getStart().getY()) {
                            classes += "start ";
                        }
                        if (cell.getLocation().getX() == maze.getFinish().getX()
                                && cell.getLocation().getY() == maze.getFinish().getY()) {
                            classes += "finish ";
                        }
                        if (cell.isPartOfSolution()) {
                            classes += "solution ";
                        }
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
                    }
                    bw.write(classes);
                    bw.write("\">");
                    bw.write("&nbsp;");
                    bw.write(""+cell.getDistanceFromStart());
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
            return sw.toString();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
}
