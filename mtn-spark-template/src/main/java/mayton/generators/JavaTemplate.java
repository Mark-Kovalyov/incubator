package mayton.generators;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JavaTemplate extends GenericTemplate {

    static Logger logger = LoggerFactory.getLogger("java-template");

    protected JavaTemplate() throws ParseException {
    }

    @Override
    public void generate(String[] args) throws ParseException {
        opt.addOption("s", "slf4j",          false, "SLF4j");
        opt.addOption("e", "slf4jext",       false, "SLF4j extensions");
        if (args.length == 0) {
            printHelp(opt);
            System.exit(1);
        } else {
            logger.info("packageName : {}, appName : {}, mainClass : {}", packageName, appName, mainClass);
        }
    }

    @Override
    public String resourceDomain() {
        return "java";
    }

    @Override
    public List<Pair<String, String>> velocityTemplates(CommandLine cli) {
        return Arrays.asList(
                Pair.of("logback.xml.vm", "src/main/resources/logback.xml"),
                Pair.of("pom.xml.vm",     "pom.xml"),
                Pair.of("Main.java.vm",   "src/main/java/" + domainToPath(packageName) + "/" + mainClass + ".java")
        );
    }

    @Override
    public String usageHelp() {
        return "Java template generator 1.1\n" +
               "Usage : java -jar java-template-*.jar";
    }

    @Override
    public VelocityContext velocityContext(CommandLine cli) {
        context.put("javaVersion",   "11");
        context.put("appName", appName);
        context.put("organization", packageName);
        context.put("packageName", packageName);
        context.put("mainClass", mainClass);
        context.put("jarName", appName);
        context.put("usageHelp", usageHelp());
        if (cli.hasOption("slf4j"))          context.put("slf4j", Boolean.TRUE);
        if (cli.hasOption("slf4jext"))       context.put("slf4jext",       Boolean.TRUE);
        return context;
    }


}
