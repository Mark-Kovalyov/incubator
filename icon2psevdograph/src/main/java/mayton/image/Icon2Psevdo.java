package mayton.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Icon2Psevdo {

    public static final double GK=0.587;
    public static final double BK=0.114;
    public static final double RK=0.299;

    public static void main(String[] args) throws IOException {
        BufferedImage i = ImageIO.read(new FileInputStream(args[0]));
        PrintWriter p = new PrintWriter(new FileWriter(args[1], StandardCharsets.UTF_8));
        boolean inverse = false;
        if (args.length == 3) {
            inverse = Boolean.getBoolean(args[2]);
        }

        //                             y    |   y + 1
        //                      ---------------------
        char[] c = {' ',        //     0        0
                   '\u2584',    // ▄   0        1
                   '\u2580',    // ▀   1        0
                   '\u2588' };  // █   1        1
        for (int y = 0; y < i.getWidth(); y+=2) {
            for (int x = 0; x < i.getWidth(); x++) {
                int pix1 = decodeY(i.getRGB(x,y))     > 0.5 ? 0x1 : 0x0;
                int pix2 = decodeY(i.getRGB(x,y + 1)) > 0.5 ? 0x1 : 0x0;
                int index = (pix1 << 1) | pix2;
                p.print(c[inverse ? 3 - index : index]);
            }
            p.println();
        }
        p.close();
    }

    private static double decodeY(int color) {
        return  (GK * ((color & 0x00FF00) >> 8) + BK * (color & 0x0000FF) + RK * ((color & 0xFF0000) >> 16)) / 255.0;
    }
}
