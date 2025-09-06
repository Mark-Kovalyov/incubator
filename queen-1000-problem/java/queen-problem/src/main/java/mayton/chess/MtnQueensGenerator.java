package mayton.chess;

import mayton.chess.datastructures.Position;
import mayton.chess.tools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.PrintStream;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static java.lang.String.valueOf;
import static java.lang.System.out;
import static java.util.stream.Stream.concat;
import static mayton.chess.Constants.MIN_QUEEN_DESK_SIZE;
import static mayton.chess.tools.ReportUtils.formatOffset;
import static mayton.chess.tools.ReportUtils.formatQueens;

/**
 * See:
 *  Queen problem
 *  --------------
 *  https://en.wikipedia.org/wiki/Eight_queens_puzzle
 *  http://claymath.org/events/news/8-queens-puzzle
 *  http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.51.7113&rep=rep1&type=pdf
 *  http://www.ic-net.or.jp/home/takaken/e/queen/
 *
 * <PRE>
 *  N:           Total          Unique days hh:mm:ss.--
 *  5:              10               2             0.00
 *  6:               4               1             0.00
 *  7:              40               6             0.00
 *  8:              92              12             0.00
 *  9:             352              46             0.00
 * 10:             724              92             0.00
 * 11:            2680             341             0.00
 * 12:           14200            1787             0.00
 * 13:           73712            9233             0.02
 * 14:          365596           45752             0.05
 * 15:         2279184          285053             0.22
 * 16:        14772512         1846955             1.47
 * 17:        95815104        11977939             9.42
 * 18:       666090624        83263591          1:11.21
 * 19:      4968057848       621012754          8:32.54
 * 20:     39029188884      4878666808       1:10:55.48
 * 21:    314666222712     39333324973       9:24:40.50
 </PRE>
 */
public class MtnQueensGenerator {


    static Logger logger = LoggerFactory.getLogger(MtnQueensGenerator.class);

    PrintStream outstream = System.out;
    int solutions                 = 0;
    int printedSolutions          = 0;
    int recursiveProcessCallbacks = 0;
    int canonicalSolutions        = 0;
    int torusSolutions            = 0;

    boolean checkCanonical        = false;
    boolean checkTorus            = false;
    boolean filterCanonical       = false;
    boolean filterTorus           = false;

    int n = -1;

    Deque<Integer> selected = new ArrayDeque<>();
    boolean printDetailed   = false;
    boolean indentSolutions = false;
    boolean printReport     = false;
    boolean traceRecursiveArguments = false;
    // Debug only
    boolean stopAfter      = false;
    static int solutionsThreshold = 100;
    boolean printSummary   = false;


    public MtnQueensGenerator(int n) {
        if (n < MIN_QUEEN_DESK_SIZE) throw new IllegalArgumentException();
        this.n = n;
    }



    // AbstractSet<E>
    //  +- TreeSet<E> : [ NavigableSet<E> ]
    // TODO: Optimize
    public static boolean isCanonical(@Nonnull Iterator<Integer> candidate, int size) {
        final int ROTATES = 4;
        int[] topCandidate = new int[size];
        Arrays.fill(topCandidate, -1);

        List<String> candidatesStr = new ArrayList<>(ROTATES * 2);
        // Phase-1
        List<Position> positionList = Utils.intsToPositions(candidate);
        /*if (size < MIN_QUEEN_DESK_SIZE || positionList.size() < MIN_QUEEN_DESK_SIZE) {
            throw new IllegalArgumentException("Bad argument! isCanonical() designed to process 5x5 or greather matrices only!");
        }*/
        final String candidateStr = Utils.posCollectionToString(positionList.iterator());
        for (int i = 0; i < ROTATES; i++) {
            RotateIterator ri = new RotateIterator(positionList.iterator(), size);
            positionList = Utils.materializeIterator(ri);
            String temp = Utils.posCollectionToCsvIntString(positionList.iterator(), size);
            candidatesStr.add(temp);
        }
        // Swap horizontal (Phase-2)
        Iterator<Position> positionIterator = new SwapHorizontalIterator(positionList.iterator(), size);
        positionList = Utils.materializeIterator(positionIterator);
        for (int i = 0; i < ROTATES; i++) {
            RotateIterator ri = new RotateIterator(positionList.iterator(), size);
            positionList = Utils.materializeIterator(ri);
            String temp = Utils.posCollectionToCsvIntString(positionList.iterator(), size);
            candidatesStr.add(temp);
        }
        // Sorting (Phase-3)
        Collections.sort(candidatesStr);
        boolean res = candidateStr.equals(candidatesStr.get(0));
        return res;
    }


    // Iterable<E> ( ::iterator() )
    //   +- Collection<E> ( ::stream() )
    //        +- Queue<E>
    //             +- Deque<E> ( ::add(), ::addLast(), ::pollLast(), ::size(), ::stream() )
    // TODO: Check!
    public static boolean isConsistent(@Nonnull Deque<Integer> queens) {
        int candidate = queens.peekLast();
        return isConsistent(queens.stream().limit(queens.size() - 1).collect(Collectors.toList()), candidate);
    }

    public static boolean isConsistent(@Nonnull Collection<Integer> queens, int candidate) {
        int n = queens.size();
        int i = 0;
        Iterator<Integer> iterator = queens.iterator();
        while(iterator.hasNext()){
            int qi = iterator.next();
            int deltai = n - i;
            int deltaRow = qi - candidate;
            if (abs(deltaRow) == deltai) {
                return false;
            }
            i++;
        }
        return true;
    }

