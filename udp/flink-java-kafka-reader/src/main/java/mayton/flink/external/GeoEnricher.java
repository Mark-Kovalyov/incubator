package mayton.flink.external;

import mayton.flink.DhtFinalEntity;
import mayton.ip2loc.DummyLocationService;
import mayton.ip2loc.Ipv4Loc;
import mayton.ip2loc.Ipv4LocationService;
import org.apache.flink.api.common.accumulators.IntCounter;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Optional;

public class GeoEnricher extends RichMapFunction<DhtFinalEntity, DhtFinalEntity> implements Serializable {

    static Logger logger = LoggerFactory.getLogger(GeoEnricher.class);

    /*private IntCounter all        = new IntCounter();
    private IntCounter unresolved = new IntCounter();
    private IntCounter ignored    = new IntCounter();
*/
    //private transient Ipv4LocationService ipv4LocationServiceRadix;

    @Override
    public void open(Configuration parameters) throws Exception {
        /*getRuntimeContext().addAccumulator("geo_all",        all);
        getRuntimeContext().addAccumulator("geo_unresolved", unresolved);
        getRuntimeContext().addAccumulator("geo_ignored",    ignored);*/
        //ipv4LocationServiceRadix = new Ipv4LocationServiceRadix("~/ip2location-lite-db3.csv");
        //ipv4LocationServiceRadix = new DummyLocationService();
        logger.info("opened");
    }

    @Override
    public DhtFinalEntity map(DhtFinalEntity dhtFinalEntity) throws Exception {
/*        all.add(1);

        if (dhtFinalEntity.getPacket() == null || dhtFinalEntity.getPacket().getIp() == null) {
            ignored.add(1);
            return dhtFinalEntity;
        }

        String ip = dhtFinalEntity.getPacket().getIp();

        Optional<Ipv4Loc> ipv4Loc = ipv4LocationServiceRadix.resolve(ip);
        if (ipv4Loc.isPresent()) {
            dhtFinalEntity.setIpv4Loc(ipv4Loc.get());
        } else {
            unresolved.add(1);
        }*/

        return dhtFinalEntity;
    }
}
