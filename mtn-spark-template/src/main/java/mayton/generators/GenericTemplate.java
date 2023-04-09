package mayton.generators;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.replace;

public abstract class GenericTemplate {

    protected String packageName;
    protected String appName;
    protected String mainClass;

    static Logger logger = LoggerFactory.getLogger("generic-template");

    protected Options opt;

    protected CommandLine cli;

    protected VelocityContext context = new VelocityContext();

    protected void afterCliParsedActions() {
        packageName = cli.getOptionValue("company-name");
        appName     = cli.getOptionValue("app-name");
        mainClass   = cli.getOptionValue("main-class");
    }


    public static String trimDomain(String domain) {
        Validate.notNull(domain);
        int dot = domain.lastIndexOf('.');
        if (dot >= 0)
            return domain.substring(0, dot);
        else {
            return "";
        }
    }

    public static String domainToPath(String domain) {
        Validate.notNull(domain);
        return domain.replace('.','/');
    }

    protected GenericTemplate() throws ParseException {
        opt = new Options();
        opt.addRequiredOption("a", "app-name", true, "External application name");
        opt.addRequiredOption("c", "company-name", true, "Company (package) name");
        opt.addRequiredOption("m", "main-class", true, "Main class name");
    }

    private Optional<Template> safeGetTemplate(VelocityEngine velocityEngine, String templatePath) {
        try {
            Template template = velocityEngine.getTemplate(templatePath);
            return Optional.of(template);
        } catch (Exception ex) {
            logger.warn("Cannot find template {}", templatePath);
            return Optional.empty();
        }
    }

    private VelocityEngine initVelocityEngine(boolean templatesInJar) {
        VelocityEngine velocityEngine = new VelocityEngine();
        Properties properties = new Properties();
        properties.put("resource.loaders", "jar");
        properties.put("resource.loader.jar.class", "org.apache.velocity.runtime.resource.loader.JarResourceLoader");
        properties.put("resource.loader.jar.path", "jar:file:java-template.jar");
        velocityEngine.init(properties);
        return velocityEngine;
    }

    private void goVelocity() {
        logger.info("start velocity");
        VelocityEngine velocityEngine = initVelocityEngine(true);
        String destRoot = appName;
        Validate.notNull(cli, "Command line object must be initialized");
        velocityTemplates(cli).stream().forEach(pair -> {
            String dest = destRoot + '/' + pair.getRight();
            new File(getDir(dest)).mkdirs();
            try(Writer writer = new FileWriter(dest)) {
                String currentDomain = resourceDomain();
                Optional<Template> template = Optional.empty();
                while(template.isEmpty() && !trimDomain(currentDomain).equals("")) {
                    String templatePath = domainToPath(currentDomain) + '/' + pair.getLeft();
                    logger.info("templatePath = '{}', currentDomain = '{}'", templatePath, currentDomain);
                    template = safeGetTemplate(velocityEngine, templatePath);
                    currentDomain = trimDomain(currentDomain);
                }
                Validate.isTrue(template.isPresent(), "Velocity template must be initialized!");
                template.get().merge(velocityContext(cli), writer);
            } catch (IOException ex) {
                logger.error("IOException", ex);
            }
        });
    }

    public void go() {
        logger.info("start");
        goVelocity();
        logger.info("finished");
    }



    public abstract String resourceDomain();

    public abstract List<Pair<String,String>> velocityTemplates(CommandLine cli);

    public String appNameToClassName(String app) {
        return capitalize(
                replace(replace(app,"-",""), "_",""));
    }

    public String getDir(String path) {
        int i = path.lastIndexOf('/');
        if (i >= 0) {
            return path.substring(0, i);
        } else {
            return path;
        }
    }

    public abstract String usageHelp();

    public abstract VelocityContext velocityContext(CommandLine cli);

    public void printHelp() {
        new HelpFormatter().printHelp(usageHelp(), opt);
    }

    public CommandLine parseCommandLine(String[] args) throws ParseException {
        Validate.notNull(opt, "Command line strings must not be null");
        return new DefaultParser().parse(opt, args);
    }




}
