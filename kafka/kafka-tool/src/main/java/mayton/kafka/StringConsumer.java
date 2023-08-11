package mayton.kafka;

import org.apache.commons.cli.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class StringConsumer {

    public static Logger logger = LoggerFactory.getLogger(StringConsumer.class);

    public static Options createOptions() {
        return new Options()
                .addRequiredOption("b", "bootstrap-servers", true, "bootstrap servers (Comma separated)")
                .addRequiredOption("t", "topic", true, "topic")
                .addRequiredOption("c", "consumer-id", true, "consumer id")
                .addRequiredOption("g", "group-id", true, "group.id");
    }

    public static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar consumer.jar", createOptions(), true);
    }

    public static void main(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        Options options = createOptions();
        if (args.length == 0) {
            printHelp();
        } else {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("h")) {
                printHelp();
                return;
            }
            process(line);
        }
    }

    private static void process(CommandLine line) {
        logger.info("Start");
        Properties properties = new Properties();

        properties.put("bootstrap.servers",  line.getOptionValue("bootstrap-servers"));
        properties.put("key.deserializer",   StringDeserializer.class);
        properties.put("value.deserializer", StringDeserializer.class);

        //properties.put("enable.auto.commit", "false");
        //properties.put("auto.commit.interval.ms", "1000");

        properties.put("group.id", line.getOptionValue("group-id"));

        properties.put("consumer.id", line.getOptionValue("consumer-id"));

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        String topic = line.getOptionValue("topic");

        consumer.subscribe(Arrays.asList(topic));

        boolean isWorking = true;
        try {
            while (isWorking) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(3));
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("Polled : key = '{}', value = '{}', part = {}, off = {}",
                            record.key(),
                            record.value(),
                            record.partition(),
                            record.offset());
                }
            }
        } catch (Exception ex) {
            logger.error("Exc: ", ex);
            consumer.close();
        }

    }


}
