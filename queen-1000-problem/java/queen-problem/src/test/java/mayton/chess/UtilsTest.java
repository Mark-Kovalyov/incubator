package mayton.chess;

import mayton.chess.datastructures.Position;
import mayton.chess.tools.Utils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.*;

import static com.google.common.primitives.Ints.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UtilsTest {

    private List<Position> positions;
    private List<Integer> ints;

    @Before
    public void before(){
        ints = asList(1,3,5,0,2,4);

        positions = new ArrayList<>();
        positions.add(new Position(1,0));
        positions.add(new Position(3,1));
        positions.add(new Position(5,2));
        positions.add(new Position(0,3));
        positions.add(new Position(2,4));
        positions.add(new Position(4,5));
    }

    @Test
    public void test_01_String2Pos(){
        assertThat(Collections.<Integer>emptyList(), is(Utils.positionsStrToIntCollection("")));
        assertThat(ints, is(Utils.positionsStrToIntCollection("1,0;3,1;5,2;0,3;2,4;4,5;")));
    }

    @Test
    public void test_02_Ints2Positions() {
        assertThat(Collections.emptyList(), is(Utils.intsToPositions(Collections.emptyIterator())));
        assertThat(positions, is(Utils.intsToPositions(ints.iterator())));
    }

    @Test
    public void test_03_MaterializeIterator(){
        List<Position> positionList = Utils.materializeIterator(positions.iterator());
        assertThat(positionList, is(positions));
    }

    @Test(expected = NullPointerException.class)
    public void test_04_CompareArrays() {
        Utils.compareArrays(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void test_04_CompareArrays2() {
        Utils.compareArrays(new int[]{}, null);
    }

    @Test(expected = NullPointerException.class)
    public void test_05_CompareArrays3() {
        Utils.compareArrays(null,new int[]{});
    }

    @Test
    public void test_06_CompareArrays4() {
        assertEquals(-1, Utils.compareArrays(new int[]{1},new int[]{2}));
        assertEquals(1, Utils.compareArrays(new int[]{2},new int[]{1}));
        assertEquals(0, Utils.compareArrays(new int[]{1},new int[]{1}));
    }

    @Test
    public void test_07_PosCollectionToStr(){
        assertEquals("1,0;3,1;5,2;0,3;2,4;4,5;", Utils.posCollectionToString(positions.iterator()));
    }

    @Test
    public void test_08_PosCollectionToCsvIntString() {
        assertEquals("1,3,5,0,2,4", Utils.posCollectionToCsvIntString(positions.iterator(), positions.size()));
        /**
         *    0 1 2 3 4 5 6 7
         *  0 . . . Q . . . .
         *  1 . . . . . . Q .
         *  2 . . Q . . . . .
         *  3 . . . . . . . Q
         *  4 . Q . . . . . .
         *  5 . . . . Q . . .
         *  6 Q . . . . . . .
         *  7 . . . . . Q . .
         *
         */
        List<Position> p = new ArrayList<>();

        p.add(new Position(5,7));
        p.add(new Position(0,6));
        p.add(new Position(4,5));
        p.add(new Position(1,4));

        p.add(new Position(3,0));
        p.add(new Position(6,1));
        p.add(new Position(2,2));
        p.add(new Position(7,3));

        assertEquals("3,6,2,7,1,4,0,5",
                Utils.posCollectionToCsvIntString(p.iterator(), p.size()));
    }

    @Test
    public void test_09_copyIntIteratorToArray() {
        int[] arr = new int[4];

        Utils.copyIntIteratorToArray(
                asList(new int[]{2,5,3,4}).iterator(),
                arr);

        assertEquals(2, arr[0]);
        assertEquals(5, arr[1]);
        assertEquals(3, arr[2]);
        assertEquals(4, arr[3]);
    }


    @Test
    public void test_10_copyIntIteratorToArray() {
        int[] arr = new int[5];

        List<Position> positions = new ArrayList<Position>(){{
            add(new Position(1,0));
            add(new Position(4,1));
            add(new Position(2,2));
            add(new Position(0,3));
            add(new Position(3,4));
        }};

        Utils.copyPosIteratorToArray(
                positions.iterator(),
                arr);

        assertEquals(1, arr[0]);
        assertEquals(4, arr[1]);
        assertEquals(2, arr[2]);
        assertEquals(0, arr[3]);
        assertEquals(3, arr[4]);
    }

}

