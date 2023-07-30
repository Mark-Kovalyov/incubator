package mayton.bigdata;

import mayton.network.dht.DhtUdpPacket;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Properties;

public class UdpConverter {

    public static Logger logger = LoggerFactory.getLogger(UdpConverter.class);

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage : java -jar udp-kafka-sender [boostrap-servers] [topic] [acks]");
            System.exit(1);
        }
        String bootstrap = args[0];
        String topic     = args[1];
        String acks      = args[2];
        logger.info("Start");
        InputStream is = System.in;

        CSVParser parser = CSVParser.parse(is, StandardCharsets.UTF_8, CSVFormat.newFormat(';'));

        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrap);
        properties.put("key.serializer",   "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "io.confluent.kafka.serializers.KafkaJsonSerializer");
        properties.put("acks",             acks);
        properties.put("retries",          "0");

        try(KafkaProducer<String, DhtUdpPacket> producer = new KafkaProducer<>(properties)) {
            ProducerRecord<String, DhtUdpPacket> kafkaRecord;
            Iterator<CSVRecord> it = parser.stream().iterator();
            try {
                while(it.hasNext()) {
                    CSVRecord rec  = it.next();
                    String ts      = rec.get(0);
                    String ip      = rec.get(1);
                    String port    = rec.get(2);
                    String tag     = rec.get(3);
                    String length  = rec.get(4);
                    String udpBody = rec.get(5);
                    DhtUdpPacket udpRecord = new DhtUdpPacket(ts,ip,Integer.parseInt(port), tag, Integer.parseInt(length), udpBody);
                    String key = tag + "/" + ts;
                    kafkaRecord = new ProducerRecord(
                            topic,
                            key,
                            udpRecord
                    );
                    RecordMetadata res = producer.send(kafkaRecord).get();
                    logger.info("sent : {}", udpRecord);
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
