package mayton.chess.datastructures;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class ChessDesk {

    protected int size;
    private boolean[] cells;

    public ChessDesk(int size) {
        this.size = size;
        cells = new boolean[size * size];
    }

    public int getSize() {
        return size;
    }

    // TODO: Simplify with Guava checks
    public void setValue(int x,int y){
        if (x < 0 || x >= size){
            throw new IllegalArgumentException("Out of range. Unable to set position with x = " + x);
        }
        if (y < 0 || y >= size){
            throw new IllegalArgumentException("Out of range. Unable to set position with y = " + y);
        }
        cells[x + y * size ] = true;
    }

    public boolean getValue(int x, int y) {
        if (x < 0 || x >= size){
            throw new IllegalArgumentException("Out of range. Unable to get position with x = " + x);
        }
        if (y < 0 || y >= size){
            throw new IllegalArgumentException("Out of range. Unable to get position with y = " + y);
        }
        return cells[x + y * size ];
    }


}
