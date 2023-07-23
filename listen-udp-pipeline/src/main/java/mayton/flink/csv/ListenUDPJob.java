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
import org.apache.flink.streaming.connectors.kafka.partitioner.FlinkFixedPartitioner;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;
import org.apache.flink.walkthrough.common.sink.AlertSink;
import org.apache.flink.walkthrough.common.source.TransactionSource;

public class ListenUDPJob {

    public static void main(String[] args) throws Exception {

        String dataDir = "/bigdata/udp/udp-tr-51313";

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        //Define the text input format based on the directory
        TextInputFormat auditFormat = new TextInputFormat(new Path(dataDir));


        DataStream<String> udpStream = env.readFile(auditFormat,
                dataDir,
                FileProcessingMode.PROCESS_CONTINUOUSLY,
                15 * 1000L).name("CSV files souce");

        DataStream<UDPRecord> browserEventsObj = udpStream.map(
                (MapFunction<String, UDPRecord>) eventStr -> {
                    String[] columns = eventStr.split(";");
                    return new UDPRecord(columns[0], columns[1], Integer.parseInt(columns[2]), columns[3], Integer.parseInt(columns[4]), columns[5]);
                }
        ).name("CSV object mapper");

        //Window by 5 seconds, count #of records and save to output
/*        SingleOutputStreamOperator<Tuple2<String,Integer>> recCount
            = udpStream
                .map(row -> new Tuple2<String,Integer>(String.valueOf(System.currentTimeMillis()),1))
                .returns(Types.TUPLE(Types.STRING ,Types.INT))
                .timeWindowAll(Time.seconds(5))
                .reduce((x,y) ->  (new Tuple2<String, Integer>(x.f0, x.f1 + y.f1)))
                .name("count #of records");*/

        KafkaRecordSerializationSchema schema = KafkaRecordSerializationSchema.builder()
                .setTopicSelector((element) -> "udp-packets-from-tr")
                .setValueSerializationSchema(new SimpleStringSchema())
                .setKeySerializationSchema(new SimpleStringSchema())
                .setPartitioner(new FlinkFixedPartitioner())
                .build();


        KafkaSink<UDPRecord> sink = KafkaSink.<UDPRecord>builder()
                .setBootstrapServers("localhost:1111")
                .setRecordSerializer(schema)
                /*.setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic("udp-packets-from-tr")
                        .setValueSerializationSchema(new SimpleStringSchema())
                        .build()
                )*/
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();

        browserEventsObj.sinkTo(sink);



        env.execute("ListenUDP job");

    }

}
