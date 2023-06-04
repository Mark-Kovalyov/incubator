package mayton.generators;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.velocity.VelocityContext;

import java.io.*;
import java.util.*;

public class SparkTemplate extends GenericTemplate {

    public SparkTemplate(String[] args) throws ParseException {
        super();
        opt.addOption("ml", "machine-learning", false, "Support for Machine Learning");
        if (args.length == 0) {
            printHelp();
            System.exit(1);
        } else {
            cli = parseCommandLine(args);
            afterCliParsedActions();
        }
    }

    /**
     * This is follows COC (Convention Over Configuration)
     * See: https://en.wikipedia.org/wiki/Convention_over_configuration
     * @return
     */
    @Override
    public String resourceDomain() {
        return "spark";
    }

    @Override
    public List<Pair<String, String>> velocityTemplates(CommandLine cli) {
        return Arrays
                .asList(
                        Pair.of("build.sbt.vm",        "build.sbt"),
                        Pair.of("Makefile.vm",         "Makefile"),
                        Pair.of("Main.scala.vm",       "src/main/scala/" + domainToPath(packageName) + "/" + mainClass + ".scala"),
                        Pair.of("build.properties.vm", "project/build.properties"),
                        Pair.of("README.md.vm",        "README.md")
                );
    }

    @Override
    public String usageHelp() {
        return "java -jar spark-template-*.jar";
    }


    @Override
    public VelocityContext velocityContext(CommandLine cli) {
        VelocityContext context = new VelocityContext();
        context.put("scalaMajor",   "2.12");
        context.put("scalaMinor", "17");
        context.put("version", "1.0");
        context.put("sparkVersion", "3.3.2");
        context.put("version",    "1.0");
        context.put("dbutilsApiVersion", "0.0.6");
        context.put("sbtVersion", "1.8.2");
        context.put("appName", appName);
        context.put("organization", packageName);
        context.put("packageName", packageName);
        context.put("mainClass", mainClass);
        context.put("scalaParserCombinatorsVer", "1.1.2");
        return context;
    }

    public static void main(String[] args) throws IOException, ParseException {
        new SparkTemplate(args).generate();
    }

}
