package mayton.chess.tools;

import mayton.chess.datastructures.Position;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.Integer.min;
import static java.lang.Integer.valueOf;

public class Utils {

    private Utils() {
    }

    @Nonnull
    public static <T> Stream<T> iteratorToStream(Iterator<T> iterator) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                false);
    }

    @Nonnull
    public static List<Position> intsToPositions(@Nonnull Iterator<Integer> iterator) {
        // TODO: Refactor with lambda if possible
        List<Position> result = new ArrayList<>();
        int y = 0;
        while (iterator.hasNext()) {
            result.add(new Position(iterator.next(), y++));
        }
        return result;
    }

    @Nonnull
    public static <T> List<T> materializeIterator(Iterator<T> iterator) {
        return iteratorToStream(iterator).collect(Collectors.toList());
    }

    @Nonnull
    public static String posCollectionToString(@Nonnull Iterator<Position> iterator) {
        return iteratorToStream(iterator).map(Object::toString).collect(Collectors.joining());
    }

    @Nonnull
    public static List<Integer> positionsStrToIntCollection(@Nonnull String positionsStr) {
        if (positionsStr.isEmpty()){
            return Collections.emptyList();
        }

        return Arrays.stream(positionsStr.split(Pattern.quote(";")))
                .map(posPair -> valueOf(posPair.substring(0, posPair.indexOf(','))))
                .collect(Collectors.toList());

    }

    @SuppressWarnings("unchecked")
    public static List<Integer> posCollectionToIntCollection(Iterator<Position> iterator, int size) {
        List list = Arrays.asList(new Integer[size]);
        while(iterator.hasNext()) {
            Position p = iterator.next();
            list.set(p.y, p.x);
        }
        return list;
    }

    public static String posCollectionToCsvIntString(@Nonnull Iterator<Position> iterator, int size) {
        return posCollectionToIntCollection(iterator, size)
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public static void copyIntIteratorToArray(@Nonnull Iterator<Integer> iterator,@Nonnull int[] res) {
        int i = 0;
        while (iterator.hasNext()){
            res[i++] = iterator.next();
        }
    }

    public static void copyPosIteratorToArray(@Nonnull Iterator<Position> iterator,@Nonnull int[] res) {
        while(iterator.hasNext()) {
            Position p = iterator.next();
            res[p.y] = p.x;
        }
    }

    public static int compareArrays(@Nonnull int[] arr1, @Nonnull int[] arr2){
        int s = min(arr1.length,arr2.length);
        for(int i = 0;i<s;i++){
            if (arr1[i] < arr2[i]) {
                return -1;
            } else if (arr1[i] > arr2[i]) {
                return 1;
            }
        }
        return 0;
    }
}
