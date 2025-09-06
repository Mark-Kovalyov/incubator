package mayton.chess;

import mayton.chess.tools.ReportUtils;
import mayton.chess.tools.Utils;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReportUtilsTest {

    List<Integer> list = Arrays.asList(1,2,3,4,5);

    @Test
    public void testFormatQueens() {
        assertEquals("[]", ReportUtils.formatQueens(Collections.emptyList()));
        assertEquals("[1, 2, 3, 4, 5]", ReportUtils.formatQueens(list));
    }

    @Test
    public void testFormatQueensStream(){
        assertEquals("[]",ReportUtils.formatQueens(Utils.iteratorToStream(Collections.emptyIterator())));
        assertEquals("[1, 2, 3, 4, 5]",ReportUtils.formatQueens(Utils.iteratorToStream(list.iterator())));
    }

    @Test
    public void testFormatOffset() {
        assertEquals("", ReportUtils.formatOffset(-1,'*'));
        assertEquals("", ReportUtils.formatOffset(0,'*'));
        assertEquals("*", ReportUtils.formatOffset(1,'*'));
        assertEquals("**", ReportUtils.formatOffset(2,'*'));
    }

}
