package mayton.bigdata;

import mayton.image.Raster;

import java.awt.*;
import java.awt.image.BufferedImage;


public class ImageSynth {

    public static BufferedImage solidBar(int w, int h, int rgbcolor) {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(new Color(
                Raster.getRPixel(rgbcolor),
                Raster.getGPixel(rgbcolor),
                Raster.getBPixel(rgbcolor))
        );
        g.fillRect(0, 0, w, h);

        return image;
    }

}
