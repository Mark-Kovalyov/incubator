package mayton.flink;

import mayton.network.dht.SparkSupport;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

import java.io.Serializable;
import java.util.Optional;

public class DhtEventDecoder extends RichMapFunction<DhtFinalEntity, DhtFinalEntity> implements Serializable {

    @Override
    public void open(Configuration parameters) throws Exception {

    }

    @Override
    public DhtFinalEntity map(DhtFinalEntity value) throws Exception {
        Optional<String> optJson = SparkSupport.dumpDhtMessageToPlainJson(value.getPacket().getPacketBody());
        if (optJson.isPresent()) {
            value.setDhtDecodedJson(optJson.get());
        }
        return value;
    }
}
