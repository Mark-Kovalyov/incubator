package mayton.bigdata;

import java.awt.*;
import java.util.Random;

public class ImagePolygons {

    static Random rand = new Random();

    public static Shape randomPolygon(int minimal, int maxi, int w, int h, int minRad, int rad) {
        int numPoints = minimal + rand.nextInt(maxi - minimal);
        int[] xPoints = new int[numPoints];
        int[] yPoints = new int[numPoints];

        int pcx = rand.nextInt(w);
        int pcy = rand.nextInt(h);

        int pradius = minRad + rand.nextInt(rad);

        for (int i = 0; i < numPoints; i++) {
            double angle = (2 * Math.PI * i) / numPoints;
            xPoints[i] = pcx + (int)(pradius * Math.cos(angle));
            yPoints[i] = pcy + (int)(pradius * Math.sin(angle));
        }
        return new Polygon(xPoints, yPoints, numPoints);
    }

}
