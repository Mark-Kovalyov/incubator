package mayton.flink.dummy;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.connector.source.*;
import org.apache.flink.api.java.typeutils.ResultTypeQueryable;
import org.apache.flink.core.io.SimpleVersionedSerializer;

public class DummySource<OUT> implements Source<OUT, DummySplit, Object>,  ResultTypeQueryable<OUT> {

    @Override
    public Boundedness getBoundedness() {
        return null;
    }

    @Override
    public SplitEnumerator<DummySplit, Object> createEnumerator(SplitEnumeratorContext<DummySplit> enumContext) throws Exception {
        return null;
    }

    @Override
    public SplitEnumerator<DummySplit, Object> restoreEnumerator(SplitEnumeratorContext<DummySplit> enumContext, Object checkpoint) throws Exception {
        return null;
    }

    @Override
    public SimpleVersionedSerializer<DummySplit> getSplitSerializer() {
        return null;
    }

    @Override
    public SimpleVersionedSerializer<Object> getEnumeratorCheckpointSerializer() {
        return null;
    }

    @Override
    public SourceReader<OUT, DummySplit> createReader(SourceReaderContext readerContext) throws Exception {
        return null;
    }

    @Override
    public TypeInformation<OUT> getProducedType() {
        return null;
    }
}
