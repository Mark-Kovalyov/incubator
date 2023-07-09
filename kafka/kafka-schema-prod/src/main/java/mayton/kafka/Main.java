package mayton.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer","io.confluent.kafka.serializers.KafkaAvroSerializer");
        properties.put("value.serializer","io.confluent.kafka.serializers.KafkaAvroSerializer");
        properties.put("schema.registry.url","http://localhost:9090");

        Producer<String, Customer> producer = new KafkaProducer<>(properties);

        while(true) {
            Customer customer = Customer
                    .newBuilder()
                    .setCustomerId(10)
                    .setCustomerName("Nem")
                    .setFaxNumber("555-555")
                    .build();
            ProducerRecord<String, Customer> record = new ProducerRecord<>("customers", 1, "key1", customer);
            producer.send(record);
        }
    }
}