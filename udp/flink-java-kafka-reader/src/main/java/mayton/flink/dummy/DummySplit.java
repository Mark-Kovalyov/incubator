package mayton.flink.dummy;

import org.apache.flink.api.connector.source.SourceSplit;

public class DummySplit implements SourceSplit {
    @Override
    public String splitId() {
        return "dummy";
    }
}
