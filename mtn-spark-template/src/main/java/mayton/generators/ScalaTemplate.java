package mayton.generators;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ScalaTemplate extends GenericTemplate {

    static Logger logger = LoggerFactory.getLogger("scala-template");

    public ScalaTemplate() throws ParseException {
        super();
    }

    @Override
    public void generate(String[] args) throws ParseException {
        opt.addOption("z",  "zio",          false, "Support ZIO");
        opt.addOption("ca", "cats",         false, "Support cats effects");
        if (args.length == 0) {
            printHelp(opt);
            System.exit(1);
        } else {
            logger.info("packageName : {}, appName : {}, mainClass : {}", packageName, appName, mainClass);
        }
    }

    @Override
    public String resourceDomain() {
        if (cli.hasOption("z")) {
            return "scala.zio";
        } else {
            return "scala";
        }
    }

    @Override
    public List<Pair<String, String>> velocityTemplates(CommandLine cli) {
        return Arrays.asList(
                Pair.of("Main.scala.vm",       "src/main/scala/" + domainToPath(packageName) + "/" + mainClass + ".scala"),
                Pair.of("build.sbt.vm",        "build.sbt"),
                Pair.of("Makefile.vm",         "Makefile"),
                Pair.of("build.properties.vm", "project/build.properties"),
                Pair.of("README.md.vm",        "README.md")
        );
    }

    @Override
    public String usageHelp() {
        return "Scala template generator 1.2\n" +
                "Usage : java -jar scala-template-*.jar";
    }

    @Override
    public VelocityContext velocityContext(CommandLine cli) {
        VelocityContext context = new VelocityContext();
        context.put("scalaMajor",   "3");
        context.put("scalaMinor",   "2.2");
        context.put("version",      "1.0");
        context.put("sbtVersion",   "1.8.2");
        context.put("appName",      appName);
        context.put("organization", packageName);
        context.put("packageName",  packageName);
        context.put("mainClass",    mainClass);
        if (cli.hasOption("z"))     context.put("z", Boolean.TRUE);
        if (cli.hasOption("ca"))    context.put("ca", Boolean.TRUE);
        return context;
    }

    /*public static void main(String[] args) throws IOException, ParseException {
        if (IS_DEBUG) {
            String[] a2 = {"-a", "zio-cats-demo", "-c", "mayton.scala", "-m", "ZioCatsDemo", "-z"};
            new ScalaTemplate(a2).generate();
        } else {
            new ScalaTemplate(args).generate();
        }
        logger.info("Done");
    }*/

}
