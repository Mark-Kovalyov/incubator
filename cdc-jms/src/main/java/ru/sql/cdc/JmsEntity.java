package ru.sql.cdc;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.Immutable;
import java.util.List;
import java.util.Objects;

@Immutable
public final class JmsEntity {

    public final String ts;
    public final String operation;
    public final String tableName;
    public final List<Pair<String, String>> fields;

    public JmsEntity(@NotNull String ts, @NotNull String operation, @NotNull String tableName, @NotNull List<Pair<String, String>> fields) {
        this.ts = ts;
        this.operation = operation;
        this.tableName = tableName;
        this.fields = fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JmsEntity jmsEntity = (JmsEntity) o;
        return Objects.equals(ts, jmsEntity.ts) &&
                Objects.equals(operation, jmsEntity.operation) &&
                Objects.equals(tableName, jmsEntity.tableName) &&
                Objects.equals(fields, jmsEntity.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ts, operation, tableName, fields);
    }

    @Override
    public String toString() {
        return "JmsEntity{" +
                "ts='" + ts + '\'' +
                ", operation='" + operation + '\'' +
                ", tableName='" + tableName + '\'' +
                ", fields=" + fields +
                '}';
    }
}
