package mayton.flink.dummy;

import org.apache.flink.api.common.accumulators.IntCounter;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.jackson.JacksonMapperFactory;

import java.io.Serializable;

public class DummyRichMapFunction<T> extends RichMapFunction<T, T> implements Serializable {

    private IntCounter cnt = new IntCounter();

    @Override
    public void open(Configuration parameters) throws Exception {
        getRuntimeContext().addAccumulator("dummy_cnt", cnt);
        Thread.sleep(60 * 1000L);
    }

    @Override
    public T map(T value) throws Exception {
        cnt.add(1);
        return value;
    }
}
