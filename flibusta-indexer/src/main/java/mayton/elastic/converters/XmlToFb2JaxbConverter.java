package mayton.elastic.converters;

import java.io.InputStream;
import java.util.function.Function;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import mayton.elastic.Fb2Document;
import mayton.elastic.jaxb.FictionBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlToFb2JaxbConverter implements Function<InputStream, FictionBook> {

    static Logger logger = LoggerFactory.getLogger(XmlToFb2JaxbConverter.class);

    @Override
    public FictionBook apply(InputStream inputStream) {

        try {
            JAXBContext context = JAXBContext.newInstance(Object.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FictionBook fictionBook = (FictionBook) unmarshaller.unmarshal(inputStream);
            return fictionBook;
        } catch (JAXBException ex) {
            logger.error("JAXBException : ", ex);
        }

        return null;
    }

}
