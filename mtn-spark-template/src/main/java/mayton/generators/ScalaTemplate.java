package mayton.generators;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.velocity.VelocityContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ScalaTemplate extends GenericTemplate {

    protected ScalaTemplate(String[] args) throws ParseException {
        super();
        opt.addOption("pbt", "property-based-testing", false, "Support for Scala PBT libraries");
        cli = parseCommandLine(args);
    }

    @Override
    public String resourceDomain() {
        return "scala";
    }

    @Override
    public List<Pair<String, String>> velocityTemplates(CommandLine cli) {
        return Arrays.asList(
                Pair.of("build.sbt.vm",        "build.sbt"),
                Pair.of("Makefile.vm",         "Makefile"),
                Pair.of("Main.scala.vm",       "src/main/scala/" + domainToPath(packageName) + "/" + mainClass + ".scala"),
                Pair.of("build.properties.vm", "project/build.properties"),
                Pair.of("README.md.vm",        "README.md")
        );
    }

    @Override
    public String usageHelp() {
        return "java -jar scala-template-*.jar";
    }

    @Override
    public VelocityContext velocityContext(CommandLine cli) {
        VelocityContext context = new VelocityContext();
        context.put("scalaMajor",   "2.12");
        context.put("scalaMinor", "17");
        context.put("version", "1.0");
        context.put("sbtVersion", "1.8.2");
        context.put("appName", appName);
        context.put("organization", packageName);
        context.put("packageName", packageName);
        context.put("mainClass", mainClass);
        return context;
    }

    public static void main(String[] args) throws IOException, ParseException {
        new SparkTemplate(args).go();
    }

}
