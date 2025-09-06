package mayton.chess.tools;

import mayton.chess.datastructures.RestrictedTorusQueensDesk;

import java.util.Iterator;

public class TorusChecker {

    RestrictedTorusQueensDesk restrictedTorusQueensDesk;

    public TorusChecker(int size) {
        restrictedTorusQueensDesk = new RestrictedTorusQueensDesk(size);
    }

    public boolean isTorus(Iterator<Integer> iterator) {
        int y = 0;
        boolean result = true;
        while(iterator.hasNext()){
            int x = iterator.next();
            if (!restrictedTorusQueensDesk.tryToSetQueen(x,y)){
                result = false;
                break;
            };
            y++;
        }
        return result;
    }
}
