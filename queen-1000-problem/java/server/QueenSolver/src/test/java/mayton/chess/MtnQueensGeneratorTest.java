package mayton.chess;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static mayton.chess.MtnQueensGenerator.isConsistent;
import static mayton.chess.MtnQueensGenerator.subList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MtnQueensGeneratorTest {

    @Test(expected = IllegalArgumentException.class)
    public void sublistNullZero() {
        subList(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sublistEmptyNegative() {
        subList(Collections.emptyList(), -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sublistEmptyOutOfRange() {
        subList(Collections.emptyList(), 10);
    }

    @Test
    public void subFirst() {
        List<Integer> res = subList(Lists.newArrayList(3, 5), 0);
        assertEquals(1, res.size());
        assertEquals(5, (int) res.get(0));
    }

    @Test
    public void subListLast() {
        List<Integer> res = subList(Lists.newArrayList(7, 11), 1);
        assertEquals(1, res.size());
        assertEquals(7, (int) res.get(0));
    }

    @Test
    public void subMiddle() {
        List<Integer> res = subList(Lists.newArrayList(13, 17, 19, 23, 27), 2);
        assertEquals(4, res.size());
        assertEquals(13, (int) res.get(0));
        assertEquals(17, (int) res.get(1));
        assertEquals(23, (int) res.get(2));
        assertEquals(27, (int) res.get(3));
    }


    @Test
    public void subMiddle2() {
        List<Integer> res = subList(Lists.newArrayList(13, 17, 19, 23, 27), 1);
        assertEquals(4, res.size());
        assertEquals(13, (int) res.get(0));
        assertEquals(19, (int) res.get(1));
        assertEquals(23, (int) res.get(2));
        assertEquals(27, (int) res.get(3));
    }

    @Test
    public void testConsistent(){
        List<Integer> desk1 = Lists.newArrayList(2,4,1);
        assertTrue(isConsistent(desk1,3));
        List<Integer> desk2 = Lists.newArrayList(3,1,4);
        assertTrue(isConsistent(desk2,2));
    }


}
