package de.tkunkel.maze.tiles;

import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

/**
 * Provides access to tiles inside a tileset
 */
@Service
public class TilesetCutter {
    private int sizeTile;
    private BufferedImage image;

    public void cutTileSet(BufferedImage image, int sizeTile) {
        this.image = image;
        this.sizeTile = sizeTile;
    }

    public BufferedImage extractImage(int x, int y) {
        return image.getSubimage(x * sizeTile, y * sizeTile, sizeTile, sizeTile);
    }

    public int getSizeTile() {
        return sizeTile;
    }
}
