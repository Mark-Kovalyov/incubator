package org.example;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DoubleBufferingDemo extends JPanel implements Runnable {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private Thread thread;
    private boolean running;
    private BufferedImage buffer;
    private Graphics2D bufferGraphics;

    public DoubleBufferingDemo() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        bufferGraphics = buffer.createGraphics();
        bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            running = true;
            thread.start();
        }
    }

    public void stop() {
        if (thread != null) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread = null;
        }
    }

    @Override
    public void run() {
        Random random = new Random();
        while (running) {
            update();

            render(new ColoredRect(
                    new Rect(
                            random.nextInt(WIDTH),
                            random.nextInt(HEIGHT),
                            random.nextInt(50),
                            random.nextInt(50)),
                    Color.getHSBColor(random.nextFloat(), 1.0f, 1.0f)));

            paintScreen();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        // update game state here
    }

    static class Rect {
        public int x;
        public int y;
        public int w;
        public int h;
        public Rect(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
    }

    static class ColoredRect {
        public Rect rect;
        public Color color;
        public ColoredRect(Rect rect, Color color) {
            this.rect = rect;
            this.color = color;
        }
    }

    private void render(ColoredRect coloredRect) {
        //bufferGraphics.setColor(Color.BLACK);
        //bufferGraphics.fillRect(0, 0, WIDTH, HEIGHT);

        Rect rect = coloredRect.rect;
        // render game objects here
        bufferGraphics.setColor(coloredRect.color);
        bufferGraphics.fillRect(rect.x, rect.y, rect.w, rect.h);
    }

    private void paintScreen() {
        Graphics g = getGraphics();
        if (g != null) {
            g.drawImage(buffer, 0, 0, null);
            g.dispose();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Double Buffering Demo");
        DoubleBufferingDemo panel = new DoubleBufferingDemo();
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        panel.start();
    }

}
