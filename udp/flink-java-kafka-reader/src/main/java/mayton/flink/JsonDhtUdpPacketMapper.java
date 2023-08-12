package mayton.flink;

import mayton.network.dht.DhtUdpPacket;
import org.apache.flink.api.common.accumulators.IntCounter;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.util.jackson.JacksonMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class JsonDhtUdpPacketMapper extends RichMapFunction<String, DhtFinalEntity> implements Serializable {

    private ObjectMapper objectMapper;

    static Logger logger = LoggerFactory.getLogger(JsonDhtUdpPacketMapper.class);

    private IntCounter dhtAll = new IntCounter();
    private IntCounter dhtUnknown = new IntCounter();

    @Override
    public void open(Configuration parameters) throws Exception {
        objectMapper = JacksonMapperFactory.createObjectMapper();
        getRuntimeContext().addAccumulator("dht_all", dhtAll);
        getRuntimeContext().addAccumulator("dht_unknown", dhtUnknown);
    }

    @Override
    public DhtFinalEntity map(String dhtUdpPacketJson) throws Exception {
        dhtAll.add(1);
        try {
            DhtUdpPacket retval = objectMapper.readValue(dhtUdpPacketJson, DhtUdpPacket.class);
            return new DhtFinalEntity(retval);
        } catch (Exception ex) {
            dhtUnknown.add(1);
            logger.error("Exception {}", ex.getMessage());
            return DhtFinalEntity.emptyEntity();
        }
    }

}
