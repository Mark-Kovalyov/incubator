package mayton.bigdata;

import mayton.lib.SofarTracker;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.profiler.Profiler;
import org.slf4j.profiler.TimeInstrument;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class CSVIndexer extends GenericIndexer {

    static Logger logger = LoggerFactory.getLogger(CSVIndexer.class);

    public CSVIndexer(long sofarSize) {
        sofarTracker = SofarTracker.createUnitLikeTracker("file", sofarSize);
    }

    public void processFile(File file, CSVPrinter printer, String prefix) throws IOException {
        MagnetEntity magnetEntity = new MagnetEntity();
        magnetEntity.name = file.getName();
        magnetEntity.size = file.length();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(file.lastModified()),
                ZoneOffset.UTC
        );
        magnetEntity.lastModified = localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
        byte[] blob = IOUtils.toByteArray(new FileInputStream(file.getAbsolutePath()));
        magnetEntity.md4 = mayton.libs.encoders.Hashes.md4(blob);
        magnetEntity.md5 = mayton.libs.encoders.Hashes.md5(blob);
        magnetEntity.sha1 = mayton.libs.encoders.Hashes.sha1(blob);
        magnetEntity.treeTiger = mayton.libs.encoders.DhtFunctions.treeTiger(blob).get();
        magnetEntity.path = file.getPath().substring(prefix.length());

        printer.printRecord(
                magnetEntity.name,
                magnetEntity.size,
                magnetEntity.lastModified,
                magnetEntity.md4,
                magnetEntity.md5,
                magnetEntity.sha1,
                magnetEntity.treeTiger,
                magnetEntity.path
        );
    }

    public void procesFolder(File folder, CSVPrinter printer, String prefix) throws IOException {
        File[] nodes = folder.listFiles();
        if (nodes == null) return;
        for(File node : nodes) {
            if (node.isDirectory()) {
                procesFolder(node, printer, prefix);
            }
        }
        for(File node : nodes) {
            if (!node.isDirectory()) {
                if (isBookExtenstion(node.getName())) {
                    processFile(node, printer, prefix);
                    processFiles++;
                    sofarTracker.update(processFiles);
                    logger.info(sofarTracker.toString());
                } else {
                    ignoredFiles++;
                }
            }
        }
    }

    public void go(CSVPrinter printer, String folder, String prefix) throws IOException {
        procesFolder(new File(folder), printer, prefix);
    }

    public static void main(String[] args) throws IOException {
        logger.info("start");
        if (args.length == 0) {
            System.out.print("Usage: java -jar magnet-indexer.jar [source-folder] [dest-file.csv] [sofar]");
            System.exit(1);
        }
        String folder = args[0];
        String dest   = args[1];
        String sofar  = args[2];
        Profiler profiler = new Profiler("magnet-csv-indexer");

        LocalDateTime localDateTime = LocalDateTime.now();
        String timeTag = localDateTime
                .format(DateTimeFormatter.ISO_DATE_TIME)
                .replace(':', '-')
                .replace('T', '-');

        profiler.start("process folder " + folder);

        CSVPrinter printer = new CSVPrinter(new FileWriter(dest + timeTag + ".csv"),
                CSVFormat.Builder.create()
                        .setHeader("name", "size", "lastModified", "md5", "sha1", "md4", "treeTiger", "path")
                        .setDelimiter(';')
                        .setQuote('\"')
                        .setEscape('\\')
                        .build());

        CSVIndexer indexer = new CSVIndexer(Long.parseLong(sofar));
        indexer.go(printer, folder, folder);
        printer.close();

        TimeInstrument time = profiler.stop();

        logger.info(time.toString());
        logger.info("Proceed files : {}", indexer.processFiles);
        logger.info("Ignored files : {}", indexer.ignoredFiles);
        logger.info("finish");
    }
}
