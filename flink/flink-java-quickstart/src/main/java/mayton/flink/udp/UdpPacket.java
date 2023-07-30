package mayton.flink.udp;

import java.io.Serializable;

public final class UdpPacket implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String ip;

    public UdpPacket() {
        this.ip = "";
    }

    public UdpPacket(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }


}
