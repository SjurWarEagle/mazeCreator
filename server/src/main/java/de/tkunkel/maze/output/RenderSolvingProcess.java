package de.tkunkel.maze.output;

import de.tkunkel.maze.types.Cell;
import de.tkunkel.maze.types.Maze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class RenderSolvingProcess {
    private final Logger LOG = LoggerFactory.getLogger(RenderSolvingProcess.class);

    public static BufferedImage copyImage(BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    public BufferedImage renderSolutionProgress(BufferedImage mazeImageWithoutSolution, Maze maze,
                                                int sizeOfCell, int distanceFromStartToDraw,
                                                int maxDistanceFromStart) {
        BufferedImage mazeImageWithSolution = copyImage(mazeImageWithoutSolution);
        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                Cell cell = maze.getCell(x, y);
                if (cell.getDistanceFromStart() > distanceFromStartToDraw) {
                    continue;
                }
                Color distanceColor = determinProgressColor(cell.getDistanceFromStart(), maxDistanceFromStart);
                paintCellWithDistanceInfo(maze, mazeImageWithSolution, cell, sizeOfCell, distanceColor);
            }
        }
        return mazeImageWithSolution;
    }

    private Color determinProgressColor(int distanceFromStartToDraw, int maxDistanceFromStart) {
        Color color;
        int marker = 100+(150 * distanceFromStartToDraw) / maxDistanceFromStart;
        color = new Color(marker, 0, 0);
//        LOG.debug(color.toString());
        return color;
    }

    private void paintCellWithDistanceInfo(Maze maze, BufferedImage img, Cell cell, int sizeOfCell, Color distanceColor) {
        int x = cell.getLocation().getX() * sizeOfCell;
        int y = cell.getLocation().getY() * sizeOfCell;

        Graphics g = img.getGraphics();
        if (cell.isBlocker() || cell.getLocation().equals(maze.getStart()) || cell.getLocation().equals(maze.getFinish())) {
            return;
        }

        g.setColor(distanceColor);
        fillRect(g, x, y, x + sizeOfCell, y + sizeOfCell);

        renderWalls(cell, sizeOfCell, g, x, y);

    }

    private static void renderWalls(Cell cell, int sizeOfCell, Graphics g, int x, int y) {
        //walls
        g.setColor(Color.BLACK);
        if (cell.isNorthWall()) {
            g.drawLine(x, y, x + sizeOfCell, y);
        }
        if (cell.isEastWall()) {
            g.drawLine(x + sizeOfCell, y, x + sizeOfCell, y + sizeOfCell);
        }
        if (cell.isSouthWall()) {
            g.drawLine(x, y + sizeOfCell, x + sizeOfCell, y + sizeOfCell);
        }
        if (cell.isWestWall()) {
            g.drawLine(x, y, x, y + sizeOfCell);
        }
    }

    private void fillRect(Graphics g, int x1, int y1, int x2, int y2) {
        for (int i = 0; i < (x2 - x1); i++) {
            g.drawLine(x1 + i, y1, x1 + i, y2);
        }
    }
}
