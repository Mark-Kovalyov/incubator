package mayton.flink;

import mayton.network.dns.RocksDbDnsClient;
import org.apache.flink.api.common.accumulators.IntCounter;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.metrics.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Optional;

public class RocksDbDnsEnricher extends RichMapFunction<DhtFinalEntity, DhtFinalEntity> implements Serializable {

    private RocksDbDnsClient rocksDbDnsClient;

    static Logger logger = LoggerFactory.getLogger("rocks-dns-enricher");
    private IntCounter all = new IntCounter();
    private IntCounter unresolved = new IntCounter();

    private transient long lag;

    private transient long cumLag;

    @Override
    public void open(Configuration parameters) throws Exception {
        long threadId = Thread.currentThread().getId();
        String path = "/tmp/rocks-db-dns-enricher/" + threadId;

        getRuntimeContext().addAccumulator("all", all);
        getRuntimeContext().addAccumulator("unresolved", unresolved);

        getRuntimeContext().getMetricGroup().gauge("lag", (Gauge<Long>) () -> lag);
        getRuntimeContext().getMetricGroup().gauge("avg_lag", (Gauge<Long>) () -> cumLag / all.getLocalValue());
        rocksDbDnsClient = new RocksDbDnsClient(path);
        logger.info("Created rocksDbDnsClient with persistence {}", path);
    }

    @Override
    public DhtFinalEntity map(DhtFinalEntity dhtFinalEntity) throws Exception {
        long t1 = System.currentTimeMillis();
        Optional<String> res = rocksDbDnsClient.resolvePtr(dhtFinalEntity.getPacket().getIp());
        long t2 = System.currentTimeMillis();
        lag = t2 - t1;
        cumLag += lag;
        all.add(1);
        if (res.isPresent()) {
            dhtFinalEntity.setPtr(res.get());
        } else {
            unresolved.add(1);
            logger.warn("Unable to resolve {}", dhtFinalEntity.getPacket().getIp());
        }
        return dhtFinalEntity;
    }

    @Override
    public void close() throws Exception {
        rocksDbDnsClient.close();
    }
}
