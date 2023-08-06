import mayton.flink.DhtFinalEntity;
import mayton.flink.RevDnsEnricher;
import mayton.network.dht.DhtUdpPacket;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RevDnsEnricherTest {

    private DhtFinalEntity createEntity(String ip) {
        DhtFinalEntity entity = new DhtFinalEntity();
        DhtUdpPacket p = new DhtUdpPacket();
        p.setIp(ip);
        entity.setPacket(p);
        return entity;
    }

    @Test
    @Disabled
    void test() throws Exception {
        RevDnsEnricher revDnsEnricher = new RevDnsEnricher();
        revDnsEnricher.open(null);
        DhtFinalEntity res = revDnsEnricher.map(createEntity("142.250.180.238"));
        assertEquals(res.getPtr(), "google.com");
    }

}
