package mayton.flink.udp;

import org.apache.flink.streaming.api.functions.source.FromIteratorFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.walkthrough.common.entity.Transaction;

import java.io.Serializable;
import java.util.Iterator;

public class UdpSource extends FromIteratorFunction<UdpPacket> implements Serializable {

    private static final long serialVersionUID = 1L;

    public UdpSource(Iterator<UdpPacket> iterator) {
        super(iterator);
    }


}


