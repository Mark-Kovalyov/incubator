package mayton.generators.demo;

import org.apache.commons.cli.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

public class DemoConsumer {

    public static Logger logger = LoggerFactory.getLogger("demo-consumer");

    public static void main(String[] args) throws ParseException, IOException {
        process();
    }

    private static void process() throws IOException {
        logger.info("Start");

        Properties properties = new Properties();
        properties.load(new FileInputStream("app.properties"));

        KafkaConsumer<UUID, Integer> consumer = new KafkaConsumer<>(properties);

        String topic = properties.getProperty("topic");

        consumer.subscribe(Arrays.asList(topic));

        boolean isWorking = true;
        int cnt = 0;
        try {
            while (isWorking) {
                ConsumerRecords<UUID, Integer> records = consumer.poll(Duration.ofSeconds(3));
                int n = 0;
                for (ConsumerRecord<UUID, Integer> record : records) {
                    cnt++;
                    n++;
                    logger.trace("Received message topic = {}, partition = {}, key = {}, offset = {}, value = {}",
                            record.topic(),
                            record.partition(),
                            record.key(),
                            record.offset(),
                            record.value(),
                            cnt);
                    }
                }
                consumer.commitSync();
                logger.info("Commit");
        } catch (Exception ex) {
            logger.error("Exc: ", ex);
            consumer.close();
        }

    }


}

