package mayton;

import java.io.File;
import java.io.IOException;

public class Utils {

    public static long fileSize(String path) throws IOException {
        File file = new File(path);
        return file.length();
    }

    public static void println(Object format) {
        System.out.println(format);
    }

    public static void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

}
