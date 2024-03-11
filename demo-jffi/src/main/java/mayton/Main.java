package mayton;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.nio.file.Path;

import static java.lang.foreign.ValueLayout.JAVA_LONG;
import static mayton.CLI.printf;
import static mayton.CLI.println;

public class Main {

    public static void main(String[] args) {
        println(":: Start");

        println(":: initialize libs");

        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");

        printf(":: os.name = '%s'\n", osName);
        printf(":: os.arch = '%s'\n", osArch);

        // Call Foregn Functions API
        SymbolLookup sumlib   = null;
        SymbolLookup primelib = null;

        if (osName.toLowerCase().startsWith("windows")) {
            // Windows 10
            // amd64
            sumlib   = SymbolLookup.libraryLookup(Path.of("sumlib-amd64.dll"), Arena.ofAuto());
            primelib = SymbolLookup.libraryLookup(Path.of("prime-amd64.dll"), Arena.ofAuto());
        } else {
            // Linux
            // amd64
            sumlib   = SymbolLookup.libraryLookup(Path.of("sumlib-amd64.so"), Arena.ofAuto());
            primelib = SymbolLookup.libraryLookup(Path.of("prime-amd64.so"), Arena.ofAuto());
        }

        println(":: Symb lookup ");

        MemorySegment sumSegment = sumlib.find("sum").orElseThrow();
        MemorySegment gcdSegment = primelib.find("gcd").orElseThrow();

        println(":: Method handle");

        MethodHandle sum = Linker.nativeLinker().downcallHandle(
                sumSegment,
                FunctionDescriptor.of(JAVA_LONG, JAVA_LONG, JAVA_LONG)
        );

        MethodHandle gcd = Linker.nativeLinker().downcallHandle(
                gcdSegment,
                FunctionDescriptor.of(JAVA_LONG, JAVA_LONG, JAVA_LONG)
        );

        println(":: Invoke");
        try {
            long len = (long) sum.invoke(36L, 26L);
            System.out.println("sum, taken from asm library = " + len);

            long gcc = (long) gcd.invoke(125L, 45);
            System.out.println("gcc, taken from GNU c library = " + gcc);

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        println("::4");

    }
}
