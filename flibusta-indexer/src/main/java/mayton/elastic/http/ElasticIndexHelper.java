package mayton.elastic.http;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ElasticIndexHelper {

    static JsonNodeFactory factory;
    static JsonFactory jsonFactory;

    static {
        factory = new JsonNodeFactory(false);
        jsonFactory = new JsonFactory();
    }

    public static String buildRussianIndex() {
        //JsonGenerator generator;
        //Writer writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        try {
            //generator = jsonFactory.createGenerator(writer);
            ObjectNode russianStop = factory.objectNode();
                russianStop.put("type","stop");
                russianStop.put("stopwords","_russian_");

            ObjectNode russianStemmer = factory.objectNode();
                russianStemmer.put("type","stemmer");
                russianStemmer.put("language","russian");

            ObjectNode settings = factory.objectNode();

            ObjectNode filterMap = factory.objectNode();

            Map<String, ObjectNode> map = new HashMap() {{
                put("russian_stop",    russianStop);
                put("russian_stemmer", russianStemmer);
            }};

            filterMap.setAll(map);

            ObjectNode filter = factory.objectNode();

            filter.set("filter", filterMap);

            ObjectNode analysis = factory.objectNode();

            analysis.set("analysis",filter);

            settings.set("settings", analysis);

            //mapper.writeTree(generator, settings);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(settings);

        } catch (IOException e) {

        }
        return "{}";
    }

    public static String buildEnglishIndex() {
        return "{\n" +
                "  \"settings\": {\n" +
                "    \"analysis\": {\n" +
                "      \"filter\": {\n" +
                "        \"english_stop\": {\n" +
                "          \"type\":       \"stop\",\n" +
                "          \"stopwords\":  \"_english_\" \n" +
                "        },\n" +
                "        \"english_keywords\": {\n" +
                "          \"type\":       \"keyword_marker\",\n" +
                "          \"keywords\":   [\"example\"] \n" +
                "        },\n" +
                "        \"english_stemmer\": {\n" +
                "          \"type\":       \"stemmer\",\n" +
                "          \"language\":   \"english\"\n" +
                "        },\n" +
                "        \"english_possessive_stemmer\": {\n" +
                "          \"type\":       \"stemmer\",\n" +
                "          \"language\":   \"possessive_english\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"analyzer\": {\n" +
                "        \"rebuilt_english\": {\n" +
                "          \"tokenizer\":  \"standard\",\n" +
                "          \"filter\": [\n" +
                "            \"english_possessive_stemmer\",\n" +
                "            \"lowercase\",\n" +
                "            \"english_stop\",\n" +
                "            \"english_keywords\",\n" +
                "            \"english_stemmer\"\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

}
