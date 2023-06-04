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
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.replace;

public abstract class GenericTemplate {

    static final boolean IS_DEBUG = System.getProperty("isDebug") == null
            ? false
            : Boolean.valueOf(System.getProperty("isDebug"));

    protected String packageName;
    protected String appName;
    protected String mainClass;
    protected Options opt;
    protected VelocityContext context = new VelocityContext();

    static Logger logger = LoggerFactory.getLogger("generic-template");

    public static <T> List<Pair<T,T>> zip(List<T> a, List<T> b) {
        return Collections.emptyList();
    }

    public static <T> List<T> tail(List<T> a) {
        return a.subList(1, a.size());
    }

    public static <T> T[] toArray(List<T> a) {
        return (T[]) a.toArray();
    }

    public static String hyphenToCamel(String hyphenString) {
        if (hyphenString.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        boolean upper = true;
        for(char c : hyphenString.toLowerCase().toCharArray()) {
            if (c == '-') {
                upper = true;
            } else {
                if (upper) {
                    sb.append(Character.toUpperCase(c));
                    upper = false;
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    public static String hyphenToCamel2(String hyphenString) {
        switch (hyphenString) {
            case "spark-template" : return "SparkTemplate";
            case "java-template" : return "JavaTemplate";
            default: return "";
        }
        /**/
    }

    public String appNameToClassName(String app) {
        return capitalize(
                replace(replace(app,"-",""), "_",""));
    }

    public String getDir(String path) {
        int i = path.lastIndexOf('/');
        return i >= 0 ? path.substring(0, i) : path;
    }

    public static String trimDomain(String domain) {
        Validate.notNull(domain);
        int dot = domain.lastIndexOf('.');
        return dot >= 0 ? domain.substring(0, dot) : "";
    }

    public static String domainToPath(String domain) {
        Validate.notNull(domain);
        return domain.replace('.','/');
    }

    protected GenericTemplate() throws ParseException {

    }

    public void prepareOptions(String[] args) {
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

    private VelocityEngine initVelocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        Properties properties = new Properties();
        if (IS_DEBUG) {
            properties.put("resource.loaders",           "file");
            properties.put("resource.loader.file.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
            properties.put("resource.loader.file.path",  "src/main/resources/");
        } else {
            properties.put("resource.loaders",           "jar");
            properties.put("resource.loader.jar.class",  "org.apache.velocity.runtime.resource.loader.JarResourceLoader");
            properties.put("resource.loader.jar.path",   "jar:file:java-template.jar");
        }
        velocityEngine.init(properties);
        return velocityEngine;
    }

    public static <T> List<T> allButLast(List<T> objects) {
        return objects.isEmpty() ? objects : objects.subList(0, objects.size() - 1);
    }

    public void generate(String[] args) throws ParseException {

        logger.info("Parse command line. Add default options");

        CommandLine cli = parseCommandLine(args, opt);

        packageName = cli.getOptionValue("company-name");
        appName     = cli.getOptionValue("app-name");
        mainClass   = cli.getOptionValue("main-class");

        logger.info("start velocity");
        VelocityEngine velocityEngine = initVelocityEngine();
        String destRoot = appName;
        Validate.notNull(cli, "Command line object must be initialized");
        String currentDomain = resourceDomain();
        logger.info("currentDomain = {}", currentDomain);

        velocityTemplates(cli).stream().forEach(pair -> {
            List<String> domainItems = Arrays.asList(currentDomain.split(Pattern.quote(".")));
            Optional<Template> template = Optional.empty();
            while(!domainItems.isEmpty()) {
                String templatePath = domainItems.stream().collect(Collectors.joining("/")) + '/' + pair.getLeft();
                logger.info("templatePath = {}", templatePath);
                template = safeGetTemplate(velocityEngine, templatePath);
                if (template.isPresent()) {
                    break;
                }
                domainItems = allButLast(domainItems);
            }
            if (template.isPresent()) {
                String dest = destRoot + '/' + pair.getRight();
                logger.info("Processing pair: '{}', '{}'", pair.getLeft(), pair.getRight());
                new File(getDir(dest)).mkdirs();
                try (Writer writer = new FileWriter(dest)) {
                    template.get().merge(velocityContext(cli), writer);
                } catch (IOException ex) {
                    logger.warn("IOException", ex);
                }
            }
        });
    }

    public abstract String resourceDomain();

    public abstract List<Pair<String,String>> velocityTemplates(CommandLine cli);

    public abstract String usageHelp();

    public abstract VelocityContext velocityContext(CommandLine cli);

    public void printHelp(Options opt) {
        new HelpFormatter().printHelp(usageHelp(), opt);
    }

    public CommandLine parseCommandLine(String[] args, Options opt) throws ParseException {
        Validate.notNull(opt, "Command line strings must not be null");
        return new DefaultParser().parse(opt, args);
    }




}
