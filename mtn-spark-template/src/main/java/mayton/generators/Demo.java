package mayton.generators;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Properties;

public class Demo {

    public static void main(String[] args) {
        VelocityEngine velocityEngine = new VelocityEngine();
        Properties properties = new Properties();
        properties.put("resource.loaders", "file");
        properties.put("resource.loader.file.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        properties.put("resource.loader.file.path",  "src/main/resources");
        velocityEngine.init(properties);
        Template template = velocityEngine.getTemplate("scala/build.sbt.vm");
        VelocityContext context = new VelocityContext();
        context.put("scalaMajor", "3");
        context.put("scalaMinor", "2.1");
        context.put("organization", "microsoft");
        context.put("name",    "Demo");
        context.put("version", "1.0");
        context.put("ca", true);
        context.put("z", true);
        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        System.out.println(sw);
    }

}
