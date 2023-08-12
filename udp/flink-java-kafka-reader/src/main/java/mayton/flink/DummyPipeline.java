package mayton.flink;

import mayton.flink.dummy.DummyRichMapFunction;
import mayton.flink.dummy.DummySinkFunction;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DummyPipeline {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        String BROKERS = "localhost:9092";
        String TOPIC   = "udp";

        // TODO: Replace kafka with dummy source
        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers(BROKERS)
                .setTopics(TOPIC)
                .setStartingOffsets(OffsetsInitializer.latest())
                .setDeserializer(KafkaRecordDeserializationSchema.valueOnly(StringDeserializer.class))
                .setClientIdPrefix("DummyPipeline")
                .build();

        DataStreamSource<String> dataStreamSource = env.fromSource(
                source,
                WatermarkStrategy.noWatermarks(),
                "dummy kafka datasource");

        DataStream<String> func = dataStreamSource
                .map(new DummyRichMapFunction<String>())
                .name("dummy rich map func");

        func.addSink(new DummySinkFunction())
                .name("dummy sink");

        LocalDateTime localDateTime = LocalDateTime.now();

        env.execute("dummy-" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm")));
    }    
    
}
