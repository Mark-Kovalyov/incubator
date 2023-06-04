package mayton.generators.kafka;

import mayton.generators.GenericTemplate;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.velocity.VelocityContext;

import java.util.Arrays;
import java.util.List;

public class JavaKafkaProducerTemplate extends GenericTemplate {

    public JavaKafkaProducerTemplate(String[] args) throws ParseException {
        super();
        opt.addOption("s", "slf4j",             false, "SLF4j");
        opt.addOption("t", "topic",             true, "Topic Name");
        opt.addOption("cl", "client-id",        true, "Client ID");
        opt.addOption("ks", "key-serializer",   true, "Key Serializer");
        opt.addOption("vs", "value-serializer", true, "Value Serializer");
        opt.addOption("pr", "proto",            false, "Support Google Protobuf");
        opt.addOption("av", "avro",             false, "Support Apache Avro");
        opt.addOption("th", "thrift",           false, "Support Apache Thrift");
        if (args.length == 0) {
            new HelpFormatter().printHelp(usageHelp(), opt);
            System.exit(1);
        } else {
            cli = parseCommandLine(args);
            afterCliParsedActions();
            context.put("javaVersion", "11");
            context.put("appName", appName);
            context.put("organization", packageName);
            context.put("packageName", packageName);
            context.put("mainClass", mainClass);
            context.put("jarName", appName);
            context.put("usageHelp", usageHelp());
            context.put("parts", "4");

            context.put("kafkaVersion", "3.4.0");
            context.put("protobufVersion", "3.22.2");
            context.put("avroVersion", "1.11.1");
            context.put("thriftVersion", "0.18.1");
            context.put("entityName", "Emp");

            Arrays.asList("proto", "avro", "slf4j", "thrift").forEach(key -> {
                if (cli.hasOption(key)) context.put(key, Boolean.TRUE);
            });

        }
    }

    @Override
    public String resourceDomain() {
        return "java.kafka.producer";
    }

    @Override
    public List<Pair<String, String>> velocityTemplates(CommandLine cli) {
        List<Pair<String, String>> pairs = Arrays.asList(
                Pair.of("Producer.java.vm",    "src/main/java/" + domainToPath(packageName) + "/" + mainClass + ".java"),
                Pair.of("app.properties.vm",   "app.properties"),
                Pair.of("pom.xml.vm",          "pom.xml"),
                Pair.of("README.md.vm",        "README.md"),
                Pair.of("Makefile.vm",         "Makefile")
        );
        if (cli.hasOption("avro")) {
            pairs.add(Pair.of("emp.avsc.vm", "avro/emp.avsc"));
        }
        if (cli.hasOption("proto")) {
            pairs.add(Pair.of("emp.proto.vm", "prorobuf/emp.proto"));
        }
        if (cli.hasOption("thrift")) {
            pairs.add(Pair.of("emp.thrift.vm", "thrift/emp.thrift"));
        }
        return pairs;
    }

    @Override
    public String usageHelp() {
        return "Java Kafka Producer template generator 1.0\n" +
               "Usage : java -jar java-kafka-producer-template-*.jar";
    }

    @Override
    public VelocityContext velocityContext(CommandLine cli) {
        return context;
    }

    public static void main(String[] args) throws Exception {
        new JavaKafkaProducerTemplate(args).generate();
    }

}
