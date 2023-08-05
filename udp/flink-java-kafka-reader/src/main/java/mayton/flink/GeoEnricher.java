package mayton.flink;

import org.apache.flink.api.common.functions.RichMapFunction;

import java.io.Serializable;

public class GeoEnricher extends RichMapFunction<DhtFinalEntity, DhtFinalEntity> implements Serializable {
    @Override
    public DhtFinalEntity map(DhtFinalEntity value) throws Exception {
        return null;
    }
}
