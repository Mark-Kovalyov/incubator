package mayton.chess;

import mayton.chess.datastructures.RestrictedClassicQueensDesk;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestrictedClassicChessDeskTest {

    @Test
    public void test_01_HorizonalAndVerticalFire() {

        // 1 1 1 1 = vertical
        //
        // -----------------------
        // 0 0 1 0   1   horizontal
        // 1 0 0 0   1
        // 0 0 0 0   0
        // 0 1 0 0   1
        //
        RestrictedClassicQueensDesk iqd = new RestrictedClassicQueensDesk(4);

        assertTrue(iqd.tryToSetQueen(2, 0));
        assertTrue(iqd.tryToSetQueen(0, 1));

        assertTrue(iqd.tryToSetQueen(1, 3));

        assertTrue(iqd.isVerticalUnderFire(0));
        assertTrue(iqd.isVerticalUnderFire(1));
        assertTrue(iqd.isVerticalUnderFire(2));
        assertFalse(iqd.isVerticalUnderFire(3));

        assertTrue(iqd.isHorizontalUnderFire(0));
        assertTrue(iqd.isHorizontalUnderFire(1));
        assertFalse(iqd.isHorizontalUnderFire(2));
        assertTrue(iqd.isHorizontalUnderFire(3));

    }


    @Test
    public void test_02_LeftDiagonal() {

        RestrictedClassicQueensDesk iqd = new RestrictedClassicQueensDesk(5);

        //   1 0 0 1 1 0 1 1 0
        // 1 0 0 0 0|
        // 0 0 0 1 0|
        // 0 1 0 0 0|
        // 0 0 0 0 1|
        // 0 0 1 0 0|

        iqd.tryToSetQueen(0,0);
        iqd.tryToSetQueen(3,1);
        iqd.tryToSetQueen(1,2);
        iqd.tryToSetQueen(4,3);
        iqd.tryToSetQueen(2,4);

        //assertTrue(iqd.isLeftTopDiagonalUnderFire(0));
        assertFalse(iqd.isLeftTopDiagonalUnderFire(1));
        assertFalse(iqd.isLeftTopDiagonalUnderFire(2));
        //assertTrue(iqd.isLeftTopDiagonalUnderFire(3));

        //assertFalse(iqd.isLeftTopDiagonalUnderFire(4));
        //assertTrue(iqd.isLeftTopDiagonalUnderFire(5));
        assertTrue(iqd.isLeftTopDiagonalUnderFire(6));
        assertTrue(iqd.isLeftTopDiagonalUnderFire(7));
        assertFalse(iqd.isLeftTopDiagonalUnderFire(8));


        
    }


    @Test
    public void test_03_RightDiagonal() {

        RestrictedClassicQueensDesk iqd = new RestrictedClassicQueensDesk(5);

        //
        // 1 0 0 0 0
        // 0 0 0 1 0
        // 0 1 0 0 0
        // 0 0 0 0 1
        // 0 0 1 0 0

        iqd.tryToSetQueen(0,0);
        iqd.tryToSetQueen(3,1);
        iqd.tryToSetQueen(1,2);
        iqd.tryToSetQueen(4,3);
        iqd.tryToSetQueen(2,4);



    }
}
