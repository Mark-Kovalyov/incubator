package mayton.flink.dummy;

import org.apache.flink.api.common.functions.MapFunction;

import java.io.Serializable;

public class DummyMapFunction implements MapFunction<String, String>, Serializable {

    @Override
    public String map(String value) throws Exception {
        return value;
    }
}
