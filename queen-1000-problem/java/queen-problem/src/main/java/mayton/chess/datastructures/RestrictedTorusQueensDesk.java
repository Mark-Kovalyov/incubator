package mayton.chess.datastructures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class RestrictedTorusQueensDesk extends RestrictedQueensDesk {

    static Logger logger = LoggerFactory.getLogger(RestrictedTorusQueensDesk.class);

    private boolean[] cycledHorizontal;
    private boolean[] cycledVertical;
    private boolean[] cycledLeftDiagonal;
    private boolean[] cycledRightDiagonal;

    public RestrictedTorusQueensDesk(int size) {
        super(size);
        cycledHorizontal    = new boolean[size];
        cycledVertical      = new boolean[size];
        cycledLeftDiagonal  = new boolean[size];
        cycledRightDiagonal = new boolean[size];
    }

    @Override
    public boolean tryToSetQueen(int x, int y) {
        if (isUnderFire(x, y)) {
            return false;
        } else {
            setValue(x, y);
            cycledHorizontal[y] = true;
            cycledVertical[x] = true;
            cycledRightDiagonal[calcCycledRightDiagonal(x,y,size)] = true;
            cycledLeftDiagonal[calcCycledLeftDiagonal(x,y,size)] = true;
            return true;
        }
    }

    @Override
    public boolean isUnderFire(int x, int y) {
        if (cycledHorizontal[y]) {
            return true;
        } else if (cycledVertical[x]) {
            return true;
        } else if (cycledLeftDiagonal[calcCycledLeftDiagonal(x,y,size)]) {
            return true;
        } else if (cycledRightDiagonal[calcCycledRightDiagonal(x,y,size)]) {
            return true;
        }
        return false;
    }

    public static int calcCycledLeftDiagonal(int x, int y, int size) {
        if (x < 0 || x >= size){
            throw new IllegalArgumentException("Out of range. Unable calculate diagonal position with x = " + x);
        }
        if (y < 0 || y >= size){
            throw new IllegalArgumentException("Out of range. Unable calculate diagonal position with y = " + y);
        }
        return (size - (x - y)) % size;
    }

    /**
     * Returns (x,0) intersect between X axis and right diagonal line with(x,y) point
     *
     * @param x
     * @param y
     * @param size
     * @return
     */

    public static int calcRightDiagonal(int x, int y, int size) {
        int offset = x + y;
        return offset;
    }

    public static int calcCycledRightDiagonal(int x, int y, int size) {
        if (x < 0 || x >= size){
            throw new IllegalArgumentException("Out of range. Unable calculate diagonal position with x = " + x);
        }
        if (y < 0 || y >= size){
            throw new IllegalArgumentException("Out of range. Unable calculate diagonal position with y = " + y);
        }

        int offset = calcRightDiagonal(x,y,size) % size;

        return offset;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("===================\n");

        for(int k = 0;k < this.cycledRightDiagonal.length;k++) {
            sb.append(cycledRightDiagonal[k] ? "1" : "0");
            sb.append(" ");
        }

        sb.append("\n");

        sb.append("-------------------\n");

        for(int k = 0;k < this.cycledLeftDiagonal.length;k++) {
            sb.append(cycledLeftDiagonal[k] ? "1" : "0");
            sb.append(" ");
        }

        sb.append("\n");

        return sb.toString() + super.toString();
    }
}
