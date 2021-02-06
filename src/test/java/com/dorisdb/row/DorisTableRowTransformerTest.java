package com.dorisdb.row;

import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.junit.Test;

import mockit.Injectable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.dorisdb.DorisSinkBaseTest;

import org.apache.flink.table.data.DecimalData;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.data.StringData;
import org.apache.flink.table.data.TimestampData;

public class DorisTableRowTransformerTest extends DorisSinkBaseTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testTransformer(@Injectable TypeInformation<RowData> rowDataTypeInfo, @Injectable RuntimeContext runtimeCtx) {
        DorisTableRowTransformer rowTransformer = new DorisTableRowTransformer(rowDataTypeInfo);
        rowTransformer.setRuntimeContext(runtimeCtx);
        rowTransformer.setTableSchema(TABLE_SCHEMA);
        GenericRowData rowData = createRowData();
        String result = rowTransformer.transform(rowData);
        Map<String, Object> rMap = (Map<String, Object>)JSON.parse(result);
        assertNotNull(rMap);
        assertEquals(TABLE_SCHEMA.getFieldCount(), rMap.size());
        for (String name : TABLE_SCHEMA.getFieldNames()) {
            assertTrue(rMap.containsKey(name));
        }
    }

	private GenericRowData createRowData() {
		GenericRowData genericRowData = new GenericRowData(TABLE_SCHEMA.getFieldCount());
		genericRowData.setField(0, (byte)20);
		genericRowData.setField(1, StringData.fromString("xxxssss"));
		genericRowData.setField(2, TimestampData.fromTimestamp(Timestamp.valueOf("2021-02-02 12:22:22.006")));
		genericRowData.setField(3, (int)LocalDate.now().toEpochDay());
		genericRowData.setField(4, DecimalData.fromBigDecimal(BigDecimal.valueOf(1000), 10, 2));
		genericRowData.setField(5, (short)30);
		genericRowData.setField(6, StringData.fromString("ch"));
		return genericRowData;
	}
}