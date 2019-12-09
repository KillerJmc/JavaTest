package com.test.ORM.SORM.bean;

/**
 * Package the information of a field in the table.
 * @author Jmc
 * @version 0.8
 */
public class ColumnInfo {
    /**
     * Field name
     */
    private String name;

    /**
     * The data type of the field in the database table
     */
    private String dataType;

    /**
     * The key type of the field (0: normal key, 1: primary key, 2: outer key)
     */
    private int keyType;

    public ColumnInfo(String name, String dataType, int keyType) {
        this.name = name;
        this.dataType = dataType;
        this.keyType = keyType;
    }

    public ColumnInfo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getKeyType() {
        return keyType;
    }

    public void setKeyType(int keyType) {
        this.keyType = keyType;
    }
}
