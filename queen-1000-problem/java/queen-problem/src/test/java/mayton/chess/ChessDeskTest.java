package mayton.chess;

import mayton.chess.datastructures.ChessDesk;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChessDeskTest {

    @Test
    public void test_01_get_and_set() {
        ChessDesk qd = new ChessDesk(2);
        assertEquals(2, qd.getSize());

        qd.setValue(0,0);
        qd.setValue(0,1);
        qd.setValue(1,0);
        qd.setValue(1,1);

        assertTrue(qd.getValue(0,0));
        assertTrue(qd.getValue(0,1));
        assertTrue(qd.getValue(1,0));
        assertTrue(qd.getValue(1,1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_02_out_of_range_x() {
        ChessDesk qd = new ChessDesk(2);
        qd.setValue(5,0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_03_out_of_range_y() {
        ChessDesk qd = new ChessDesk(2);
        qd.setValue(0,5);
    }

}
