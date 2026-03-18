package mayton;

import com.googlecode.concurrenttrees.common.PrettyPrinter;
import com.googlecode.concurrenttrees.radix.ConcurrentRadixTree;
import com.googlecode.concurrenttrees.radix.RadixTree;
import com.googlecode.concurrenttrees.radix.node.Node;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharSequenceNodeFactory;
import com.googlecode.concurrenttrees.radix.node.util.PrettyPrintable;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.Iterator;
import java.util.stream.IntStream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DemoRadix {

    static Logger logger = LoggerFactory.getLogger("DemoRadix");

    public static Options buildOptions() {
        Options options = new Options();
        options.addRequiredOption("i", "input-file", true, "Input file");
        options.addRequiredOption("o", "out-file", true, "Input file");
        return options;
    }

    static CommandLine parseCommandLine(String[] args) throws ParseException {
        return new DefaultParser().parse(buildOptions(), args);
    }

    public static String fill(int n) {
        StringBuilder sb = new StringBuilder(n);
        IntStream.range(0, n).forEach(x -> sb.append('-'));
        return sb.toString();
    }

    public static void prettyPrint(PrintWriter pw, Node node, int level) {
        //pw.print(fill(level));
        pw.printf("(%s", node.getIncomingEdge());
        for(Node child : node.getOutgoingEdges()) {
            prettyPrint(pw, child, level + 1);
        }
        pw.printf(")");
    }

    public static void main(String[] args) throws ParseException, IOException {
        CSVParser parser = CSVParser.parse(
                new FileReader("c:/db/tpb/only-names/part-00000-f5ba7ec2-fcae-410d-b2aa-f9a6a194ee03-c000.csv"),
                CSVFormat
                    .newFormat(',')
                    .withQuote('"')
                    .withEscape('\\')
        );
        PrintWriter pw = new PrintWriter("out.txt", UTF_8);
        Iterator<CSVRecord> res = parser.iterator();
        ConcurrentRadixTree<String> radixTree = new ConcurrentRadixTree(new DefaultCharSequenceNodeFactory());
        while(res.hasNext()) {
            CSVRecord rec = res.next();
            String name = rec.get(2);
            //System.out.println(name);
            radixTree.putIfAbsent(name,"");
        }
        parser.close();

        Iterator<CharSequence> keys = radixTree.getClosestKeys("Apache").iterator();
        while(keys.hasNext()) {
            System.out.println(keys.next());
        }

        //PrettyPrintable pp = (PrettyPrintable) radixTree;
        //Node node = pp.getNode();

        //prettyPrint(pw, node, 0);

        DummyAppendable dummyAppendable = new DummyAppendable();
        PrettyPrinter.prettyPrint(radixTree, dummyAppendable);

        System.out.println("Done. Size on disk estimate:");
        System.out.println("Lines      : " + dummyAppendable.lineCount);
        System.out.println("Characters : " + dummyAppendable.charCount);

        // 71 067 393
    }
}
