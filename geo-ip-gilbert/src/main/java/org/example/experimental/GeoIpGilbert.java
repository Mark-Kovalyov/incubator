package org.example.experimental;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.concurrent.atomic.AtomicBoolean;

public class GeoIpGilbert extends JFrame implements Runnable {

    public AtomicBoolean isStopped = new AtomicBoolean(false);

    public GeoIpGilbert() {
        this.setPreferredSize(new Dimension(800, 600));
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // define the position
        int locX = 200;
        int locY = 200;
        // draw a line (there is no drawPoint..)
        g.drawLine(locX, locY, locX, locY);
    }

    public static void main(String[] args) {
        GeoIpGilbert test = new GeoIpGilbert();
    }

    @Override
    public void run() {


        // Create our component
        Window w = new Window(null);

        // Show our window
        w.setVisible(true);

        // Create a general double-buffering strategy
        w.createBufferStrategy(2);
        BufferStrategy strategy = w.getBufferStrategy();

        // Main loop
        while (!isStopped.get()) {

            do {

                do {
                    // Get a new graphics context every time through the loop
                    // to make sure the strategy is validated
                    Graphics graphics = strategy.getDrawGraphics();

                    // Render to graphics
                    // ...

                    // Dispose the graphics
                    graphics.dispose();

                    // Repeat the rendering if the drawing buffer contents
                    // were restored
                } while (strategy.contentsRestored());

                // Display the buffer
                strategy.show();

                // Repeat the rendering if the drawing buffer was lost
            } while (strategy.contentsLost());
        }

        // Dispose the window
        w.setVisible(false);
        w.dispose();
    }
}