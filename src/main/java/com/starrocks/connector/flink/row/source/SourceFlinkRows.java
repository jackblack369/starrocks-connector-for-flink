package com.starrocks.connector.flink.row.source;

import org.apache.flink.table.data.GenericRowData;

public interface SourceFlinkRows {
    boolean hasNext();

    GenericRowData next();
}
