package de.tkunkel.maze.output;

import de.tkunkel.maze.tiles.TilesetCutter;
import de.tkunkel.maze.types.Cell;
import de.tkunkel.maze.types.Maze;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class RenderTiledImage {
    private final TilesetCutter tilesetCutter;

    public RenderTiledImage(TilesetCutter tilesetCutter) {
        this.tilesetCutter = tilesetCutter;
    }

    private BufferedImage copyImage(BufferedImage source) {
        return RenderImage.copyImage(source);
    }

    public BufferedImage renderSolution(BufferedImage mazeImageWithoutSolution, Maze maze, int sizeOfCell, int widthOfWall) {
        BufferedImage mazeImageWithSolution = copyImage(mazeImageWithoutSolution);
        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                Cell cell = maze.getCell(x, y);
                if (cell.isPartOfSolution()) {
                    paintCell(maze, mazeImageWithSolution, cell, sizeOfCell, true);
                }
            }
        }
        return mazeImageWithSolution;
    }

    public BufferedImage render(Maze maze, int sizeOfCell) {
        BufferedImage img = new BufferedImage(3 * maze.getWidth() * sizeOfCell, 3 * maze.getHeight() * sizeOfCell, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
//        for (int x = 0; x < maze.getWidth(); x++) {
//            for (int y = 0; y < maze.getHeight(); y++) {
                Cell cell = maze.getCell(x, y);
                paintCell(maze, img, cell, sizeOfCell, false);
            }
        }
        return img;
    }

    private void paintCell(Maze maze, BufferedImage img, Cell cell, int sizeOfCell, boolean renderSolutionInfo) {
        int x = cell.getLocation().getX();
        int y = cell.getLocation().getY();

        Graphics g = img.getGraphics();
        if (cell.isBlocker()) {
            BufferedImage tile = tilesetCutter.extractImage(3, 1);
            for (int dx = 0; dx < 2; dx++) {
                for (int dy = 0; dy < 4; dy++) {
                    int pixelX = (x * 3 + dx) * sizeOfCell;
                    int pixelY = (y * 3 + dy) * sizeOfCell;
                    g.drawImage(tile.getScaledInstance(sizeOfCell, sizeOfCell, Image.SCALE_SMOOTH), pixelX, pixelY, null);
                }
            }
        } else {
            BufferedImage tile = getCorrectTile(cell, 0, renderSolutionInfo);
            if (tile != null) {
                int dx = 0;
                int dy = 0;
                int pixelX = (x * 3 + dx) * sizeOfCell;
                int pixelY = (y * 3 + dy) * sizeOfCell;
                g.drawImage(tile.getScaledInstance(sizeOfCell, sizeOfCell, Image.SCALE_SMOOTH), pixelX, pixelY, null);
            }

            tile = getCorrectTile(cell, 1, renderSolutionInfo);
            if (tile != null) {
                int dx = 1;
                int dy = 0;
                int pixelX = (x * 3 + dx) * sizeOfCell;
                int pixelY = (y * 3 + dy) * sizeOfCell;
                g.drawImage(tile.getScaledInstance(sizeOfCell, sizeOfCell, Image.SCALE_SMOOTH), pixelX, pixelY, null);
            }

            tile = getCorrectTile(cell, 2, renderSolutionInfo);
            if (tile != null) {
                int dx = 2;
                int dy = 0;
                int pixelX = (x * 3 + dx) * sizeOfCell;
                int pixelY = (y * 3 + dy) * sizeOfCell;
                g.drawImage(tile.getScaledInstance(sizeOfCell, sizeOfCell, Image.SCALE_SMOOTH), pixelX, pixelY, null);
            }

            tile = getCorrectTile(cell, 3, renderSolutionInfo);
            if (tile != null) {
                int dx = 0;
                int dy = 1;
                int pixelX = (x * 3 + dx) * sizeOfCell;
                int pixelY = (y * 3 + dy) * sizeOfCell;
                g.drawImage(tile.getScaledInstance(sizeOfCell, sizeOfCell, Image.SCALE_SMOOTH), pixelX, pixelY, null);
            }

            tile = getCorrectTile(cell, 4, renderSolutionInfo);
            if (tile != null) {
                int dx = 1;
                int dy = 1;
                int pixelX = (x * 3 + dx) * sizeOfCell;
                int pixelY = (y * 3 + dy) * sizeOfCell;
                g.drawImage(tile.getScaledInstance(sizeOfCell, sizeOfCell, Image.SCALE_SMOOTH), pixelX, pixelY, null);
            }

            tile = getCorrectTile(cell, 5, renderSolutionInfo);
            if (tile != null) {
                int dx = 2;
                int dy = 1;
                int pixelX = (x * 3 + dx) * sizeOfCell;
                int pixelY = (y * 3 + dy) * sizeOfCell;
                g.drawImage(tile.getScaledInstance(sizeOfCell, sizeOfCell, Image.SCALE_SMOOTH), pixelX, pixelY, null);
            }
        }
    }

    private BufferedImage getCorrectTile(Cell cell, int desiredPosition, boolean renderSolutionInfo) {
        switch (desiredPosition) {
            case 0:
                return getCorrectTileNW(cell, desiredPosition, renderSolutionInfo);
            case 1:
                return getCorrectTileN(cell, desiredPosition, renderSolutionInfo);
            case 2:
                return getCorrectTileNE(cell, desiredPosition, renderSolutionInfo);
            case 3:
                return getCorrectTileW(cell, desiredPosition, renderSolutionInfo);
            case 4:
                return tilesetCutter.extractImage(10, 2);
            case 5:
                return getCorrectTileE(cell, desiredPosition, renderSolutionInfo);
        }

        return null;
    }

    private BufferedImage getCorrectTileN(Cell cell, int desiredPosition, boolean renderSolutionInfo) {
        if (cell.isNorthWall()) {
            return tilesetCutter.extractImage(7, 3);
        }
        if (!cell.isNorthWall()) {
            return tilesetCutter.extractImage(10, 2);
        }
        return null;
    }

    private BufferedImage getCorrectTileE(Cell cell, int desiredPosition, boolean renderSolutionInfo) {
        if (cell.isEastWall()) {
            return tilesetCutter.extractImage(6, 2);
        } else if (!cell.isEastWall()) {
            return tilesetCutter.extractImage(10, 2);
        } else {
            System.out.println("north=" + cell.isNorthWall() + " east=" + cell.isEastWall());
        }
        return null;
    }

    private BufferedImage getCorrectTileW(Cell cell, int desiredPosition, boolean renderSolutionInfo) {
        if (cell.isWestWall()) {
            return tilesetCutter.extractImage(5, 3);
        } else if (!cell.isWestWall()) {
            return tilesetCutter.extractImage(10, 2);
        } else {
            System.out.println("north=" + cell.isNorthWall() + " west=" + cell.isWestWall());
        }
        return null;
    }

    private BufferedImage getCorrectTileNE(Cell cell, int desiredPosition, boolean renderSolutionInfo) {
        if (cell.isNorthWall() && cell.isEastWall()) {
            return tilesetCutter.extractImage(8, 2);
        } else if (!cell.isNorthWall() && !cell.isEastWall()) {
            return tilesetCutter.extractImage(10, 2);
        } else if (!cell.isNorthWall() && cell.isEastWall()) {
            return tilesetCutter.extractImage(7, 2);
        } else if (cell.isNorthWall() && !cell.isEastWall()) {
            return tilesetCutter.extractImage(8, 3);
        } else {
            System.out.println("north=" + cell.isNorthWall() + " east=" + cell.isEastWall());
        }
        return null;
    }

    private BufferedImage getCorrectTileNW(Cell cell, int desiredPosition, boolean renderSolutionInfo) {
        if (cell.isNorthWall() && cell.isWestWall()) {
            return tilesetCutter.extractImage(5, 2);
        } else if (!cell.isNorthWall() && cell.isWestWall()) {
            return tilesetCutter.extractImage(9, 2);
        } else if (cell.isNorthWall() && !cell.isWestWall()) {
            return tilesetCutter.extractImage(8, 3);
        } else if (!cell.isNorthWall() && !cell.isWestWall()) {
            return tilesetCutter.extractImage(10, 2);
        } else {
            System.out.println("north=" + cell.isNorthWall() + " east=" + cell.isEastWall());
        }
        return null;
    }

}
