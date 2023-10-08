package mayton.elastic;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

public class Fb2Deserializer extends StdDeserializer<Fb2Document> {

    protected Fb2Deserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Fb2Document deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
                                                                                     JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        // TODO:

        return new Fb2Document();
    }
}
