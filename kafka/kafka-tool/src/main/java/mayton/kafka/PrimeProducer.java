package mayton.kafka;

import org.apache.commons.cli.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Random;

public class PrimeProducer {

    public static Logger logger = LoggerFactory.getLogger("prime-producer");

    public static Options createOptions() {
        return new Options()
                .addRequiredOption("b", "bootstrap-servers", true, "bootstrap servers (Comma separated)")
                .addRequiredOption("t", "topic", true, "topic");
    }

    static boolean isPrime(int n) {
        int mx = (int) Math.sqrt(n);
        for (int i = 2; i <= (mx + 1); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar prime-producer.jar", createOptions(), true);
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

    public static void process(CommandLine commandLine) {
        logger.info("Start");

        Properties properties = new Properties();
        properties.put("bootstrap.servers", commandLine.getOptionValue("bootstrap-servers"));
        properties.put("key.serializer",   StringSerializer.class);
        properties.put("value.serializer", StringSerializer.class);
        properties.put("acks",    "1");
        properties.put("retries", "0");
        Random r = new Random();
        String topic = commandLine.getOptionValue("topic");
        try(KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            ProducerRecord<String, String> kafkaRecord;
            try {
                for(int i = 3; i < Integer.MAX_VALUE;i+=2) {
                    if (isPrime(i)) {
                        kafkaRecord = new ProducerRecord(
                                topic,
                                String.valueOf(i),
                                String.format("HEX: %08X", i)
                        );
                        RecordMetadata res = producer.send(kafkaRecord).get();
                        logger.info("sent : key = {} with offset = {}, topic = {}, value = {}", i, res.offset(), res.topic(), kafkaRecord.value());
                        Thread.sleep(1500 + r.nextInt(500));
                    }
                }
            } catch (ProducerFencedException ex) {
                producer.close();
            } catch (KafkaException ex) {
                producer.abortTransaction();
            }

        } catch (Exception ex) {
            logger.error("Producer::process error", ex);
        }



        logger.info("Finish");
    }

}
