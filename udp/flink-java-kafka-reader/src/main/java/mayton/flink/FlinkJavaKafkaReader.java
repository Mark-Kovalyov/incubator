package mayton.flink;

import mayton.network.dht.DhtUdpPacket;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;

import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.evictors.CountEvictor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.streaming.util.keys.KeySelectorUtil;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Types;


public class FlinkJavaKafkaReader {

	static Logger logger = LoggerFactory.getLogger("flink-java-kafka-reader");

	static String BROKERS = "localhost:9092";
	static String TOPIC   = "udp";

	static String JDBC_SQLITE        = "jdbc:sqlite:/mnt/c/db/sqlite/flink-java-kafka-reader.db";
	static String DRIVER_NAME_SQLITE = "org.sqlite.JDBC";

	static String TEMP = System.getProperty("java.io.tmpdir");

	public static void initSQL(String jdbcUrl, String driver) {

	}

	public static void main(String[] args) throws Exception {

		initSQL(JDBC_SQLITE, DRIVER_NAME_SQLITE);

		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		logger.info(":: checkpoint interval : {}",env.getCheckpointConfig().getCheckpointInterval());

		KafkaSource<String> source = KafkaSource.<String>builder()
				.setBootstrapServers(BROKERS)
				.setTopics(TOPIC)
				.setStartingOffsets(OffsetsInitializer.latest())
				.setDeserializer(KafkaRecordDeserializationSchema.valueOnly(StringDeserializer.class))
				.setClientIdPrefix("Kafka")
				.build();

		DataStreamSource<String> dataStreamSource = env.fromSource(
				source,
				WatermarkStrategy.noWatermarks(),
				"udp datasource");

		DataStream<DhtFinalEntity> entityDataStream = dataStreamSource.map(new JsonDhtUdpPacketMapper())
				.name("JSON to DHT converter");

/*		SingleOutputStreamOperator<DhtFinalEntity> operator = entityDataStream
				.keyBy("ip")
				.window(SlidingEventTimeWindows.of(Time.hours(24), Time.minutes(15)))
				.apply()*/
				//.<windowed transformation>(<window function>);

		DataStream<DhtFinalEntity> dnsEnriched = entityDataStream.map(new RevDnsEnricher())
				.name("Rev DNS enricher");

		DataStream<DhtFinalEntity> dhtEventDecoded = dnsEnriched.map(new DhtEventDecoder())
						.name("DHT event decode");

		dhtEventDecoded.addSink(
			JdbcSink.sink(
					"insert into dht_messages(ts,ip,source_port,tag,packet_size,packet_body,ptr,decoded_event) values(?,?,?,?,?,?,?,?)",
					(statement, finalentity) -> {
						DhtUdpPacket entity = finalentity.getPacket();
						statement.setString(1, entity.getTs());
						statement.setString(2, entity.getIp());
						statement.setInt(3, entity.getSourcePort());
						statement.setString(4, entity.getTag());
						statement.setInt(5, entity.getPacketSize());
						statement.setString(6, entity.getPacketBody());
						statement.setString(7, finalentity.getPtr());
						statement.setString(8, finalentity.getDhtDecodedJson());

					},
					JdbcExecutionOptions.builder().withBatchIntervalMs(3000).build(),
					new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
							.withUrl(JDBC_SQLITE)
							.withDriverName(DRIVER_NAME_SQLITE)
							.withUsername("sa")
							.build())
		).name("SQLite");

		env.execute("flink-java-kafka-reader-10");
	}
}
