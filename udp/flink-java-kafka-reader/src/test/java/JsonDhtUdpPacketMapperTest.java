import mayton.flink.DhtFinalEntity;
import mayton.flink.JsonDhtUdpPacketMapper;
import mayton.network.dht.DhtUdpPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JsonDhtUdpPacketMapperTest {

    @Test
    void test() throws Exception {
        JsonDhtUdpPacketMapper mapperFun = new JsonDhtUdpPacketMapper();
        DhtFinalEntity finalEntity = mapperFun.map("{ \"ip\" : \"195.1.1.1\", \"packetSize\" : 1500 }");
        DhtUdpPacket packet = finalEntity.getPacket();
        assertEquals("195.1.1.1", packet.getIp());
        assertEquals(1500, packet.getPacketSize());
    }
    
    
    @Test
    void test2() throws Exception {
        JsonDhtUdpPacketMapper mapperFun = new JsonDhtUdpPacketMapper();
        String json2 = "{ \"ts\":\"" +
                "2023-08-01T21:44:41.604309\", \"ip\":\"238.201.140.55\", \"sourcePort\":5555, \"tag\":\"tr\", \"packetSize\":1, \"packetBody\":\"FF\" }";
        DhtFinalEntity finalEntity = mapperFun.map(json2);
        DhtUdpPacket packet = finalEntity.getPacket();
        assertNotNull(packet);
        assertEquals("238.201.140.55", packet.getIp());
        assertEquals(1, packet.getPacketSize());
    }
}
