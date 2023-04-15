package mayton.generators.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.*;


public class XmlReaderDemo {

    static Logger logger = LoggerFactory.getLogger("xml-reader-demo");

    public static void main(String[] args) throws IOException, XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        InputStream is = new FileInputStream(args[0]);
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(is);
        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            logger.info("event = {}", nextEvent.toString());
        }
        reader.close();
    }

}
