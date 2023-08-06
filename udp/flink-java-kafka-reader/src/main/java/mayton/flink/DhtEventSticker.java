package mayton.flink;


import org.apache.flink.api.common.functions.RichMapFunction;

import java.io.Serializable;

// ping
// announce_peer
// get_peers
// find_node
public class DhtEventSticker extends RichMapFunction<DhtFinalEntity, DhtFinalEntity> implements Serializable {

    @Override
    public DhtFinalEntity map(DhtFinalEntity value) throws Exception {
        return value;
    }
}
