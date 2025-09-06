package mayton.chess.backtracking;

import mayton.chess.datastructures.RestrictedClassicQueensDesk;

import java.io.File;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;

import static mayton.chess.Constants.QUEEN_DESK_SIZE;

public class QueensHeuristicBackTracking {

    static Random random = new SecureRandom();

    public static void main(String[] args) {

        // Init desk with N = 1000, init main indexes, datastructures

        int n = QUEEN_DESK_SIZE;

        RestrictedClassicQueensDesk rqd = new RestrictedClassicQueensDesk(n);

        boolean res = fillRandomPercent(20, rqd);

        process(rqd);

    }

    private static void process(RestrictedClassicQueensDesk rqd) {

    }

    private static boolean fillRandomPercent(int m, RestrictedClassicQueensDesk rqd) {

        for(int i = 0 ; i < m ; i++) {
            int x = random.nextInt(QUEEN_DESK_SIZE);
            int y = random.nextInt(QUEEN_DESK_SIZE);
            boolean res = rqd.tryToSetQueen(x,y);
        }

        return true;
    }


}
