package mayton.flink;

import mayton.flink.dummy.DummyRichMapFunction;
import mayton.flink.external.CachedDnsEnricher;
import mayton.flink.external.GeoEnricher;
import mayton.lib.Uniconf;
import mayton.network.dht.DhtUdpPacket;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.runtime.state.StateBackend;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.graph.StreamGraph;
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.evictors.CountEvictor;

import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.streaming.util.keys.KeySelectorUtil;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.flink.api.common.time.Time;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FlinkJavaKafkaReader {

	static Logger logger = LoggerFactory.getLogger(FlinkJavaKafkaReader.class);

	public static void initSQL(String jdbcUrl, String driver) throws SQLException {
		Connection connection = DriverManager.getConnection(jdbcUrl);
		Statement st = connection.createStatement();

		connection.close();
	}

	public static void main(String[] args) throws Exception {

		Uniconf uniconf = new Uniconf();

		String BROKERS = "localhost:9092";
		String TOPIC   = "udp";
		String JDBC_URL            = uniconf.lookupProperty("flinkJavaKafkaReader.jdbcUrl",	   "jdbc:postgresql://localhost:5432/postgres");
		String DRIVER_NAME         = uniconf.lookupProperty("flinkJavaKafkaReader.driverName", "org.postgresql.Driver");
		String UNAME               = uniconf.lookupProperty("flinkJavaKafkaReader.name",       "scott");
		String PWD                 = uniconf.lookupProperty("flinkJavaKafkaReader.pwd",        "tiger");

		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

/*		env.setRestartStrategy(RestartStrategies.exponentialDelayRestart(
				Time.seconds(3),
				Time.hours(1),
				2.0,
				Time.hours(1),
				1.0));*/

		CheckpointConfig checkpointConfig = env.getCheckpointConfig();

		logger.warn(":: checkpoint interval : {} ms", checkpointConfig.getCheckpointInterval());
		logger.warn(":: checkpoint mode     : {}", checkpointConfig.getCheckpointingMode());
		logger.warn(":: checkpoint storage  : {}", checkpointConfig.getCheckpointStorage());

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


		DataStream<DhtFinalEntity> dnsEnriched = entityDataStream.map(new CachedDnsEnricher())
				.name("Rev Rocks DB-DNS enricher");

/*		DataStream<DhtFinalEntity> geoEnriched = dnsEnriched.map(new GeoEnricher())
				.name("Geo enricher");*/

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
						if (DRIVER_NAME.equals("org.postgresql.Driver")) {
							PGobject jsonObject = new PGobject();
							jsonObject.setType("json");
							jsonObject.setValue(finalentity.getDhtDecodedJson());
							statement.setObject(8, jsonObject);
						} else {
							statement.setString(8, finalentity.getDhtDecodedJson());
						}
					},
					JdbcExecutionOptions.builder().withBatchIntervalMs(3000).build(),
					new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
							.withUrl(JDBC_URL)
							.withDriverName(DRIVER_NAME)
							.withUsername(UNAME)
							.withPassword(PWD)
							.build())
		).name("Postgres");

		LocalDateTime localDateTime = LocalDateTime.now();

		env.execute("flink-java-kafka-reader-" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm")));
	}
}
