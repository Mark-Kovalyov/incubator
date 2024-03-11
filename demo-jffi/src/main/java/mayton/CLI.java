package mayton;

import java.io.PrintStream;

public class CLI {
    public static void println(String s) {
        System.out.println(s);
    }

    public static PrintStream printf(String format, Object ...args) {
        return System.out.printf(format, args);
    }
}
