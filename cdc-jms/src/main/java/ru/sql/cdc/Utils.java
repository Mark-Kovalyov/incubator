package ru.sql.cdc;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.ThreadSafe;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@ThreadSafe
public class Utils {

    private Utils() {}

    @NotNull public static <T> Stream<T> toStream(@NotNull Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    // TODO: Refactor with Streams/GroupBy
    @NotNull public static Iterable<JmsEntity> groupByTimeOperationTableName(@NotNull Iterable<EavEntity> eavEntities) {
        Iterator<EavEntity> iterator = eavEntities.iterator();
        List<JmsEntity> jmsIntercomEntities = new ArrayList<>();
        List<Pair<String, String>> fields = new ArrayList<>();
        EavEntity prevItem = null;
        // Copy from eavEntities to jmsEntities with groupping by ts/operation/tablename
        while(iterator.hasNext()) {
            EavEntity item = iterator.next();
            if (prevItem == null || (prevItem.ts.equals(item.ts) && prevItem.operation.equals(item.operation) && prevItem.tableName.equals(item.tableName))) {
                fields.add(Pair.of(item.columnName, item.columnValue));
            } else {
                jmsIntercomEntities.add(new JmsEntity(prevItem.ts, prevItem.operation, prevItem.tableName, fields));
                fields = new ArrayList<>();
                fields.add(Pair.of(item.columnName, item.columnValue));
            }
            prevItem = item;
        }
        // Flush last eavEntities from buffer to JMS entity
        if (!fields.isEmpty()) {
            jmsIntercomEntities.add(new JmsEntity(prevItem.ts, prevItem.operation, prevItem.tableName, fields));
        }
        return jmsIntercomEntities;
    }

    @NotNull public static String jmsIntercomToJson(@NotNull JmsEntity entity) {
        // TODO:
        return "{}";
    }

}
