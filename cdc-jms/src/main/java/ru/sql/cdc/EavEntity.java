package ru.sql.cdc;

import org.jetbrains.annotations.NotNull;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class EavEntity {

    public final String ts;
    public final String operation;
    public final String tableName;
    public final String columnName;
    public final String columnValue;

    public EavEntity(@NotNull String ts,@NotNull String operation,@NotNull String tableName,@NotNull String columnName,@NotNull String columnValue) {
        this.ts = ts;
        this.operation = operation;
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    public boolean isTheSameTsOperationAndTable(@NotNull EavEntity that) {
        return ts.equals(that.ts) && operation.equals(that.operation) && tableName.equals(that.tableName);
    }

}
