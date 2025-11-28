package mayton.bigdata;

import java.awt.image.BufferedImage;
import java.io.PrintStream;

public class Utils {

    public static PrintStream printf(String format, Object ...args) {
        return System.out.printf(format,args);
    }

    public static String formatImageSize(BufferedImage img) {
        return String.format("Image size : %d x %d", img.getWidth(), img.getHeight());
    }

}
