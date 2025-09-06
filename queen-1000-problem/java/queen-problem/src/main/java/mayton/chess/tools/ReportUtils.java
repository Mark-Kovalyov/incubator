package mayton.chess.tools;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.join;
import static java.lang.String.valueOf;

public class ReportUtils {

    private ReportUtils(){}

    public static String formatQueens(@Nonnull List<Integer> list) {
        return join(",", valueOf(list));
    }

    // TODO: Optimize with stdout
    public static String formatQueens(@Nonnull Stream<Integer> stream) {
        StringBuilder sb = new StringBuilder("[");
        sb.append(stream.map(Object::toString).collect(Collectors.joining(", ")));
        sb.append("]");
        return sb.toString();
    }

    public static String formatOffset(int n, char c) {
        if (n <= 0) return "";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) sb.append(c);
        return sb.toString();
    }

}
