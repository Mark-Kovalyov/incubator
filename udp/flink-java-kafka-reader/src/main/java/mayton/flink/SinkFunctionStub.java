package mayton.flink;

import mayton.network.dht.DhtUdpPacket;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

public class SinkFunctionStub implements SinkFunction<DhtFinalEntity> {

    @Override
    public void invoke(DhtFinalEntity finalEntity, Context context) throws Exception {

    }
}
