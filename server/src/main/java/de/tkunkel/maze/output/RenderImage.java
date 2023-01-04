package de.tkunkel.maze.output;

import de.tkunkel.maze.types.Cell;
import de.tkunkel.maze.types.Maze;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class RenderImage {

    public BufferedImage render(Maze maze, int sizeOfCell, int widthOfWall, boolean renderSolutionInfo) {
        BufferedImage img = new BufferedImage(maze.getWidth() * sizeOfCell, maze.getHeight() * sizeOfCell, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                Cell cell = maze.getCell(x, y);
                paintCell(maze, img, cell, sizeOfCell, widthOfWall, renderSolutionInfo);
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
            g.fillRect(x, y, x + sizeOfCell, y + sizeOfCell);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, x + sizeOfCell, y + sizeOfCell);
            if (cell.getLocation().equals(maze.getStart())) {
                g.setColor(Color.RED);
                g.fillRect(x, y, x + sizeOfCell, y + sizeOfCell);
            } else if (cell.getLocation().equals(maze.getFinish())) {
                g.setColor(Color.GREEN);
                g.fillRect(x, y, x + sizeOfCell, y + sizeOfCell);
            } else if (cell.isPartOfSolution() && renderSolutionInfo) {
                g.setColor(new Color(0x4D99DA));
                g.fillRect(x, y, x + sizeOfCell, y + sizeOfCell);
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
}
