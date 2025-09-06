package mayton.chess;

import mayton.chess.datastructures.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.Integer.valueOf;

public class PositionMockIterator implements Iterator<Position> {

    Iterator<Position> iterator;

    public PositionMockIterator(String a) {
        String[] s = a.split((", "));
        List<Position> positionList = new ArrayList<>();
        int y = 0;
        for(String item : s){
            positionList.add(new Position(Integer.parseInt(item), y));
            y++;
        }
        iterator = positionList.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Position next() {
        return iterator.next();
    }
}