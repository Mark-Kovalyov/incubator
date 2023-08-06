package mayton.flink;

import mayton.network.dht.DhtUdpPacket;
import org.apache.flink.api.common.accumulators.IntCounter;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.Meter;
import org.apache.flink.metrics.MeterView;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.util.jackson.JacksonMapperFactory;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Optional;

public class JsonDhtUdpPacketMapper extends RichMapFunction<String, DhtFinalEntity> implements Serializable {


    private ObjectMapper objectMapper;

    static Logger logger = LoggerFactory.getLogger("json-dht-pack-map");

    private IntCounter dhtDecoded = new IntCounter();
    private IntCounter dhtUnknown = new IntCounter();

    @Override
    public DhtFinalEntity map(String dhtUdpPacketJson) throws Exception {
        try {
            DhtUdpPacket retval = objectMapper.readValue(dhtUdpPacketJson, DhtUdpPacket.class);
            dhtDecoded.add(1);
            return new DhtFinalEntity(retval);
        } catch (Exception ex) {
            dhtUnknown.add(1);
            logger.error("Exception {}", ex.getMessage());
            return DhtFinalEntity.emptyEntity();
        }
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        objectMapper = JacksonMapperFactory.createObjectMapper();
        getRuntimeContext().addAccumulator("dht_decoded", dhtDecoded);
        getRuntimeContext().addAccumulator("dht_unknown", dhtUnknown);
    }
}
