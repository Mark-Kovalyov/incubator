package mayton.flink.csv;

import mayton.flink.UDPRecord;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.io.GenericCsvInputFormat;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.connector.sink.Sink;
import org.apache.flink.api.java.io.CsvInputFormat;
import org.apache.flink.api.java.io.RowCsvInputFormat;
import org.apache.flink.api.java.io.TextInputFormat;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.core.fs.Path;
import org.apache.flink.formats.csv.CsvReaderFormat;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.FileProcessingMode;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.cassandra.CassandraSink;
import org.apache.flink.streaming.connectors.kafka.partitioner.FlinkFixedPartitioner;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;
import org.apache.flink.walkthrough.common.sink.AlertSink;
import org.apache.flink.walkthrough.common.source.TransactionSource;

public class ListenUDPJob {

    public static void main(String[] args) throws Exception {



        String dataDir = "$UDP_HOME";

        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();

        //Define the text input format based on the directory
        TextInputFormat udpFiles = new TextInputFormat(new Path(dataDir));

        DataStream<String> udpStream = env.readFile(
                udpFiles,
                "file://" + dataDir,
                FileProcessingMode.PROCESS_CONTINUOUSLY,
                15 * 1000L).name("CSV files souce");

        DataStream<UDPRecord> udpRecords = udpStream.map(
                (MapFunction<String, UDPRecord>) eventStr -> {
                    String[] columns = eventStr.split(";");
                    return new UDPRecord(
                            columns[0],
                            columns[1],
                            Integer.parseInt(columns[2]),
                            columns[3],
                            Integer.parseInt(columns[4]),
                            columns[5]);
                }
        ).name("CSV object mapper");



        CassandraSink<UDPRecord> cassandraSink = CassandraSink.addSink(udpRecords)
                .setQuery("INSERT INTO dht.udp(ts, ip, source_port, tag, packet_size, packet_body) values (?, ?, ?, ?, ?, ?)")
                .setHost("127.0.0.1")
                .build();

        env.execute("ListenUDP job");

    }

}
