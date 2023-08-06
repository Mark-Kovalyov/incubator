package mayton.flink;

import mayton.network.dht.SparkSupport;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.MetricGroup;

import java.io.Serializable;
import java.util.Optional;

public class DhtEventDecoder extends RichMapFunction<DhtFinalEntity, DhtFinalEntity> implements Serializable {

    private transient Counter counter;
    private transient Counter errcounter;

    @Override
    public void open(Configuration parameters) throws Exception {
        MetricGroup mg = getRuntimeContext().getMetricGroup().addGroup("mtn");
        errcounter = mg.counter("ed_err");
        counter    = mg.counter("ed_ok");
    }

    @Override
    public DhtFinalEntity map(DhtFinalEntity dhtFinalEntity) throws Exception {
        Optional<String> optJson = SparkSupport.dumpDhtMessageToPlainJson(dhtFinalEntity.getPacket().getPacketBody());
        if (optJson.isPresent()) {
            counter.inc();
            dhtFinalEntity.setDhtDecodedJson(optJson.get());
        } else {
            errcounter.inc();
        }
        return dhtFinalEntity;
    }
}
