package mayton.bigdata;

import mayton.network.dht.DhtFunctions;
import mayton.network.dht.MagnetLink;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.profiler.Profiler;
import org.slf4j.profiler.TimeInstrument;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class MagnetXmlIndexer {

    static Logger logger = LoggerFactory.getLogger(MagnetXmlIndexer.class);

    public static void processFile(File file, XMLStreamWriter w) throws IOException, XMLStreamException {
        w.writeStartElement("file");
        w.writeAttribute("name", file.getName());
        w.writeAttribute("size", String.valueOf(file.length()));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(file.lastModified()),
                ZoneOffset.UTC
        );
        w.writeAttribute("lastModified", localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));
        byte[] blob = IOUtils.toByteArray(new FileInputStream(file.getAbsolutePath()));
        try {
            MagnetLink.Builder magnetLink = DhtFunctions.generateMagnetLinksPack(blob);
            magnetLink.withDisplayName(file.getName());
            w.writeAttribute("magnet", magnetLink.build().toString());
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }
        w.writeEndElement();
    }

    static int processFiles = 0;
    static int ignoredFiles = 0;

    public static boolean isBookExtenstion(String filename) {
        Pattern book = Pattern.compile(".+\\.(pdf|djvu|ps|dvi|fb2|chm)$", Pattern.CASE_INSENSITIVE);
        return book.matcher(filename).matches();
    }

    public static void proceeFolder(File folder, XMLStreamWriter w) throws IOException, XMLStreamException {
        File[] nodes = folder.listFiles();
        if (nodes == null) return;
        for(File node : nodes) {
            if (node.isDirectory()) {
                String dirName = node.getName();
                w.writeStartElement("dir");
                w.writeAttribute("name", dirName);
                proceeFolder(node, w);
                w.writeEndElement();
            }
        }
        for(File node : nodes) {
            if (!node.isDirectory()) {
                if (isBookExtenstion(node.getName())) {
                    processFile(node, w);
                    processFiles++;
                } else {
                    ignoredFiles++;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, XMLStreamException {
        logger.info("start");
        Profiler profiler = new Profiler("magnet-xml-indexer");
        XMLOutputFactory factory = XMLOutputFactory.newFactory();
        LocalDateTime localDateTime = LocalDateTime.now();
        String timeTag = localDateTime
                .format(DateTimeFormatter.ISO_DATE_TIME)
                .replace(':', '-')
                .replace('T', '-');
        XMLStreamWriter w = null;
        if (args.length >= 2) {
            w = factory.createXMLStreamWriter(
                    new BufferedOutputStream(new FileOutputStream(args[1] + timeTag + ".xml"),
                            64 * 1024), "utf-8");
        } else {
            w = factory.createXMLStreamWriter(System.out);
        }
        profiler.start("process folder books");
        w.writeStartDocument();
        w.writeDTD(
                "<!DOCTYPE magnetxmlindex\n" +
                "[\n" +
                "<!ELEMENT dir (dir+|file+)>\n" +
                "<!ELEMENT file ANY >\n" +
                "<!ATTLIST file size CDATA #IMPLIED>\n" +
                "<!ATTLIST file name CDATA #IMPLIED>\n" +
                "<!ATTLIST file magnet CDATA #IMPLIED>\n" +
                "<!ATTLIST file lastModified CDATA #IMPLIED>\n" +
                "]>\n");
        w.writeStartElement("magnetxmlindex");
        proceeFolder(new File(args[0]), w);
        w.writeEndElement();
        w.writeEndDocument();
        w.close();
        TimeInstrument time = profiler.stop();
        logger.info(time.toString());
        logger.info("Proceed files : {}", processFiles);
        logger.info("Ignored files : {}", ignoredFiles);
        logger.info("finish");
    }

}
