package com.test.ORM.SORM.bean;

import java.util.List;
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
     * key: columnName
     * value: columnInfo
     */
    private Map<String, ColumnInfo> columns;

    /**
     * The only primary key
     */
    private ColumnInfo onlyPriKey;

    /**
     * The primary keys
     */
    private List<ColumnInfo> priKeys;

    public TableInfo(String tName, List<ColumnInfo> priKeys, Map<String, ColumnInfo> columns) {
        this.tName = tName;
        this.priKeys = priKeys;
        this.columns = columns;
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

    public List<ColumnInfo> getPriKeys() {
        return priKeys;
    }

    public void setPriKeys(List<ColumnInfo> priKeys) {
        this.priKeys = priKeys;
    }

    public ColumnInfo getOnlyPriKey() {
        return onlyPriKey;
    }

    public void setOnlyPriKey(ColumnInfo onlyPriKey) {
        this.onlyPriKey = onlyPriKey;
    }
}
