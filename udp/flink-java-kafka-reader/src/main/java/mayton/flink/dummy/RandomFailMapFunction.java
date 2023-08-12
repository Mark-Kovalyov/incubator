package mayton.flink.dummy;

import org.apache.flink.api.common.functions.MapFunction;

import java.io.Serializable;
import java.util.Random;

public class RandomFailMapFunction<T> implements MapFunction<T, T>, Serializable {

    @Override
    public T map(T value) throws Exception {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        if (random.nextDouble() < 0.01) {
            throw new Exception("Random exception");
        }
        return value;
    }
}
