package com.test.mysql.sorm.info;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Storage the information of the table structure.
 * @author Jmc
 */
@Data
public class TableInfo {
    /**
     * Table name
     */
    private String tableName;

    /**
     * The information of all fields
     * key: columnName
     * value: columnInfo
     */
    private Map<String, ColumnInfo> columns = new HashMap<>();

    /**
     * The only primary key
     */
    private ColumnInfo onlyPriKey;

    /**
     * The primary keys
     */
    private List<ColumnInfo> priKeys = new ArrayList<>();
}
