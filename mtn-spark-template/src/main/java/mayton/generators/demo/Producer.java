package mayton.generators.demo;

import org.apache.commons.cli.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class Producer {

    public static Logger logger = LoggerFactory.getLogger("");

    public static void main(String[] args) throws ParseException, IOException {
        process();
    }

    private static void process() throws IOException {
        logger.info("Start");

        Properties properties = new Properties();
        properties.load(new FileInputStream("app.properties"));


        String topic = properties.getProperty("topic");
        int begin = 100;
        int size  = 200;

        try(KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            Random r = new Random();
            ProducerRecord<String, String> kafkaRecord;
            producer.initTransactions();
            try {
                for (int i = begin; i < begin + size; i++) {
                    UUID key = UUID.randomUUID();
                    int value = (int) (30 * r.nextGaussian());
                    int partition = Math.abs(value % 4);
                    kafkaRecord = new ProducerRecord(
                            topic,
                            partition,
                            System.currentTimeMillis(),
                            key,
                            value);
                    producer.send(kafkaRecord);
                    if (logger.isInfoEnabled()) {
                        logger.info("Send message with topic = {}, partition = {}, timestamp = {}, key = {}, msg = {}",
                                kafkaRecord.topic(),
                                kafkaRecord.partition(),
                                kafkaRecord.timestamp(),
                                kafkaRecord.key(),
                                kafkaRecord.value());
                    }
                }
                producer.commitTransaction();
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
