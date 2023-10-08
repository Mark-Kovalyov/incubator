package mayton.elastic.jaxb;

import mayton.elastic.Fb2Document;
import mayton.elastic.converters.XmlToFb2DomConverter;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class XmlToFb2DocumentConverterTest {

    @Test
    public void testParse() throws IOException {
        String path = System.getProperty("user.dir") + "/src/test/resources/110119.xml";
        XmlToFb2DomConverter converter = new XmlToFb2DomConverter();
        Fb2Document document = converter.apply(new FileInputStream(path));
        assertNotNull(document);
        // TODO
    }

}
