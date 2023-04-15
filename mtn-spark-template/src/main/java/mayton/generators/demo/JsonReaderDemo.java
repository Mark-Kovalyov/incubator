package mayton.generators.demo;

import org.jsfr.json.compiler.JsonPathCompiler;
import org.jsfr.json.GsonParser;
import org.jsfr.json.JsonSurfer;
import org.jsfr.json.provider.GsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;


public class JsonReaderDemo {

    static Logger logger = LoggerFactory.getLogger("json-reader-demo");

    static void process(String sourceJsonFile, String jsonPathQuery) throws FileNotFoundException {
        InputStream is = new FileInputStream(sourceJsonFile);
        JsonSurfer surfer = new JsonSurfer(GsonParser.INSTANCE, GsonProvider.INSTANCE);
        Iterator<Object> iterator = surfer.iterator(is, JsonPathCompiler.compile(jsonPathQuery)) ;
        while (iterator.hasNext()) {
            Object object = iterator.next();
            logger.info("next : {}", object);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        process(args[0],args[1]);
    }

}
