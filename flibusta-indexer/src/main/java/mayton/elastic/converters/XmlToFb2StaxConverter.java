package mayton.elastic.converters;

import java.io.InputStream;
import java.util.function.Function;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import mayton.elastic.Fb2Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlToFb2StaxConverter implements Function<InputStream, Fb2Document> {

    static Logger logger = LoggerFactory.getLogger(XmlToFb2StaxConverter.class);

    @Override
    public Fb2Document apply(InputStream inputStream) {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader xmlEventReader = null;
        Fb2Document document = new Fb2Document();
        try {
            xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);
            while (xmlEventReader.hasNext()) {
                XMLEvent event = xmlEventReader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String localPart = startElement.getName().getLocalPart();
                    if (localPart.equals("first-name")) {
                        logger.info("::[1] first-name");
                        while(!event.isCharacters()){
                            event = xmlEventReader.nextEvent();
                        }
                        logger.info("event.asCharacters().getData() = '{}'", event.asCharacters().getData());
                        document.setAuthor(event.asCharacters().getData());
                    }
//                    logger.info(":: start element = {} ", startElement.getName().getLocalPart());
//                    Iterator iterator = startElement.getAttributes();
//                    while (iterator.hasNext()) {
//                        Attribute attribute = (Attribute) iterator.next();
//                        QName name = attribute.getName();
//                        String value = attribute.getValue();
//                        logger.info(":: Attribute {} = {}", name, value);
//                    }
                } else if (event.isEndElement()) {
//                    EndElement endElement = event.asEndElement();
//                    logger.info(":: start element = ", endElement.getName());
                }
            }
        } catch (XMLStreamException e) {
            logger.error("", e);
        }
        return document;
    }

}
