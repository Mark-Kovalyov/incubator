package mayton.kafka;

import io.confluent.kafka.serializers.KafkaJsonSerializer;
import mayton.network.NetworkUtils;
import org.apache.commons.cli.*;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Properties;
import java.util.Random;

public class UdpProducer {

    public static Logger logger = LoggerFactory.getLogger(UdpProducer.class);

    private static void main(String[] args) {
        logger.info("Start");



        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer",   "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "io.confluent.kafka.serializers.KafkaJsonSerializer");
        properties.put("client.id", "clientid1");
        properties.put("acks",    "0");
        properties.put("retries", "0");

        try(KafkaProducer<String, UDPRecord> producer = new KafkaProducer<>(properties)) {
            Random r = new Random();
            ProducerRecord<String, UDPRecord> kafkaRecord;
            try {
                while(true) {
                    Instant instant = Instant.now();
                    UDPRecord udpRecord = new UDPRecord(
                            instant.toString(),
                            NetworkUtils.formatIpV4(r.nextInt(Integer.MAX_VALUE)),
                            r.nextInt(65535),
                            "tr",
                            1,
                            String.format("%08X",r.nextLong()));
                    kafkaRecord = new ProducerRecord(
                            "flood",
                            udpRecord
                    );
                    producer.send(kafkaRecord);
                    logger.info("sent : {}", udpRecord);
                    Thread.sleep(15000 + r.nextInt(5000));
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
