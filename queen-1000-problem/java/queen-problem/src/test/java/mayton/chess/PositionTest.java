package mayton.chess;

import mayton.chess.datastructures.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PositionTest {

    @Test
    public void testPos(){
        Position p = new Position(1,2);
        assertEquals("1,2;", p.toString());
        assertEquals(p, new Position(1,2));
    }

}
