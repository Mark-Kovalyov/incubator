package mayton.chess.tools;

import mayton.chess.MtnQueensGenerator;
import mayton.chess.datastructures.Position;
import mayton.chess.tools.RotateIterator;
import mayton.chess.tools.SwapHorizontalIterator;
import mayton.chess.tools.Utils;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static mayton.chess.Constants.MIN_QUEEN_DESK_SIZE;

// TODO: Complete
public class CanonicalChecker {

    private static final int ROTATES = 4;
    private int size = 0;
    private int[] argumentArray;

    public CanonicalChecker(int size) {
        if (size < MIN_QUEEN_DESK_SIZE) {
            throw new IllegalArgumentException();
        }
        this.size = size;
        this.argumentArray = new int[size];
    }

    public boolean isCanonical(@Nonnull Iterator<Integer> argument) {

        // First minPosition is argument
        Utils.copyIntIteratorToArray(argument, argumentArray);

        // Copy ints to positions
        List<Position> positionList = Utils.intsToPositions(argument);

        if (positionList.size() == 0) {
            // Zero-size solution is undefined to be canonical. So false.
            return false;
        }

        if (positionList.size() == 1) {
            // 1-cell size solution is canonical always by definition.
            return true;
        }

        // Rotate 90 degrees right 4 times
        for (int i = 0; i < ROTATES; i++) {
            // Init temp array with (-1,-1,-1,.....)
            int[] temp = new int[size];
            Arrays.copyOf(temp, -1);
            RotateIterator ri = new RotateIterator(positionList.iterator(), size);
            positionList = Utils.materializeIterator(ri);
            Iterator<Position> ip = positionList.iterator();
            while (ip.hasNext()) {
                Position p = ip.next();
                int x = p.x;
                int y = p.y;
                if (temp[y] < 0) {
                    temp[y] = x;
                } else {
                    throw new IllegalStateException();
                }
            }
            // If more minimal found than argumentArray then - non canonical
            if (Utils.compareArrays(temp, argumentArray) < 0) {
                return false;
            }
        }

        // Swap horizontal
        Iterator<Position> positionIterator = new SwapHorizontalIterator(positionList.iterator(), size);
        positionList = Utils.materializeIterator(positionIterator);

        // Rotate 90 degrees right 4 times


        return true;
    }

}
