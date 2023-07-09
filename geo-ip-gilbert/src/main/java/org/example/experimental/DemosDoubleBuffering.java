package org.example.experimental;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class DemosDoubleBuffering extends JFrame {

    private static final int WIDTH  = 600;
    private static final int HEIGHT = 400;

    int xs = 3;
    int ys = xs;

    int x = 0;
    int y = 0;

    final int r = 80;

    final BufferedImage bi1;

    public static void main( final String[] args ) {

        DemosDoubleBuffering frame = new DemosDoubleBuffering();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing( WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setSize( WIDTH, HEIGHT );
        frame.pack();
        frame.setVisible( true );
    }

    public DemosDoubleBuffering() {
        super( "Trying to do double buffering" );
        final JLabel jl = new JLabel();
        bi1 = new BufferedImage( WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB );
        final Thread t = new Thread(() -> {
            while ( true ) {
                move();
                drawSquare( bi1 );
                jl.repaint();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        t.start();
        jl.setIcon( new ImageIcon( bi1 ) );
        getContentPane().add( jl );
    }

    private void drawSquare( final BufferedImage bi ) {
        final int[] buf = ((DataBufferInt) bi.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < buf.length; i++) {
            buf[i] = 0xFFFFFFFF;    // clearing all white
        }
        for (int xx = 0; xx < r; xx++) {
            for (int yy = 0; yy < r; yy++) {
                buf[WIDTH*(yy+y)+xx+x] = 0xFF000000;
            }
        }
    }

    private void move() {
        if ( !(x + xs >= 0 && x + xs + r < bi1.getWidth()) ) {
            xs = -xs;
        }
        if ( !(y + ys >= 0 && y + ys + r < bi1.getHeight()) ) {
            ys = -ys;
        }
        x += xs;
        y += ys;
    }

}