package mayton.flink.udp;

import java.io.Serializable;
import java.util.Iterator;

public class UdpPacketIterator implements Iterator<UdpPacket>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public UdpPacket next() {
        try {
            Thread.sleep(1550L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new UdpPacket();
    }
}
