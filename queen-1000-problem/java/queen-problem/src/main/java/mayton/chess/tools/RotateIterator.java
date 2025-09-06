package mayton.chess.tools;

import mayton.chess.datastructures.Position;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * Iterator<String> sourceIterator = Arrays.asList("A", "B", "C").iterator();
 * <p>
 * Iterable<String> iterable = () -> sourceIterator;
 * Stream<String> targetStream = StreamSupport.stream(iterable.spliterator(), false);
 */
public class RotateIterator implements Iterator<Position> {

    private Iterator<Position> queensPositions;
    private int size;

    public RotateIterator(@Nonnull Iterator<Position> queensPositions, int size) {
        this.queensPositions = queensPositions;
        this.size = size;
    }

    @Override
    public boolean hasNext() {
        return queensPositions.hasNext();
    }

    //           | cos(90),  sin(90), 0 |   | 0,  1, 0 |
    //  R(x,y) = | -sin(90), cos(90), 0 | = | -1, 0, 0 |
    //           |       0,       0,  1 |   |  0, 0, 1 |
    //
    //           |  1 , 0 , 0  |
    //  T(x,y) = |  0 , 1 , 0  |
    //           |  tx, ty, 1  |
    //
    //
    //  (x1,y1,1) =
    @Override
    public Position next() {
        Position value = queensPositions.next();
        int x = value.x;
        int y = value.y;
        return new Position(size - 1 - y, x);
    }
}
