package mayton.elastic.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Function;
import mayton.elastic.Fb2Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fb2DocumentJSonConverter implements Function<Fb2Document, String> {

    static Logger logger = LoggerFactory.getLogger(Fb2DocumentJSonConverter.class);



    @Override

    public String apply(Fb2Document fb2Document) {

        ObjectMapper mapper = new ObjectMapper();

        try {

            return mapper.writeValueAsString(fb2Document);

        } catch (JsonProcessingException e) {

            logger.error("JsonProcessingException during convert",e);

        }

        return "{}";

    }
}
