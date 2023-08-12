package mayton.flink;

import mayton.ip2loc.Ipv4Loc;
import mayton.network.NetworkUtils;
import mayton.network.dht.DhtUdpPacket;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class DhtFinalEntity implements Serializable {

    private static Random random = new Random();

    private static String randomIp() {
        long rand = Integer.MAX_VALUE + (int) (random.nextGaussian() * 100.0);
        return NetworkUtils.formatIpV4(min(max(rand,0), 0xFFFF_FFFFL));
    }


    public static DhtFinalEntity emptyEntity() {
        DhtFinalEntity r = new DhtFinalEntity();
        DhtUdpPacket p = new DhtUdpPacket();
        p.setIp("0.0.0.0");
        p.setTs(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        p.setTag("");
        p.setPacketBody("");
        p.setPacketSize(0);
        p.setSourcePort(0);
        r.setPacket(p);
        return r;
    }


    public static DhtFinalEntity randomEntity(String err) {
        DhtFinalEntity r = new DhtFinalEntity();
        DhtUdpPacket p = new DhtUdpPacket();
        p.setIp(randomIp());
        p.setTs(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        p.setTag(err);
        p.setPacketBody("FF");
        p.setPacketSize(1);
        p.setSourcePort(random.nextInt(65535));
        r.setPacket(p);
        return r;
    }

    private DhtUdpPacket packet;
    private String ptr;
    private String dhtDecodedJson;
    private Ipv4Loc ipv4Loc;

    public DhtFinalEntity(DhtUdpPacket packet) {
        this.packet = packet;
    }

    public DhtFinalEntity() {
    }

    public DhtUdpPacket getPacket() {
        return packet;
    }

    public void setPacket(DhtUdpPacket packet) {
        this.packet = packet;
    }

    public String getPtr() {
        return ptr;
    }

    public void setPtr(String ptr) {
        this.ptr = ptr;
    }

    public String getDhtDecodedJson() {
        return dhtDecodedJson;
    }

    public void setDhtDecodedJson(String dhtDecodedJson) {
        this.dhtDecodedJson = dhtDecodedJson;
    }

    public Ipv4Loc getIpv4Loc() {
        return ipv4Loc;
    }

    public void setIpv4Loc(Ipv4Loc ipv4Loc) {
        this.ipv4Loc = ipv4Loc;
    }
}
