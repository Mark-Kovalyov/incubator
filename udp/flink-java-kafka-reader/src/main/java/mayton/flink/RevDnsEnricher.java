package mayton.flink;

import mayton.network.dns.SimpleDnsClient;
import org.apache.flink.api.common.accumulators.IntCounter;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.Serializable;
import java.util.Optional;

public class RevDnsEnricher extends RichMapFunction<DhtFinalEntity, DhtFinalEntity> implements Serializable {

    private SimpleDnsClient simpleDnsClient;

    static Logger logger = LoggerFactory.getLogger("dns-resolver");

    private IntCounter resolved = new IntCounter();
    private IntCounter unresolved = new IntCounter();

    @Override
    public void open(Configuration parameters) throws Exception {
        getRuntimeContext().addAccumulator("resolved", resolved);
        getRuntimeContext().addAccumulator("unresolved", unresolved);
        simpleDnsClient = new SimpleDnsClient("8.8.8.8", 53, 15);
    }

    @Override
    public DhtFinalEntity map(DhtFinalEntity dhtFinalEntity) throws Exception {

        Optional<String> res = simpleDnsClient.resolvePtr(dhtFinalEntity.getPacket().getIp());
        if (res.isPresent()) {
            resolved.add(1);
            dhtFinalEntity.setPtr(res.get());
        } else {
            unresolved.add(1);
            logger.warn("Unable to resolve {}", dhtFinalEntity.getPacket().getIp());
        }
        return dhtFinalEntity;
    }
}
