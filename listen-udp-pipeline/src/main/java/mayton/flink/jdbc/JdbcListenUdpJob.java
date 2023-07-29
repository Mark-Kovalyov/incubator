package mayton.flink.jdbc;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class JdbcListenUdpJob {

    public static void main(String[] args) {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        /*JdbcSink.sink(
                "insert into dht.udps(id, title, authors, year) values (?, ?, ?, ?)",
                (statement, udp) -> {
                    //statement.setLong(1,   udp.ip);
                },
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                        .withUrl("jdbc:postgresql://dbhost:5432/postgresdb")
                        .withDriverName("org.postgresql.Driver")
                        .withUsername("someUser")
                        .withPassword("somePassword")
                        .build()
        ))*/;


    }

}
