package de.tkunkel.maze.generator;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class TextImageGenerator {
    public BufferedImage generateImage(String text, int fontSize) {
        int border = 2;
//        int border = fontSize / 4;
        int width = 10;
        int height = 10;
        BufferedImage tempImageForMeasuring = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Font font = new Font("TimesRoman", Font.PLAIN, fontSize);

        FontMetrics fontMetrics = tempImageForMeasuring.getGraphics().getFontMetrics(font);
        width = fontMetrics.stringWidth(text) + 2 * border;
        height = fontMetrics.getHeight() + 2 * border;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = img.getGraphics();
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, img.getWidth(), img.getHeight());

        int x = border;
        int y = border / 2 + fontMetrics.getHeight();
        graphics.setColor(Color.BLACK);
        graphics.drawString(text, x, y);

        addStartAndFinish(img);

        return img;
    }

    private void addStartAndFinish(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        Graphics graphics = img.getGraphics();
        graphics.setColor(Color.GREEN);
        graphics.fillRect(5, 5, 1, 1);
        graphics.setColor(Color.RED);
        graphics.fillRect(width - 5, height - 5, 1, 1);
    }

}
