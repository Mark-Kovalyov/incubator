package mayton.flink.kafka;

import mayton.flink.UDPRecord;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.partitioner.FlinkFixedPartitioner;

public class KafkaUDPJob {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        KafkaSource<String> source = KafkaSource.<String>builder()
                //.setBootstrapServers(brokers)
                .setTopics("listen-udp")
                //.setGroupId("my-group")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();

        env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");


        KafkaRecordSerializationSchema schema = KafkaRecordSerializationSchema.builder()
                .setTopicSelector((element) -> "udp-packets-from-tr")
                .setValueSerializationSchema(new SimpleStringSchema())
                .setKeySerializationSchema(new SimpleStringSchema())
                .setPartitioner(new FlinkFixedPartitioner())
                .build();


        KafkaSink<UDPRecord> sink = KafkaSink.<UDPRecord>builder()
                .setBootstrapServers("localhost:1111")
                .setRecordSerializer(schema)
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();


        env.execute("Kafka Listen-UDP job");

    }

}
