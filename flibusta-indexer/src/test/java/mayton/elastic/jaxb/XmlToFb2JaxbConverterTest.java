package mayton.elastic.jaxb;

import mayton.elastic.Fb2Document;
import mayton.elastic.converters.XmlToFb2JaxbConverter;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertNotNull;

public class XmlToFb2JaxbConverterTest {

    @Test
    public void test() throws FileNotFoundException {
        String path = System.getProperty("user.dir") + "/src/test/resources/367300.xml";
        XmlToFb2JaxbConverter converter = new XmlToFb2JaxbConverter();
        FictionBook fictionBook = converter.apply(new FileInputStream(path));
        assertNotNull(fictionBook);

    }

}
