package mayton.bigdata;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageTransform {

    public static BufferedImage rotateImage(BufferedImage originalImage, double degrees) {
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));

        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        // Calculate new dimensions to fit the rotated image
        int newWidth = (int) Math.floor(originalWidth * cos + originalHeight * sin);
        int newHeight = (int) Math.floor(originalHeight * cos + originalWidth * sin);

        // Create new image with calculated dimensions
        BufferedImage rotatedImage = new BufferedImage(
                newWidth,
                newHeight,
                originalImage.getType()
        );

        Graphics2D g2d = rotatedImage.createGraphics();

        // Enable anti-aliasing for better quality
        g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Create transformation
        AffineTransform transform = new AffineTransform();
        transform.translate((newWidth - originalWidth) / 2.0, (newHeight - originalHeight) / 2.0);
        transform.rotate(radians, originalWidth / 2.0, originalHeight / 2.0);

        // Apply transformation and draw
        g2d.drawImage(originalImage, transform, null);
        g2d.dispose();

        return rotatedImage;
    }

    // Simple version specifically for 45 degrees
    public static BufferedImage rotate45Degrees(BufferedImage originalImage) {
        return rotateImage(originalImage, 45);
    }



    public static BufferedImage rotateClockwize(BufferedImage img, int degrees) {

        int w = img.getWidth();
        int h = img.getHeight();

        BufferedImage rotated = new BufferedImage(h, w, img.getType());
        Graphics2D g2 = rotated.createGraphics();

        AffineTransform at = new AffineTransform();
        at.translate(h, 0);      // move image to the correct position
        at.rotate(Math.toRadians(degrees)); // rotate by 90 deg

        g2.setTransform(at);
        g2.drawImage(img, 0, 0, null);
        g2.dispose();

        return rotated;
    }

    public static BufferedImage copyImage(BufferedImage source) {
        BufferedImage copy = new BufferedImage(
                source.getWidth(),
                source.getHeight(),
                source.getType()
        );
        Graphics2D g = copy.createGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return copy;
    }

    public static BufferedImage flipLeftRight(BufferedImage src) {
        AffineTransform tx = new AffineTransform();
        tx.scale(1, -1);                     // flip vertically
        tx.translate(0, -src.getHeight());   // move image back into view

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        return op.filter(src, null);
    }

    public static BufferedImage flipUpDown(BufferedImage src) {
        AffineTransform tx = new AffineTransform();
        tx.scale(-1, 1);
        tx.translate(-src.getWidth(), 0);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        return op.filter(src, null);
    }


}
