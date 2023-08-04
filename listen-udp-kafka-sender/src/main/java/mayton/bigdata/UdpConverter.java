package mayton.bigdata;

import mayton.network.NetworkUtils;
import mayton.network.dht.DhtUdpPacket;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class UdpConverter {

    static Random r = new Random();

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

        //CSVParser parser = CSVParser.parse(is, StandardCharsets.UTF_8, CSVFormat.newFormat(';'));

        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrap);
        properties.put("key.serializer",   "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "io.confluent.kafka.serializers.KafkaJsonSerializer");
        properties.put("acks",             acks);
        properties.put("retries",          "0");

        try(KafkaProducer<String, DhtUdpPacket> producer = new KafkaProducer<>(properties)) {
            ProducerRecord<String, DhtUdpPacket> kafkaRecord;
            //Iterator<CSVRecord> it = parser.stream().iterator();
            try {
                while(true) {
                    String ts      = LocalDateTime.now().toString();
                    String ip      = randomIp();
                    String port    = "5555";
                    String tag     = "tr";
                    String length  = "1";
                    String udpBody = "FF";
                    DhtUdpPacket udpRecord = new DhtUdpPacket(ts,ip,Integer.parseInt(port), tag, Integer.parseInt(length), udpBody);
                    String key = tag + "/" + ts;
                    kafkaRecord = new ProducerRecord(
                            topic,
                            key,
                            udpRecord
                    );
                    RecordMetadata res = producer.send(kafkaRecord).get();
                    logger.info("body : {}, topic : {}, offset : {}, part : {}, timestamp : {}",
                            udpRecord,
                            res.topic(),
                            res.offset(),
                            res.partition(),
                            res.timestamp());
                    Thread.sleep(max(2000L + (int)(1000 * r.nextGaussian()), 0));
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

    private static String gaussRandomIp() {
        long rand = Integer.MAX_VALUE + (int) (r.nextGaussian() * 100.0);
        return NetworkUtils.formatIpV4(min(max(rand,0), 0xFFFF_FFFFL));
    }

    private static String randomIp() {
        return NetworkUtils.formatIpV4(Math.abs(r.nextInt()));
    }

}
