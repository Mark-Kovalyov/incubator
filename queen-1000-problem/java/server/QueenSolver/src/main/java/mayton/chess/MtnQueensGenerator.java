package mayton.chess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.join;
import static java.lang.String.valueOf;
import static java.lang.System.out;

public class MtnQueensGenerator {

    static Logger logger = LoggerFactory.getLogger(MtnQueensGenerator.class);

    public static Integer tailElement(@Nonnull List<Integer> arg) {
        return arg.get(arg.size() - 1);
    }

    // TODO: Remove
    public static List<Integer> headElements(@Nonnull List<Integer> arg) {
        int n = arg.size();
        return arg.stream().limit(n - 1).collect(Collectors.toList());
    }
    public static List<Integer> subList(@Nonnull List<Integer> arg, int i) {
        if (arg == null) {
            throw new IllegalArgumentException("Array cannot be null!");
        }
        int n = arg.size();
        if (i < 0 || i >= n) {
            throw new IllegalArgumentException("Index is out of range");
        }
        if (i == 0) {
            if (n == 1) {
                return Collections.emptyList();
            } else {
                // TODO: Investigate for performance
                return arg.stream().skip(1).collect(Collectors.toList());
            }
        }
        if (i == n - 1) {
            // TODO: Investigate for performance
            return arg.stream().limit(n - 1).collect(Collectors.toList());
        }
        // TODO: Investigate for performance
        List<Integer> res = new ArrayList<>(arg.stream().limit(i).collect(Collectors.toList()));
        res.addAll(arg.stream().skip(i + 1).collect(Collectors.toList()));
        return res;
    }

    public static boolean isConsistent(@Nonnull List<Integer> q, int candidate) {
        int n = q.size();
        for (int i = 0; i < n; i++) {
            int qi = q.get(i);
            int deltai = n - i;
            int deltaRow = qi - candidate;
            if (Math.abs(deltaRow) == deltai) {
                return false;
            }
        }
        return true;
    }

    private static void printSolution(@Nonnull List<Integer> queens) {
        out.printf("Queens : %s\n", formatQueens(queens));
        out.printf("----------------\n");
        int n = queens.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                out.print(queens.get(i) == j ? "Q" : "*");
                out.print(" ");
            }
            out.println();
        }
        out.println();
    }

    // TODO: Replace with Guava or smth else
    static List<Integer> fromArray(@Nonnull int[] array) {
        List<Integer> res = new ArrayList<>(array.length);
        for (int elem : array) {
            res.add(elem);
        }
        return res;
    }


    public static String formatQueens(List<Integer> list) {
        StringBuilder sb = new StringBuilder("");
        sb.append(join(",", valueOf(list)));
        sb.append("");
        return sb.toString();
    }

    public static String formatOffset(int n){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<n; i++) sb.append(".");
        return sb.toString();
    }

    public static void process(int n, int level, @Nonnull List<Integer> candidates, @Nonnull List<Integer> selected) {
        out.printf("%s %d %d %s %s \n", formatOffset(level), n, level, formatQueens(candidates), formatQueens(selected));
        if (selected.size() == n) {
            // TODO: Improove overloaded isConsistent
            if (isConsistent(headElements(selected), tailElement(selected))) {
                solutions++;
                printSolution(selected);
            }
        } else {
            for (int i = 0; i < candidates.size(); i++) {
                int newCandidate = candidates.get(i);
                if (isConsistent(selected, newCandidate)) {
                    List<Integer> newSelected = new ArrayList<>(selected);
                    newSelected.add(newCandidate);
                    process(n, level + 1, subList(candidates, i), newSelected);
                }
            }
        }
    }

    static int solutions = 0;

    public static void main(String[] args) {
        int n = 8;
        process(n, 0, fromArray(IntStream.range(0, n).toArray()), new ArrayList<>());
        out.printf("Solutions : %d", solutions);
    }

}
