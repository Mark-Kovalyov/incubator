package mayton.chess;

import mayton.chess.datastructures.Position;
import mayton.chess.tools.RotateIterator;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RotateIteratorTest {

    @Test
    public void testMock(){
        Iterator<Position> iterator = new PositionMockIterator("0, 2, 4, 1, 3");
        assertEquals(new Position(0,0), iterator.next());
        assertEquals(new Position(2,1), iterator.next());
        assertEquals(new Position(4,2), iterator.next());
        assertEquals(new Position(1,3), iterator.next());
        assertEquals(new Position(3,4), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void test4Rotate5x5(){

        Iterator<Position> mockIterator = new PositionMockIterator("0, 2, 4, 1, 3");
        Iterator<Position> r1 = new RotateIterator(mockIterator, 5);
        Iterator<Position> r2 = new RotateIterator(r1, 5);
        Iterator<Position> r3 = new RotateIterator(r2, 5);
        Iterator<Position> r4 = new RotateIterator(r3, 5);

        Iterator<Position> mockIterator2 = new PositionMockIterator("0, 2, 4, 1, 3");

        assertEquals(mockIterator2.next(), r4.next());
        assertEquals(mockIterator2.next(), r4.next());
        assertEquals(mockIterator2.next(), r4.next());
        assertEquals(mockIterator2.next(), r4.next());
        assertEquals(mockIterator2.next(), r4.next());

        assertFalse(r4.hasNext());
    }


    @Test
    public void test4Rotate6x6(){

        Iterator<Position> mockIterator = new PositionMockIterator("1, 3, 5, 0, 2, 4");
        Iterator<Position> r1 = new RotateIterator(mockIterator, 6);
        Iterator<Position> r2 = new RotateIterator(r1, 6);
        Iterator<Position> r3 = new RotateIterator(r2, 6);
        Iterator<Position> r4 = new RotateIterator(r3, 6);

        Iterator<Position> mockIterator2 = new PositionMockIterator("1, 3, 5, 0, 2, 4");

        assertEquals(mockIterator2.next(), r4.next());
        assertEquals(mockIterator2.next(), r4.next());
        assertEquals(mockIterator2.next(), r4.next());
        assertEquals(mockIterator2.next(), r4.next());
        assertEquals(mockIterator2.next(), r4.next());
        assertEquals(mockIterator2.next(), r4.next());

        assertFalse(r4.hasNext());
    }

    @Test
    public void testRotate5x5(){
        // Given:
        //        [0, 2, 4, 1, 3]
        //
        //          0   1   2   3   4
        //        {'Q','*','*','*','*',},
        //        {'*','*','Q','*','*',},
        //        {'*','*','*','*','Q',},
        //        {'*','Q','*','*','*',},
        //        {'*','*','*','Q','*',},
        //
        Iterator<Position> rotateIterator = new RotateIterator(new PositionMockIterator("0, 2, 4, 1, 3"), 5);

        // Actual:
        //           0   1   2   3   4
        //     0   {'*','*','*','*','Q',},
        //     1   {'*','Q','*','*','*',},
        //     2   {'*','*','*','Q','*',},
        //     3   {'Q','*','*','*','*',},
        //     4   {'*','*','Q','*','*',},

        assertEquals(new Position(4,0), rotateIterator.next());
        assertEquals(new Position(3,2), rotateIterator.next());
        assertEquals(new Position(2,4), rotateIterator.next());
        assertEquals(new Position(1,1), rotateIterator.next());
        assertEquals(new Position(0,3), rotateIterator.next());
        assertFalse(rotateIterator.hasNext());

    }

    @Test
    public void testRotate6x6(){
        /*
                Given:

                [1, 3, 5, 0, 2, 4]

                {'*','Q','*','*','*','*',},
                {'*','*','*','Q','*','*',},
                {'*','*','*','*','*','Q',},
                {'Q','*','*','*','*','*',},
                {'*','*','Q','*','*','*',},
                {'*','*','*','*','Q','*',},
        */
        Iterator<Position> rotateIterator = new RotateIterator(new PositionMockIterator("1, 3, 5, 0, 2, 4"), 6);
        /*
        //           0   1   2   3   4   5
        //     0   {'*','*','Q','*','*','*',
        //     1   {'*','*','*','*','*','Q',
        //     2   {'*','Q','*','*','*','*',
        //     3   {'*','*','*','*','Q','*',
        //     4   {'Q','*','*','*','*','*',
        //     5   {'*','*','*','Q','*','*',

         */
        assertEquals(new Position(5,1), rotateIterator.next());
        assertEquals(new Position(4,3), rotateIterator.next());
        assertEquals(new Position(3,5), rotateIterator.next());
        assertEquals(new Position(2,0), rotateIterator.next());
        assertEquals(new Position(1,2), rotateIterator.next());
        assertEquals(new Position(0,4), rotateIterator.next());
        assertFalse(rotateIterator.hasNext());

    }

    @Test
    public void testRotate8x8_Non_Negative_invarian(){
        final int SIZE = 8;

        Iterator<Position> mockIterator = new PositionMockIterator("0, 4, 7, 5, 2, 6, 1, 3");
        // Non-negative invariant checking
        RotateIterator ri = new RotateIterator(mockIterator, SIZE);
        while(ri.hasNext()) {
            Position pos = ri.next();
            assertTrue(pos.x >= 0);
            assertTrue(pos.y >= 0);
            assertTrue(pos.x < SIZE);
            assertTrue(pos.y < SIZE);
        }


    }


        @Test
    public void test4Rotate8x8(){

        final int SIZE = 8;

        Iterator<Position> mockIterator = new PositionMockIterator("0, 4, 7, 5, 2, 6, 1, 3");
        Iterator<Position> r1 = new RotateIterator(mockIterator, SIZE);
        Iterator<Position> r2 = new RotateIterator(r1, SIZE);
        Iterator<Position> r3 = new RotateIterator(r2, SIZE);
        Iterator<Position> r4 = new RotateIterator(r3, SIZE);

        Iterator<Position> mockIterator2 = new PositionMockIterator("0, 4, 7, 5, 2, 6, 1, 3");

        assertEquals(mockIterator2.next(), r4.next());
        assertEquals(mockIterator2.next(), r4.next());
        assertEquals(mockIterator2.next(), r4.next());
        assertEquals(mockIterator2.next(), r4.next());

        assertEquals(mockIterator2.next(), r4.next());
        assertEquals(mockIterator2.next(), r4.next());
        assertEquals(mockIterator2.next(), r4.next());
        assertEquals(mockIterator2.next(), r4.next());

        assertFalse(r4.hasNext());
    }

}
