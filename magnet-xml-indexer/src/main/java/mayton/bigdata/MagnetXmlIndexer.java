package mayton.bigdata;

import mayton.libs.encoders.DhtFunctions;
import mayton.libs.encoders.Hashes;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.profiler.Profiler;
import org.slf4j.profiler.TimeInstrument;

import javax.print.DocFlavor;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class MagnetXmlIndexer extends GenericIndexer {

    static Logger logger = LoggerFactory.getLogger(MagnetXmlIndexer.class);

    public void processFile(File file, XMLStreamWriter w) throws IOException, XMLStreamException {
        w.writeStartElement("file");
        w.writeAttribute("name", file.getName());
        w.writeAttribute("size", String.valueOf(file.length()));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(file.lastModified()),
                ZoneOffset.UTC
        );
        w.writeAttribute("lastModified", localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));
        byte[] blob = IOUtils.toByteArray(new FileInputStream(file.getAbsolutePath()));
        w.writeAttribute("md5",  Hashes.md5(blob));
        w.writeAttribute("sha1", Hashes.sha1(blob));
        w.writeAttribute("md4",  Hashes.md4(blob));
        w.writeAttribute("tree-tiger", DhtFunctions.treeTiger(blob).get());
        w.writeEndElement();
    }

    public void proceeFolder(File folder, XMLStreamWriter w) throws IOException, XMLStreamException {
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

    public void main(String[] args) throws IOException, XMLStreamException {
        logger.info("start");
        String folder = args[0];
        String dest = args[1];
        Profiler profiler = new Profiler("magnet-xml-indexer");
        XMLOutputFactory factory = XMLOutputFactory.newFactory();
        LocalDateTime localDateTime = LocalDateTime.now();
        String timeTag = localDateTime
                .format(DateTimeFormatter.ISO_DATE_TIME)
                .replace(':', '-')
                .replace('T', '-');
        XMLStreamWriter w = factory.createXMLStreamWriter(new BufferedOutputStream(new FileOutputStream(dest + timeTag + ".xml"), 64 * 1024), "utf-8");



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
                "<!ATTLIST file sha1 CDATA #IMPLIED>\n" +
                "<!ATTLIST file lastModified CDATA #IMPLIED>\n" +
                "]>\n");
        w.writeComment("");
        w.writeStartElement("magnetxmlindex");
        proceeFolder(new File(folder), w);
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
