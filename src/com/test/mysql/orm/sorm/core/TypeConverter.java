package com.test.mysql.orm.sorm.core;

/**
 * Be responsible for the conversion between data type and database data type
 * @author Jmc
 */
public interface TypeConverter {
    /**
     * Convert database data type into Java data type.
     * @param databaseDataType the field data type in the database
     * @return Java data type
     */
    String databaseType2JavaType(String databaseDataType);

    /**
     * Convert Java data type into database data type.
     * @param javaDataType Java data type
     * @return database data type
     */
    String javaType2DatabaseType(String javaDataType);
}
