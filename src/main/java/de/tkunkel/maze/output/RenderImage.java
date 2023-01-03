package de.tkunkel.maze.output;

import de.tkunkel.maze.types.Cell;
import de.tkunkel.maze.types.Maze;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class RenderImage {

    public BufferedImage render(Maze maze, int sizeOfCell, int widthOfWall) {
        BufferedImage img = new BufferedImage(maze.getWidth() * sizeOfCell, maze.getHeight() * sizeOfCell, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                Cell cell = maze.getCell(x, y);
                paintCell(img, cell, sizeOfCell, widthOfWall);
            }
        }
        return img;
    }

    private void paintCell(BufferedImage img, Cell cell, int sizeOfCell, int widthOfWall) {
        int x = cell.getLocation().getX() * sizeOfCell;
        int y = cell.getLocation().getY() * sizeOfCell;

        Graphics g = img.getGraphics();
        g.setColor(Color.WHITE);
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
