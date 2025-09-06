package mayton.chess;

import mayton.chess.datastructures.Position;
import mayton.chess.tools.SwapHorizontalIterator;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SwapHorizontalIteratorTest {

    @Test
    public void testSwap(){
        // Given
        //
        // 0 1 2 3 4
        // Q . . . .
        // . . Q . .
        // . . . . Q
        // . Q . . .
        // . . . Q .
        Iterator<Position> positionIterator = new SwapHorizontalIterator(new PositionMockIterator("0, 2, 4, 1, 3"), 5);
        // 0 1 2 3 4
        // . . . . Q
        // . . Q . .
        // Q . . . .
        // . . . Q .
        // . Q . . .
        assertEquals(new Position(4,0), positionIterator.next());
        assertEquals(new Position(2,1), positionIterator.next());
        assertEquals(new Position(0,2), positionIterator.next());
        assertEquals(new Position(3,3), positionIterator.next());
        assertEquals(new Position(1,4), positionIterator.next());
        assertFalse(positionIterator.hasNext());
    }

}
