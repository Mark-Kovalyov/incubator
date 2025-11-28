package mayton.bigdata;

import com.squareup.gifencoder.*;
import mayton.image.Raster;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static mayton.bigdata.Utils.printf;

public class DemoGifenc {

    public static int[][] copyToJaggedArray(BufferedImage image) {
        int X = image.getWidth();
        int Y = image.getHeight();
        int res[][] = new int[X][Y];
        for (int y = 0; y < Y; y++) {
            for (int x = 0; x < X; x++) {
                res[x][y] = image.getRGB(x,y);
            }
        }
        return res;
    }

    public static void main(String[] args) throws Exception {

        int WIDTH  = 640;
        int HEIGHT = 200;

        LocalDateTime localDateTime = LocalDateTime.now();

        OutputStream outputStream = new FileOutputStream(
                "enot-" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm")) + ".gif");

        ImageOptions options = new ImageOptions();

        options.setDelay(20, TimeUnit.MILLISECONDS)
                .setDitherer(NearestColorDitherer.INSTANCE)
                .setColorQuantizer(UniformQuantizer.INSTANCE);

        GifEncoder encoder = new GifEncoder(outputStream, WIDTH, HEIGHT, 0);

        Random random = new Random();
        for(int i = 0; i < 50; i++) {
            BufferedImage image = ImageSynth.solidBar(WIDTH, HEIGHT, Raster.getPixel(128,128,128));
            Shape clippingShape = ImagePolygons.randomPolygon(3, 4, WIDTH, HEIGHT, HEIGHT / 4, HEIGHT);
            Graphics2D g2d = image.createGraphics();
            g2d.setClip(clippingShape);

            FontRenderContext frc = g2d.getFontRenderContext();
            Font font = new Font("Arial", Font.BOLD, 100);
            TextLayout layout = new TextLayout("Space-Enot", font, frc);
            layout.draw(g2d, 0, HEIGHT / 2);
            g2d.dispose();

            encoder.addImage(copyToJaggedArray(
                    ImageTransform.flipUpDown(
                        ImageTransform.rotateClockwize(image, 90))), options);

            printf("Frame # %d\n", i);
        }
        outputStream.close();
        printf("OK\n");
    }
}
