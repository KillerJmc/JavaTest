package com.test.ORM.SORM.Core;

/**
 * Be responsible for the conversion between MySQL data type and Java data type
 * @author Jmc
 */
public class MySQLConverter implements TypeConverter {
    @Override
    public String databaseType2JavaType(String databaseDataType) {
        if ("varchar".equals(databaseDataType) || "char".equals(databaseDataType)) {
            return "String";
        } else if ("int".equals(databaseDataType) ||
                "tinyint".equals(databaseDataType) ||
                "smallint".equals(databaseDataType) ||
                "integer".equals(databaseDataType)) {
            return "Integer";
        } else if ("bigint".equals(databaseDataType)) {
            return "Long";
        } else if ("double".equals(databaseDataType) ||
                "float".equals(databaseDataType)) {
            return "Double";
        } else if ("clob".equals(databaseDataType)) {
            return "java.sql.Clob";
        } else if ("blob".equals(databaseDataType)) {
            return "java.sql.Blob";
        } else if ("date".equals(databaseDataType)) {
            return "java.sql.Date";
        } else if ("time".equals(databaseDataType)) {
            return "java.sql.Time";
        } else if ("timestamp".equals(databaseDataType)) {
            return "java.sql.TimeStamp";
        }
        return null;
    }

    @Override
    public String javaType2DatabaseType(String javaDataType) {
        return null;
    }
}
