package mayton.elastic;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class Fb2Serializer extends StdSerializer<Fb2Document> {

    protected Fb2Serializer(Class<Fb2Document> t) {
        super(t);
    }

    @Override
    public void serialize(Fb2Document fb2Document, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        // TODO: Generalize constants
        jsonGenerator.writeStringField("author", fb2Document.getAuthor());
        jsonGenerator.writeStringField("title", fb2Document.getTitle());
        jsonGenerator.writeStringField("body", fb2Document.getBody());
        jsonGenerator.writeEndObject();
    }
}