    public void printSolution(@Nonnull Deque<Integer> queens, int level, boolean isCanonical, boolean isTorus) {

        String placeholder = "";

        if (indentSolutions) {
            placeholder = formatOffset(level, ' ');
            outstream.print(placeholder);
        }

        outstream.print(formatQueens(queens.stream()));

        if (isCanonical) {
            outstream.print(" C");
        }

        if (isTorus) {
            outstream.print(" T");
        }

        outstream.println();

        if (printDetailed) {
            printDetailed(queens, level, placeholder);
        }

        printedSolutions++;

        if (stopAfter && printedSolutions >= solutionsThreshold) {
            System.exit(0);
        }
    }

    private void printDetailed(@Nonnull Deque<Integer> queens, int level, String placeholder) {

        outstream.println(formatOffset(level, '-'));
        int n = queens.size();
        Iterator<Integer> iterator = queens.iterator();

        while(iterator.hasNext()){
            if (indentSolutions) {
                outstream.print(placeholder);
            }
            outstream.print("{");
            int value = iterator.next();
            for (int j = 0; j < n; j++) {
                outstream.print(value == j ? "'Q'" : "'*'");
                outstream.print(",");
            }
            outstream.println("},");
        }
        outstream.println();
    }

    public void process(int level, @Nonnull Stream<Integer> candidates) {
        recursiveProcessCallbacks++;
        if (traceRecursiveArguments) {
            outstream.printf("%s %s %s \n", formatOffset(level, '.'), formatQueens(candidates), formatQueens(selected.stream()));
        }
        if (selected.size() == n) {
            if (isConsistent(selected)) {
                solutions++;

                // Canonical
                boolean canonical = false;
                if (checkCanonical) {
                    if (isCanonical(selected.iterator(), n)){
                        canonicalSolutions++;
                        canonical = true;
                    }
                }

                // Torus
                boolean torus = false;
                if (checkTorus) {
                    TorusChecker torusChecker = new TorusChecker(n);
                    if (torusChecker.isTorus(selected.iterator())) {
                        torusSolutions++;
                        torus = true;
                    }
                }

                printSolution(selected, level, canonical, torus);
            }
        } else {
            // dequeA       newCandidate      dequeB
            // ==================================
            // []           []        [0,1,2,3]
            // -----------------------------------
            // []           [0]       [1,2,3]
            // [0]          [1]       [2,3]
            // [0,1]        [2]       [3]
            // [0,1,2]      [3]       []
            // ----------------------------------
            // [0,1,2,3]    []        []
            Deque<Integer> dequeA = new ArrayDeque<>();
            Deque<Integer> dequeB = new ArrayDeque<>(candidates.collect(Collectors.toList()));
            while (!dequeB.isEmpty()) {
                int newCandidate = dequeB.pollFirst();
                if (isConsistent(selected, newCandidate)) {
                    selected.addLast(newCandidate);
                    // TODO: Investigate for lazy operations
                    process(level + 1, concat(dequeA.stream(), dequeB.stream()));
                    selected.pollLast();
                }
                dequeA.add(newCandidate);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length==0){
            out.println("Usage: java -jar QueenProblem-XX.YYYY.jar DESK_SIZE [CT] [CC] [FT | FC] [PD] [PS] [SO]");
            out.println("Where:");
            out.println("     DESK_SIZE       : size of desk (4,5,6, e.t.c)");
            out.println("     CT              : check torus solutions");
            out.println("     CC              : check canonical solutions");

            out.println("     FT or FC        : filter torus or canonical during print");

            out.println("     PD              : print detailed report like a matrix (string)");
            out.println("     PS              : print summary report (string)");

            out.println("     SO              : suppress all text output");
        } else {
            Instant start = Instant.now();
            MtnQueensGenerator generator = new MtnQueensGenerator(parseInt(args[0]));
            int argcnt = 1;
            while(argcnt < args.length) {
                if ("CT".equalsIgnoreCase(args[argcnt])) generator.checkTorus      = true;
                if ("CC".equalsIgnoreCase(args[argcnt])) generator.checkCanonical  = true;

                if ("FC".equalsIgnoreCase(args[argcnt])) generator.filterCanonical = true;
                if ("FT".equalsIgnoreCase(args[argcnt])) generator.filterTorus     = true;

                if ("PD".equalsIgnoreCase(args[argcnt])) generator.printDetailed   = true;
                if ("PS".equalsIgnoreCase(args[argcnt])) generator.printSummary    = true;
                if ("SO".equalsIgnoreCase(args[argcnt])) generator.outstream       = new NullPrintStream(new NullOutputStream());
                argcnt++;
            }
            generator.process(0, IntStream.range(0, generator.n).boxed());
            Instant end = Instant.now();
            if (generator.printSummary) {
                out.println("=======================================================");
                out.printf("Solutions           : %d\n", generator.solutions);
                out.printf("Canonical solutions : %s\n", !generator.checkCanonical ? "Unknown" : valueOf(generator.canonicalSolutions));
                out.printf("Torus solutions     : %s\n", !generator.checkTorus ? "Unknown" : valueOf(generator.torusSolutions));
                long elapsed = Duration.between(start, end).toMillis();
                out.printf("Elapsed time   : %s ms\n", elapsed);
                out.printf("AVG speed      : %d solutions/s\n", 1000L * generator.solutions / elapsed);
            }
        }
    }


}
