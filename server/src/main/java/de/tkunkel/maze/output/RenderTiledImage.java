package de.tkunkel.maze.output;

import de.tkunkel.maze.tiles.TilesetCutter;
import de.tkunkel.maze.types.Cell;
import de.tkunkel.maze.types.Maze;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

@Service
public class RenderTiledImage {
    private final TilesetCutter tilesetCutter;

    public RenderTiledImage(TilesetCutter tilesetCutter) {
        this.tilesetCutter = tilesetCutter;
    }

    public BufferedImage render(Maze maze, int sizeOfCell) {
        BufferedImage img = new BufferedImage(3 * maze.getWidth() * sizeOfCell, 3 * maze.getHeight() * sizeOfCell, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
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
            for (Position position : Position.values()) {
                BufferedImage tile = getCorrectTile(cell, position, renderSolutionInfo);
                if (tile != null) {
                    int dx = position.x;
                    int dy = position.y;
                    int pixelX = (x * 3 + dx) * sizeOfCell;
                    int pixelY = (y * 3 + dy) * sizeOfCell;
                    g.drawImage(tile.getScaledInstance(sizeOfCell, sizeOfCell, Image.SCALE_SMOOTH), pixelX, pixelY, null);
                }
            }
        }
        if (Objects.equals(cell.getLocation(), maze.getStart())) {
            BufferedImage tile = tilesetCutter.extractImage(8, 14);
            int dx = 1;
            int dy = 1;
            int pixelX = (x * 3 + dx) * sizeOfCell;
            int pixelY = (y * 3 + dy) * sizeOfCell;
            g.drawImage(tile.getScaledInstance(sizeOfCell, sizeOfCell, Image.SCALE_SMOOTH), pixelX, pixelY, null);
        } else if (Objects.equals(cell.getLocation(), maze.getFinish())) {
            BufferedImage tile = tilesetCutter.extractImage(8, 16);
            int dx = 1;
            int dy = 1;
            int pixelX = (x * 3 + dx) * sizeOfCell;
            int pixelY = (y * 3 + dy) * sizeOfCell;
            g.drawImage(tile.getScaledInstance(sizeOfCell, sizeOfCell, Image.SCALE_SMOOTH), pixelX, pixelY, null);
        }
    }

    private BufferedImage getCorrectTile(Cell cell, Position desiredPosition, boolean renderSolutionInfo) {
        int[][][] tileToUse = null;
        if (!cell.isNorthWall() && !cell.isEastWall() && !cell.isSouthWall() && !cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_NO_WALLS.data;
        } else if (cell.isNorthWall() && !cell.isEastWall() && !cell.isSouthWall() && cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_WALLS_N_W.data;
        } else if (cell.isNorthWall() && !cell.isEastWall() && !cell.isSouthWall() && !cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_WALLS_N.data;
        } else if (!cell.isNorthWall() && !cell.isEastWall() && !cell.isSouthWall() && cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_WALLS_W.data;
        } else if (cell.isNorthWall() && cell.isEastWall() && !cell.isSouthWall() && !cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_WALLS_N_E.data;
        } else if (!cell.isNorthWall() && cell.isEastWall() && !cell.isSouthWall() && !cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_WALLS_E.data;
        } else if (!cell.isNorthWall() && !cell.isEastWall() && cell.isSouthWall() && cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_WALLS_S_W.data;
        } else if (!cell.isNorthWall() && !cell.isEastWall() && cell.isSouthWall() && !cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_WALLS_S.data;
        } else if (!cell.isNorthWall() && cell.isEastWall() && cell.isSouthWall() && !cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_WALLS_E_S.data;
        } else if (!cell.isNorthWall() && cell.isEastWall() && !cell.isSouthWall() && cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_WALLS_E_W.data;
        } else if (cell.isNorthWall() && cell.isEastWall() && cell.isSouthWall() && !cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_WALLS_N_E_S.data;
        } else if (cell.isNorthWall() && !cell.isEastWall() && cell.isSouthWall() && cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_WALLS_N_S_W.data;
        } else if (cell.isNorthWall() && cell.isEastWall() && cell.isSouthWall() && cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_WALLS_N_E_S_W.data;
        } else if (cell.isNorthWall() && cell.isEastWall() && !cell.isSouthWall() && cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_WALLS_N_E_W.data;
        } else if (cell.isNorthWall() && !cell.isEastWall() && cell.isSouthWall() && !cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_WALLS_N_S.data;
        } else if (!cell.isNorthWall() && cell.isEastWall() && cell.isSouthWall() && cell.isWestWall()) {
            tileToUse = TileSetArrangements.TILE_WITH_WALLS_E_S_W.data;
        } else {
            throw new RuntimeException("Unknown wall-situation " + cell + " for '" + desiredPosition + "'");
        }
        return tilesetCutter.extractImage(tileToUse[desiredPosition.x][desiredPosition.y][0], tileToUse[desiredPosition.x][desiredPosition.y][1]);
    }

}
