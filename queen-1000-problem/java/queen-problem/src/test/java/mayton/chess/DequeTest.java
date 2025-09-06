package mayton.chess;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DequeTest {

    @Test
    public void test(){
        Deque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);

        assertEquals(3, deque.size());
        assertEquals(valueOf(1),deque.getFirst());
        assertEquals(valueOf(1),deque.peekFirst());
        assertEquals(valueOf(1),deque.pollFirst());

        assertEquals(2, deque.size());

        assertEquals(valueOf(2),deque.pollFirst());
        assertEquals(valueOf(3),deque.pollFirst());

        assertEquals(0, deque.size());

        assertNull(deque.pollFirst());
        assertNull(deque.peekFirst());


    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElement(){
        Deque<Integer> deque = new ArrayDeque<>();
        assertNull(deque.getFirst());
    }

    @Test
    public void testPush(){
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(3);
        deque.addLast(5);
        assertEquals(2,deque.size());
        assertEquals(valueOf(5),deque.pollLast());
        assertEquals(valueOf(3),deque.pollLast());
        assertNull(deque.pollLast());
        assertNull(deque.pollLast());
        assertNull(deque.pollLast());
    }



}
