package mayton.flink.udp;

import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UdpJob {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //env.registerType(UdpPacket.class);
        env.registerType(TypeInformation.of(UdpPacket.class).getClass());

        List<UdpPacket> udpPacketList = new ArrayList<>();

        Iterator<UdpPacket> i = udpPacketList.listIterator();

        TypeInformation<UdpPacket> udpInfo = TypeInformation.of(UdpPacket.class);

        TypeInformation<Tuple2<String, Double>> info = TypeInformation.of(new TypeHint<Tuple2<String, Double>>(){});

        DataStream<UdpPacket> transactions = env
                .addSource(new UdpSource(i))
                .name("transactions");

        transactions
                .addSink(new UdpSync())
                .name("send-alerts");

        env.execute("Fraud Detection");
    }

}
