package com.test.ORM.SORM.Core;

import java.util.List;

/**
 * Be responsible for query. (a core class for providing the external service)
 * @author Jmc
 */
public interface Query {
    /**
     * Execute a DML command (the basic commands about insert, update, delete, select)
     * @param sql SQL statement
     * @param params parameters
     * @return the amount of affected lines after executing SQL statement
     */
    int executeDML(String sql, Object[] params);

    /**
     * Insert an Object into database.
     * @param obj the Object would be insert
     */
    void insert(Object obj);

    /**
     * Delete the record in a table whose correspond to a class.
     * (Specify a record of the primary key value id)
     * @param c the Object of a class whose correspond to its table
     * @param id primary key value
     */
    //specify 指定
    void delete(Class c, int id);

    /**
     * delete the record of an Object that is correspond to in the database.
     * (reflect)
     * @param obj
     */
    void delete(Object obj);

    /**
     * update the records whose correspond to the Object,
     * and only update the value of the specified fields.
     * @param obj the Object would be update
     * @param fieldNames a list of the updated fields
     * @return the amount of affected lines after executing SQL statement
     */
    int update(Object obj, String[] fieldNames);

    /**
     * Query and return multi-lines records,
     * and package every line's records into the specified Class Object.
     * @param sql query statement
     * @param c a class Object of the javabean whose package the data
     * @param params SQL parameters
     * @return the result of query
     */
    List queryRows(String sql, Class c, Object[] params);

    /**
     * Query and return only one line's records,
     * and package every line's records into the specified Class Object.
     * @param sql query statement
     * @param c a class Object of the javabean whose package the data
     * @param params SQL parameters
     * @return the result of query
     */
    Object queryUniqueRow(String sql, Class c, Object[] params);

    /**
     * Query and return a value. (One row and one column)
     * @param sql query statement
     * @param params SQL parameters
     * @return the result of query
     */
    Object queryValue(String sql, Object[] params);

    /**
     * Query and return a number. (One row and one column)
     * @param sql query statement
     * @param params SQL parameters
     * @return the number of query
     */
    Number queryNumber(String sql, Object[] params);
}
