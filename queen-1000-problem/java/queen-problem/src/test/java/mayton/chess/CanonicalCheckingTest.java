package mayton.chess;

import mayton.chess.tools.CanonicalChecker;
import mayton.chess.tools.Utils;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CanonicalCheckingTest {

    @Ignore
    @Test
    public void testIsCanonical(){
        CanonicalChecker canonicalChecker = new CanonicalChecker(8);
        // TODO:
        assertFalse(canonicalChecker.isCanonical(asList(6,3,5,7,1,4,2,8).iterator()));
        assertFalse(canonicalChecker.isCanonical(asList(4,2,7,3,6,8,5,1).iterator()));

        assertTrue(canonicalChecker.isCanonical(asList(1,7,5,8,2,4,6,3).iterator()));
    }

    @Test(expected = NullPointerException.class)
    public void testNpe(){
        CanonicalChecker canonicalChecker = new CanonicalChecker(8);
        canonicalChecker.isCanonical(null);
    }


    @Test
    public void testCanonical6x6() {
        final int SIZE = 6;
        // Total: 40, canonical : 6
        String s1 = "0,1;1,3;2,5;3,0;4,2;5,4;";
        // TODO: Fix
        //assertTrue(MtnQueensGenerator.isCanonical(Utils.positionsStrToIntCollection(s1).iterator(),SIZE));
        String s2 = "5,5;4,4;3,3;2,2;1,1;0,0;";
        assertFalse(MtnQueensGenerator.isCanonical(Utils.positionsStrToIntCollection(s2).iterator(),SIZE));
    }

    @Ignore
    @Test
    public void testCanonical8x8() {
        // Total: 92, canonical : 12
        final int SIZE = 8;
        String s1 = "0,0;1,4;2,7;3,5;4,2;5,6;6,1;7,3;";
        // TODO:
        assertTrue(MtnQueensGenerator.isCanonical(Utils.positionsStrToIntCollection(s1).iterator(),SIZE));
    }

}
