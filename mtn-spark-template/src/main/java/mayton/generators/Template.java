package mayton.generators;

import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;

import static mayton.generators.GenericTemplate.tail;
import static mayton.generators.GenericTemplate.toArray;

public class Template {

    static Logger logger = LoggerFactory.getLogger("template");

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, InvocationTargetException, ParseException {
        if (args.length == 0) {
            logger.error("Please peek template name!");
            System.exit(1);
        } else if (args.length == 1) {
            logger.error("Please peek advanced parameters for certian template!");
            System.exit(1);
        } else {
            String templateName = "mayton.generators." + GenericTemplate.hyphenToCamel(args[0]);
            logger.info("templateName = {}", templateName);
            Class clazz = Class.forName(templateName);
            logger.info("initialized class = {}", clazz);
            GenericTemplate template = (GenericTemplate)clazz.newInstance();
            logger.info("instance created");
            template.prepareOptions(toArray(tail(Arrays.asList(args))));
            template.generate(toArray(tail(Arrays.asList(args)))); // multiple times
            logger.info("OK");
        }
    }

}
