package mayton.chess;

import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static com.google.common.primitives.Ints.asList;
import static mayton.chess.MtnQueensGenerator.isConsistent;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MtnQueensGeneratorTest {
    @Test
    public void testConsistentCollection(){

        assertTrue(isConsistent(asList(2, 4, 1),3));

        assertTrue(isConsistent(asList(3, 1, 4),2));

        assertFalse(isConsistent(asList(1, 2, 3),2));

    }


    @Test
    public void testConsistentDeque(){
        Deque<Integer> desk1 = new ArrayDeque<Integer>(){{
            addLast(2);
            addLast(4);
            addLast(1);
        }};
        assertTrue(isConsistent(desk1));

        Deque<Integer> desk2 = new ArrayDeque<Integer>(){{
            addLast(3);
            addLast(1);
            addLast(4);
        }};
        assertTrue(isConsistent(desk2));
    }
}
