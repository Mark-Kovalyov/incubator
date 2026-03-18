package mayton;

import com.googlecode.concurrenttrees.common.PrettyPrinter;
import com.googlecode.concurrenttrees.radix.ConcurrentRadixTree;
import com.googlecode.concurrenttrees.radix.node.Node;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharSequenceNodeFactory;
import com.googlecode.concurrenttrees.radix.node.util.PrettyPrintable;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.Iterator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static mayton.Utils.printf;
import static mayton.Utils.println;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DemoRadix {

    static final boolean DEBUG = false;

    static Logger logger = LoggerFactory.getLogger("DemoRadix");

    public static void prettyPrint(PrintWriter pw, Node node, int level) {

        int n = node.getOutgoingEdges().size();
        CharSequence incomingEgde = node.getIncomingEdge();
        if (DEBUG) {
            printf("%s %d : %s\n",
                    " ".repeat(level * 2),
                    n,
                    node.getIncomingEdge());
        }

        if (n == 0) {
            pw.printf("%s", incomingEgde);
            for (Node child : node.getOutgoingEdges()) {
                prettyPrint(pw, child, level + 1);
            }
        } else {
            pw.print("(");
            pw.printf("%s", incomingEgde);
            for (Node child : node.getOutgoingEdges()) {
                prettyPrint(pw, child, level + 1);
            }
            pw.print(")");
        }
    }

    public static void main(String[] args) throws ParseException, IOException {
        CSVParser parser = CSVParser.parse(
                new FileReader("words.txt"),
                CSVFormat
                    .newFormat(',')
                    .withQuote('"')
                    .withEscape('\\')
        );
        PrintWriter pw = new PrintWriter("words-prefix-tree.txt", UTF_8);
        Iterator<CSVRecord> res = parser.iterator();
        ConcurrentRadixTree<String> radixTree = new ConcurrentRadixTree(new DefaultCharSequenceNodeFactory());
        while(res.hasNext()) {
            CSVRecord rec = res.next();
            String name = rec.get(0) + "$";
            radixTree.putIfAbsent(name,"");
        }
        parser.close();

        Iterator<CharSequence> keys = radixTree.getClosestKeys("Apache").iterator();
        while(keys.hasNext()) {
            println(keys.next());
        }

        PrettyPrintable pp = (PrettyPrintable) radixTree;
        Node node = pp.getNode();

        prettyPrint(pw, node, 0);

        LispLikeAppendable lispAppend = new LispLikeAppendable();
        PrettyPrinter.prettyPrint(radixTree, lispAppend);

        println("Done. Size on disk estimate:");
        println("InputSize: " + Utils.fileSize("words.txt"));

        println("Lines      : " + lispAppend.lineCount);
        println("Characters : " + lispAppend.charCount);

        println("Size: " + Utils.fileSize("out.txt"));

        pw.close();
        // 71 067 393
    }
}
