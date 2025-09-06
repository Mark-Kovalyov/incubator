package mayton.chess.tools;

import mayton.chess.datastructures.Position;

import java.util.Iterator;

public class SwapHorizontalIterator implements Iterator<Position> {

    private Iterator<Position> queens;
    private int y;
    private int size;

    public SwapHorizontalIterator(Iterator<Position> queens, int size) {
        this.queens = queens;
        this.size = size;
        this.y = 0;
    }

    @Override
    public boolean hasNext() {
        return queens.hasNext();
    }

    @Override
    public Position next() {
        Position pos = queens.next();
        int x = size - pos.x - 1;
        return new Position(x, y++);
    }
}
