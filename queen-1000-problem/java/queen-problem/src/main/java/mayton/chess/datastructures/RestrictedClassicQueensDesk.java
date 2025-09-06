package mayton.chess.datastructures;

public class RestrictedClassicQueensDesk extends RestrictedQueensDesk {

    private boolean[] horizontal;
    private boolean[] vertical;

    // [ -SIZE .. SIZE ]
    private boolean[] leftDiagonal;
    // [ 0 .. SIZE * 2]
    private boolean[] rightDiagonal;

    public boolean isHorizontalUnderFire(int y) {
        return horizontal[y];
    }

    public boolean isLeftTopDiagonalUnderFire(int offset) {
        return leftDiagonal[offset];
    }

    public boolean isVerticalUnderFire(int y) {
        return vertical[y];
    }

    public boolean isRightTopDiagonalUnderFire(int offset) {
        return rightDiagonal[offset];
    }

    public RestrictedClassicQueensDesk(int size) {
        super(size);
        horizontal = new boolean[size];
        vertical = new boolean[size];
        leftDiagonal = new boolean[size * 2 + 1];
        rightDiagonal = new boolean[size * 2 + 1];
    }

    public boolean tryToSetQueen(int x, int y) {
        if (isUnderFire(x, y)) {
            return false;
        } else {
            setValue(x, y);
            horizontal[y] = true;
            vertical[x] = true;
            rightDiagonal[calcRightDiagonal(x,y)] = true;
            leftDiagonal[calcLeftDiagonal(x,y)] = true;
            return true;
        }
    }

    // TODO: Simplify with Guava checks
    private int calcLeftDiagonal(int x, int y) {
        if (x < 0 || x >= size){
            throw new IllegalArgumentException("Out of range. Unable calculate diagonal position with x = " + x);
        }
        if (y < 0 || y >= size){
            throw new IllegalArgumentException("Out of range. Unable calculate diagonal position with y = " + y);
        }
        return size - (x - y);
    }

    private int calcRightDiagonal(int x, int y) {
        if (x < 0 || x >= size){
            throw new IllegalArgumentException("Out of range. Unable calculate diagonal position with x = " + x);
        }
        if (y < 0 || y >= size){
            throw new IllegalArgumentException("Out of range. Unable calculate diagonal position with y = " + y);
        }
        return x + y;
    }


    public boolean isUnderFire(int x, int y) {
        if (horizontal[y]) {
            return true;
        } else if (vertical[x]) {
            return true;
        } else if (rightDiagonal[calcRightDiagonal(x,y)]) {
            return true;
        } else if (leftDiagonal[calcLeftDiagonal(x,y)]) {
            return true;
        }
        return false;
    }

    @Override
    public boolean getValue(int x, int y) {
        return super.getValue(x, y);
    }


}
