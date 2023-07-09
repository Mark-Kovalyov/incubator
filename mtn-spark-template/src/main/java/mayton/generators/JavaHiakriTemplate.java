package mayton.generators;

import mayton.generators.demo.JavaHikaricpJdbcApp;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.velocity.VelocityContext;

import java.util.Arrays;
import java.util.List;

public class JavaHiakriTemplate extends GenericTemplate {

    protected JavaHiakriTemplate() throws ParseException {
    }

    /*protected JavaHiakriTemplate(String[] args) throws ParseException {
        super();
        opt.addOption("s", "slf4j",          false, "SLF4j");
        opt.addOption("e", "slf4jext",       false, "SLF4j extensions");
        opt.addOption("j", "jdbc-driver",    false, "JDBC driver");
        opt.addOption("jo", "oracle-driver", false, "JDBC driver for Oracle");
        opt.addOption("jp", "postgres-driver", false, "JDBC driver for PG");
        opt.addOption("jm", "maria-driver",    false, "JDBC driver for MariaDb");
        if (args.length == 0) {
            new HelpFormatter().printHelp(usageHelp(), opt);
            System.exit(1);
        } else {
            cli = parseCommandLine(args);
            afterCliParsedActions();
            logger.info("packageName : {}, appName : {}, mainClass : {}", packageName, appName, mainClass);
        }
    }*/

    @Override
    public String resourceDomain() {
        return "java.jdbc.hikari";
    }

    @Override
    public List<Pair<String, String>> velocityTemplates(CommandLine cli) {
        return Arrays.asList(
                Pair.of("Main.java.vm",   "src/main/java/" + domainToPath(packageName) + "/" + mainClass + ".java")
        );
    }

    @Override
    public String usageHelp() {
        return "Java template generator 1.0\n" +
                "Usage : java -jar java-jdbc-hikari-template-*.jar";
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

/*    public static void main(String[] args) throws Exception {
        new JavaHiakriTemplate(args).go();
    }*/

}
