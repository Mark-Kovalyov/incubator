package mayton.elastic.jaxb;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FictionBookTest {


    static Logger logger = LoggerFactory.getLogger(FictionBookTest.class);


    @Test
    public void testMarshallFictionBook() throws JAXBException {

        FictionBook fictionBook = new FictionBook();
        //fictionBook.setBody(new Body());
        //fictionBook.setDescription(new Description("titleInfo-1",  "documentInfo-1", "publishInfo-2", "custom-Info..."));

        JAXBContext jaxbContext = JAXBContext.newInstance(FictionBook.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(fictionBook, sw);

        logger.info(sw.getBuffer().toString());


    }

    @Ignore
    @Test
    public void testUnmarshallFictionBook() throws JAXBException {

        String currentDir = System.getProperty("user.dir");

        File file = new File(currentDir + "/src/test/resources/110119.xml");

        JAXBContext jaxbContext = JAXBContext.newInstance(FictionBook.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();


        FictionBook fictionBook = (FictionBook)jaxbUnmarshaller.unmarshal(file);

        assertNotNull(fictionBook);

            assertNotNull(fictionBook.getDescription());

            assertNotNull(fictionBook.getBody());



        logger.info(fictionBook.toString());

    }

}
