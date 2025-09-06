package mayton.chess;

import mayton.chess.datastructures.RestrictedQueensDesk;
import mayton.chess.datastructures.RestrictedTorusQueensDesk;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static mayton.chess.datastructures.RestrictedTorusQueensDesk.calcCycledRightDiagonal;
import static mayton.chess.datastructures.RestrictedTorusQueensDesk.calcRightDiagonal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestrictedTorusChessDeskTest {

    @Test
    public void test_00_00(){
        // 0 1 2 3 4
        // ----------
        // 1 0 0 0 0
        // 0 0 0 1 0
        // 0 1 0 0 0
        // 0 0 0 0 1
        // 0 0 1 0 0

        int size = 5;

        int offset = calcRightDiagonal(0,0,size);
        assertEquals(0, offset);

        offset = calcRightDiagonal(3,1,size);
        assertEquals(4, offset);

        offset = calcRightDiagonal(1,2,size);
        assertEquals(3, offset);

        offset = calcRightDiagonal(4,3,size);
        assertEquals(7, offset);

        offset = calcRightDiagonal(2,4,size);
        assertEquals(6, offset);

    }

    @Test
    public void test_00_calcCycledRightDiagonal(){

        // 0 1 2 3 4
        // ----------
        // 1 0 0 0 0
        // 0 0 0 1 0
        // 0 1 0 0 0
        // 0 0 0 0 1
        // 0 0 1 0 0

        int size = 5;

        int offset = calcCycledRightDiagonal(0,0,size);
        assertEquals(0, offset);

        offset = calcCycledRightDiagonal(3,1,size);
        assertEquals(4, offset);

        offset = calcCycledRightDiagonal(1,2,size);
        assertEquals(3, offset);

        offset = calcCycledRightDiagonal(4,3,size);
        assertEquals(2, offset);

        offset = calcCycledRightDiagonal(2,4,size);
        assertEquals(1, offset);
    }

    @Test
    public void test_01_toroidal_5x5_desk(){
        // 0 1 2 3 4
        // ----------
        // 1 0 0 0 0
        // 0 0 0 1 0
        // 0 1 0 0 0
        // 0 0 0 0 1
        // 0 0 1 0 0
        RestrictedQueensDesk desk = new RestrictedTorusQueensDesk(5);

        assertTrue(desk.tryToSetQueen(0,0));
        assertTrue(desk.tryToSetQueen(3,1));
        assertTrue(desk.tryToSetQueen(1,2));
        assertTrue(desk.tryToSetQueen(4,3));
        assertTrue(desk.tryToSetQueen(2,4));
    }


    @Test
    public void test_02_50x50() {
        RestrictedQueensDesk desk = new RestrictedTorusQueensDesk(25);
        assertTrue(desk.tryToSetQueen(3,4));
        //assertTrue(desk.tryToSetQueen(4,21));
        System.out.println(desk.toString());

    }



}
