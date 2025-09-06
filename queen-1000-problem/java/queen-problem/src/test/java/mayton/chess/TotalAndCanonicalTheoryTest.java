package mayton.chess;

import mayton.chess.tools.NullOutputStream;
import mayton.chess.tools.NullPrintStream;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TotalAndCanonicalTheoryTest {

    @DataPoint public static Triple<Integer,Integer,Integer> p1 = new ImmutableTriple(5, 10,  2);
    @DataPoint public static Triple<Integer,Integer,Integer> p2 = new ImmutableTriple(6, 4,   1);
    @DataPoint public static Triple<Integer,Integer,Integer> p3 = new ImmutableTriple(7, 40,  6);
    @DataPoint public static Triple<Integer,Integer,Integer> p4 = new ImmutableTriple(8, 92,  12);
    @DataPoint public static Triple<Integer,Integer,Integer> p5 = new ImmutableTriple(9, 352, 46);

    @Theory
    public void checkTheory(Triple<Integer,Integer,Integer> dataPoint) {
        int n         = dataPoint.getLeft();
        int total     = dataPoint.getMiddle();

        MtnQueensGenerator generator = new MtnQueensGenerator(n);
        generator.outstream = new NullPrintStream(new NullOutputStream());
        generator.checkCanonical = true;
        generator.process(0, IntStream.range(0, n).boxed());
        assertEquals(total, generator.solutions);
        // TODO: Continue
    }
}
