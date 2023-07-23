package mayton.flink.cassandra;

import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.mapping.Mapper;
import mayton.flink.FraudDetector;
import mayton.flink.UDPRecord;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.connector.source.Source;
import org.apache.flink.connector.cassandra.source.CassandraSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.cassandra.ClusterBuilder;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;
import org.apache.flink.walkthrough.common.sink.AlertSink;
import org.apache.flink.walkthrough.common.source.TransactionSource;
import org.apache.kafka.common.Cluster;

import java.net.InetSocketAddress;
import java.net.SocketOptions;

public class CassandraListenUdpJob {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        ClusterBuilder clusterBuilder = new ClusterBuilder() {
            @Override
            protected com.datastax.driver.core.Cluster buildCluster(com.datastax.driver.core.Cluster.Builder builder) {
                return builder.addContactPointsWithPorts(new InetSocketAddress("localhost", 5555)).build();
            }
        };

        long maxSplitMemorySize = 64;

/*        Source cassandraSource = new CassandraSource(clusterBuilder,
                maxSplitMemorySize,
                UDPRecord.class,
                "select ... from KEYSPACE.TABLE ...;",
                () -> new Mapper.Option[] {Mapper.Option.saveNullFields(true)});*/

        //DataStream<UDPRecord> stream = env.fromSource(cassandraSource, WatermarkStrategy.noWatermarks(),  "CassandraSource");


        env.execute("Cassandra Listen UDP");
    }

}
