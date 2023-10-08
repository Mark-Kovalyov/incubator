package fdx;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;

public class Main {

    static byte[] formatUtf8TwoBytes(int code) {
        byte[] arr = new byte[2];

        return arr;
    }

    static int countBits(String s) {
        int cnt = 0;
        for(int i = 0 ; i<s.length(); i++) {
            if (s.charAt(i) == '1') cnt++;
        }
        return cnt;
    }

    static String formatBin(int n, int cropDigits) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<32;i++) {
            sb.append((n & 0x1) == 1 ? "1" : "0");
            n >>= 1;
        }
        String res = StringUtils.reverse(sb.toString());
        return res.substring(32 - cropDigits);
    }

    static class QuineStruct {
        public String xxx;
        public String yyy;
        public int cnt;
    }

    public static void main(String[] args) throws Exception {

        Charset win1251 = Charset.forName("windows-1251");
        Charset koi8r = Charset.forName("koi8-r");
        Charset cp866 = Charset.forName("cp866");

        byte[] progression = new byte[128];
        for(int i=0;i<128;i++) progression[i] = (byte) ((i + 128) & 0xFF);
        ByteArrayInputStream bis = new ByteArrayInputStream(progression);

        InputStreamReader isr = new InputStreamReader(bis, koi8r);
        char[] koi8_chars = new char[256];
        System.out.println("Character CP866         UTF-16                Count bits");
        System.out.println("--------- ----------    --------------------- ----------");

        List<QuineStruct> p = new ArrayList<>();

        TreeMap<Integer, List<QuineStruct>> quineOrderedByBits = new TreeMap<>();

        for(int i = 0; i < 128 ; i++) {
            char c = (char) isr.read();
            koi8_chars[i] = c;
            //System.out.print(c);
            int code = (int) c;
            String xxx = formatBin(i, 8);
            String yyy = formatBin(code, 16);
            QuineStruct q = new QuineStruct();
            q.xxx = xxx;
            q.yyy = yyy;
            q.cnt = countBits(yyy);
            p.add(q);
            if (quineOrderedByBits.containsKey(q.cnt)) {
                quineOrderedByBits.get(q.cnt).add(q);
            } else {
                List<QuineStruct> ql = new ArrayList<>();
                ql.add(q);
                quineOrderedByBits.put(q.cnt, ql);
            }
            System.out.printf("%c         %s (%02X) %s (%04X) %d\n", c, xxx, 128 + i, yyy, code, q.cnt);
        }
        isr.close();

        System.out.println("Implicants ordered by bits:");

        String currentTerm = "";

        TreeMap<Integer, String> terms1Level = new TreeMap<>();

        for(Map.Entry<Integer, List<QuineStruct>> qs : quineOrderedByBits.entrySet()) {
            System.out.printf(" Bits : %d\n", qs.getKey());
            currentTerm = qs.getValue().get(0).yyy;
            for(int i = 1 ; i < qs.getValue().size() ; i++) {
                String term = qs.getValue().get(i).yyy;
                System.out.printf("       : %s\n", term);
                currentTerm = unionTerm(currentTerm, term);
            }
            System.out.printf("       : -------------------------------\n");
            System.out.printf("       : %s\n", currentTerm);
            terms1Level.put(qs.getKey(), currentTerm);
        }

        SortedMap<Integer, String> subset = terms1Level.subMap(3, 5);

        System.out.println(subset);
    }

    static String fillBy(String orig, char filler) {
        StringBuilder sb = new StringBuilder(orig.length());
        for(int i=0;i<orig.length();i++) sb.append(filler);
        return sb.toString();
    }

    static String unionTerm(String t1, String t2) {
        Validate.isTrue(t1.length() == t2.length());
        StringBuilder sb = new StringBuilder(t1.length());
        for(int i = 0 ; i < t1.length(); i++) {
            if (t1.charAt(i) == t2.charAt(i)) {
                sb.append(t1.charAt(i));
            } else {
                sb.append("*");
            }
        }
        return sb.toString();
    }

}

