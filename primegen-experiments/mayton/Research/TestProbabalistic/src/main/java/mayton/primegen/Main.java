package mayton.primegen;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.System.currentTimeMillis;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;

public class Main {

    public static PrintStream printf(String format, Object ...args) {
        return System.out.printf(format, args);
    }

    public static Iterable<String> test(String labelFrom, String labelTo, BigInteger a, BigInteger b, boolean dump) {
        List<String> res = new ArrayList<>();
        long t1 = currentTimeMillis();
        int cnt = 0;
        BigInteger from = a;
        do {
            a = a.nextProbablePrime();
            if (dump) {
                res.add(labelFrom + " + " + a.subtract(from));
            }
            cnt++;
        } while(a.compareTo(b) < 0);
        cnt--;
        long t2 = currentTimeMillis();
        long elapedTimeS = (t2 - t1) / 1000;
        long avgSpeed = elapedTimeS != 0 ? cnt / elapedTimeS : 0;
        printf("%s - %s;;%d;;%d;%d\n", labelFrom, labelTo, cnt, elapedTimeS, avgSpeed);
        return res.subList(0, res.size() - 1);
    }

    // Function to get the Stream
    public static <T> Stream<T> getStreamFromIterator(Iterator<T> iterator) {
        // Convert the iterator to Spliterator
        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator, 0);
        // Get a Sequential Stream from spliterator
        return StreamSupport.stream(spliterator, false);
    }

    public static void main(String[] args) {
        printf("[CSV=;]Range; Primes Found(Usoff) ; Primes Found(JDK::BigInteger) ; Primes (theoretical) ; Elapsed time(s) ; Average Speed (primes/sec)\n");

        test("2", "100", BigInteger.valueOf(2), BigInteger.valueOf(100), false);

        test("2^200 + 1", "2^200 + 1 + 1 000 000", TWO.pow(200).add(ONE),  TWO.pow(200).add(ONE).add(BigInteger.valueOf(1_000_000)), false);
        test("2^500 + 1", "2^500 + 1 + 200 000",   TWO.pow(500).add(ONE),  TWO.pow(500).add(ONE).add(BigInteger.valueOf(200_000)), false);
        test("2^1024 + 1", "2^1024 + 1 + 200 000", TWO.pow(1024).add(ONE), TWO.pow(1024).add(ONE).add(BigInteger.valueOf(200_000)), false);
        test("2^2048 + 1", "2^2048 + 1 + 100 000", TWO.pow(2048).add(ONE), TWO.pow(2048).add(ONE).add(BigInteger.valueOf(100_000)), false);
        Iterable<String> res = test("2^4096 + 1", "2^4096 + 1 + 50 000", TWO.pow(4096).add(ONE), TWO.pow(4096).add(ONE).add(BigInteger.valueOf(50_000)), true);
        printf("[/CSV]\n");
        getStreamFromIterator(res.iterator()).forEach((x) -> System.out.printf("%s\n", x));
    }

}
