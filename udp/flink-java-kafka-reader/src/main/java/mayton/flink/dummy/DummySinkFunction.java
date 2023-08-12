package mayton.flink.dummy;

import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummySinkFunction implements SinkFunction<String> {

    static Logger logger = LoggerFactory.getLogger("dummy-sink");

    @Override
    public void invoke(String s, Context context) throws Exception {
        logger.warn("invoke : {}",s);
    }
}
