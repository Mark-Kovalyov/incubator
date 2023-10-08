package mayton.elastic.jaxb;

import static org.junit.Assert.assertEquals;

import mayton.elastic.Fb2Document;
import mayton.elastic.converters.Fb2DocumentJSonConverter;
import org.junit.Test;

public class Fb2DocumentJSonConverterTest {

    @Test
    public void assume_that_simple_documents_has_been_coverted_correctly() {

        Fb2DocumentJSonConverter jSonConverter = new Fb2DocumentJSonConverter();

        Fb2Document document = new Fb2Document("movies/Epic/2", "S.Lukyanenko", "Dozor", "Long time ago...");

        assertEquals("{\"path\":\"movies/Epic/2\",\"author\":\"S.Lukyanenko\",\"title\":\"Dozor\"," +
                        "\"body\":\"Long time ago...\"}",
                     jSonConverter.apply(document));
    }

}
