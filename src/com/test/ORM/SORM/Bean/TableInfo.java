package com.test.ORM.SORM.Bean;

import java.util.Map;

/**
 * Storage the information of the table structure.
 * @author Jmc
 */
public class TableInfo {
    /**
     * Table name
     */
    private String tName;

    /**
     * The information of all fields
     */
    private Map<String, ColumnInfo> columns;

    /**
     * The only primary key
     * (we can deal with only one PriKey at the present)
     */
    private ColumnInfo onlyPriKey;

    public TableInfo(String tName, Map<String, ColumnInfo> columns, ColumnInfo onlyPriKey) {
        this.tName = tName;
        this.columns = columns;
        this.onlyPriKey = onlyPriKey;
    }

    public TableInfo() {

    }

    public String getTableName() {
        return tName;
    }

    public void setTableName(String tName) {
        this.tName = tName;
    }

    public Map<String, ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, ColumnInfo> columns) {
        this.columns = columns;
    }

    public ColumnInfo getOnlyPriKey() {
        return onlyPriKey;
    }

    public void setOnlyPriKey(ColumnInfo onlyPriKey) {
        this.onlyPriKey = onlyPriKey;
    }
}
