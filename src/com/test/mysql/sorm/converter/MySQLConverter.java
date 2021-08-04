package com.test.mysql.sorm.converter;

/**
 * Be responsible for the conversion between MySQL data type and Java data type
 * @author Jmc
 */
public class MySQLConverter implements TypeConverter {
    @Override
    public String databaseType2JavaType(String databaseDataType) {
        if ("varchar".equalsIgnoreCase(databaseDataType) || "char".equalsIgnoreCase(databaseDataType)) {
            return "String";
        } else if ("int".equalsIgnoreCase(databaseDataType) ||
                "tinyint".equalsIgnoreCase(databaseDataType) ||
                "smallint".equalsIgnoreCase(databaseDataType) ||
                "integer".equalsIgnoreCase(databaseDataType)) {
            return "Integer";
        } else if ("bigint".equalsIgnoreCase(databaseDataType)) {
            return "Long";
        } else if ("double".equalsIgnoreCase(databaseDataType) ||
                "float".equalsIgnoreCase(databaseDataType)) {
            return "Double";
        } else if ("clob".equalsIgnoreCase(databaseDataType)) {
            return "java.sql.Clob";
        } else if ("blob".equalsIgnoreCase(databaseDataType)) {
            return "java.sql.Blob";
        } else if ("date".equalsIgnoreCase(databaseDataType)) {
            return "java.sql.Date";
        } else if ("time".equalsIgnoreCase(databaseDataType)) {
            return "java.sql.Time";
        } else if ("timestamp".equalsIgnoreCase(databaseDataType)) {
            return "java.sql.TimeStamp";
        }
        return null;
    }

    @Override
    public String javaType2DatabaseType(String javaDataType) {
        return null;
    }
}
