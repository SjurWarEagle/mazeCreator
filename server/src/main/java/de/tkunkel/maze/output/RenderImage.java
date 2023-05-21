package de.tkunkel.maze.output;

import de.tkunkel.maze.types.Cell;
import de.tkunkel.maze.types.Maze;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class RenderImage {
    public static BufferedImage copyImage(BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    public BufferedImage renderSolution(BufferedImage mazeImageWithoutSolution, Maze maze, int sizeOfCell, int widthOfWall) {
        BufferedImage mazeImageWithSolution = copyImage(mazeImageWithoutSolution);
        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                Cell cell = maze.getCell(x, y);
                if (cell.isPartOfSolution()) {
                    paintCell(maze, mazeImageWithSolution, cell, sizeOfCell, widthOfWall, true);
                }
            }
        }
        return mazeImageWithSolution;
    }

    public BufferedImage render(Maze maze, int sizeOfCell, int widthOfWall) {
        BufferedImage img = new BufferedImage(maze.getWidth() * sizeOfCell + widthOfWall, maze.getHeight() * sizeOfCell + widthOfWall, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                Cell cell = maze.getCell(x, y);
                paintCell(maze, img, cell, sizeOfCell, widthOfWall, false);
            }
        }
        return img;
    }

    private void paintCell(Maze maze, BufferedImage img, Cell cell, int sizeOfCell, int widthOfWall, boolean renderSolutionInfo) {
        int x = cell.getLocation().getX() * sizeOfCell;
        int y = cell.getLocation().getY() * sizeOfCell;

        Graphics g = img.getGraphics();
        if (cell.isBlocker()) {
            g.setColor(Color.DARK_GRAY);
            fillRect(g, x, y, x + sizeOfCell, y + sizeOfCell);
        } else {
            g.setColor(Color.WHITE);
            fillRect(g, x, y, x + sizeOfCell, y + sizeOfCell);
            if (cell.getLocation().equals(maze.getStart())) {
                g.setColor(Color.RED);
                fillRect(g, x, y, x + sizeOfCell, y + sizeOfCell);
            } else if (cell.getLocation().equals(maze.getFinish())) {
                g.setColor(Color.GREEN);
                fillRect(g, x, y, x + sizeOfCell, y + sizeOfCell);
            } else if (cell.isPartOfSolution() && renderSolutionInfo) {
                g.setColor(new Color(0x4D99DA));
                fillRect(g, x, y, x + sizeOfCell, y + sizeOfCell);
            }

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
    }

    private void fillRect(Graphics g, int x1, int y1, int x2, int y2) {
        for (int i = 0; i < (x2 - x1); i++) {
            g.drawLine(x1 + i, y1, x1 + i, y2);
        }

    }
}
