package com.test.ORM.SORM.core;

import com.test.ORM.SORM.bean.ColumnInfo;
import com.test.ORM.SORM.utils.CloseUtils;
import com.test.ORM.SORM.utils.JDBCUtils;
import com.test.ORM.SORM.utils.ReflectUtils;
import com.test.ORM.SORM.utils.TryUtils;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Be responsible for query. (a core class for providing the external service)
 * @author Jmc
 */
public abstract class Query implements Cloneable {
    /**
     * Adopt the TemplateMethod, package the JDBC operation into template, for easy reuse
     * @param sql sql Statement
     * @param params sql parameters
     * @param back CallBack method
     * @return result
     */
    public Object executeQueryTemplate(String sql, Object[] params, CallBack back) {
        var conn = DBManager.getConn();
        return TryUtils.tryAndReturn(() -> {
            var ps = conn.prepareStatement(sql);
            JDBCUtils.handleParams(ps, params);
            var rs = ps.executeQuery();

            Object o = back.doExecute(conn, ps, rs);
            CloseUtils.closeAll(rs, ps);
            //use connection pool to manage
            DBManager.getPool().closeConnection(conn);
            return o;
        });
    }

    /**
     * Execute a DML command (the basic commands about insert, update, delete, select)
     * @param sql SQL statement
     * @param params parameters
     * @return the amount of affected lines after executing SQL statement
     */
    public int executeDML(String sql, Object... params) {
        int count = 0;
        try (var conn = DBManager.getConn();
             var ps = conn.prepareStatement(sql)) {
            JDBCUtils.handleParams(ps, params);
            count = ps.executeUpdate();
            System.out.println(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Insert an Object into database.
     * (Insert only non-null fields of the Object to the database.
     * And if the Number is null then insert 0)
     * @param obj the Object would be insert
     */
    @SuppressWarnings("all")
    public void insert(Object obj) {
        //obj -> table  insert into tableName (id, name, pwd) values (?, ?, ?)
        Class c = obj.getClass();
        var tableInfo = TableContext.poClassTableMap.get(c);
        var params = new ArrayList<Object>();

        StringBuilder sql = new StringBuilder("insert into " + tableInfo.getTableName() + " (");
        Field[] fs = c.getDeclaredFields();

        for (var f : fs) {
            var fieldName = f.getName();
            Object fieldValue = ReflectUtils.invokeGet(obj, fieldName);

            if (fieldValue != null) {
                sql.append(fieldName + ", ");
                params.add(fieldValue);
            }
        }
        //the last ',' -> ')'
        sql.setCharAt(sql.length() - 2, ')');

        sql.append("values (");
        for (int i = 0; i < params.size(); i++) sql.append("?, ");
        sql.setCharAt(sql.length() - 2, ')');

        executeDML(sql.toString(), params.toArray());
    }

    /**
     * Delete the record in a table whose correspond to a class.
     * (Specify a record of the primary key value id)
     * @param c the Object of a class whose correspond to its table
     * @param id primary key value
     */
    //specify 指定
    public void delete(Class c, Object id) {
        //find TableInfo with Class Object
        var tableInfo = TableContext.poClassTableMap.get(c);
        //only support one PriKey
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();

        //Emp.class, 2 -> delete from emp where id=2
        var sql = "delete from " + tableInfo.getTableName() + " where "
                + onlyPriKey.getName() + "=?";

        executeDML(sql, id);
    }

    /**
     * delete the record of an Object that is correspond to in the database.
     * (reflect)
     * @param obj the Object whose correspond to its table
     */
    public void delete(Object obj) {
        Class c = obj.getClass();
        var tableInfo = TableContext.poClassTableMap.get(c);
        //only support one PriKey
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();

        //use reflection to invoke the get/set fn associates to the field
        Object priKeyValue = ReflectUtils.invokeGet(obj, onlyPriKey.getName());

        delete(c, priKeyValue);
    }

    /**
     * update the records whose correspond to the Object,
     * and only update the value of the specified fields.
     * @param obj the Object would be update
     * @param fieldNames a list of the updated fields
     * @return the amount of affected lines after executing SQL statement
     */
    @SuppressWarnings("all")
    public int update(Object obj, String... fieldNames) {
        //obj{"name", "pwd"} -> update tableName set name=?, pwd=? where id=?
        Class c = obj.getClass();
        var tableInfo = TableContext.poClassTableMap.get(c);
        var priKey = tableInfo.getOnlyPriKey();
        var params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("update " + tableInfo.getTableName() + " set ");

        for (var fName : fieldNames) {
            Object fValue = ReflectUtils.invokeGet(obj, fName);
            params.add(fValue);
            sql.append(fName + "=?, ");
        }
        //delete the last ','
        sql.setCharAt(sql.length() - 2, ' ');
        //delete the last space
        sql.deleteCharAt(sql.length() - 1);
        sql.append("where " + priKey.getName() + "=?");

        params.add(ReflectUtils.invokeGet(obj, priKey.getName()));

        return executeDML(sql.toString(), params.toArray());
    }

    /**
     * Query and return multi-lines records,
     * and package every line's records into the specified Class Object.
     * @param sql query statement
     * @param c a class Object of the javabean whose package the data
     * @param params SQL parameters
     * @return the result of query
     */
    public List queryRows(String sql, Class c, Object... params) {
        return (List) executeQueryTemplate(sql, params, (conn, ps, rs) -> {
            var metaData = rs.getMetaData();
            var list = new ArrayList<>();

            //multi-lines
            while (rs.next()) {
                @SuppressWarnings("unchecked")
                Object rowObj = c.getDeclaredConstructor().newInstance();

                //select name 'username' , pwd, age from emp where id=?, age>18
                //'username' is an alias, and if the alias is null, getColumnLabel fn returns the columnName
                //multi-columns
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    //username, age, emp...
                    var columnName = metaData.getColumnLabel(i + 1);
                    var columnValue = rs.getObject(i + 1);

                    //invoke the setUserName fn of the rowObj Object, set the columnValue into it.
                    ReflectUtils.invokeSet(rowObj, columnName, columnValue);
                }
                list.add(rowObj);
            }
            return list.size() != 0 ? list : null;
        });
    }

    /**
     * Query and return only one line's records,
     * and package every line's records into the specified Class Object.
     * @param sql query statement
     * @param c a class Object of the javabean whose package the data
     * @param params SQL parameters
     * @return the result of query
     */
    public Object queryUniqueRow(String sql, Class c, Object... params) {
        var list = queryRows(sql, c, params);
        return list != null ? list.get(0) : null;
    }

    /**
     * Find the corresponding object by the value of the PriKey
     * @param c a class Object of the javabean whose package the data
     * @param id the value of the PriKey
     * @return the result of query
     */
    public Object queryById(Class c, Object id) {
        //select * from emp where id=?
        var tableInfo = TableContext.poClassTableMap.get(c);
        //only support one PriKey
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();

        //Emp.class, 2 -> delete from emp where id=2
        var sql = "select * from " + tableInfo.getTableName() + " where "
                + onlyPriKey.getName() + "=?";

        return queryUniqueRow(sql, c, id);
    }

    /**
     * Query and return a value. (One row and one column)
     * @param sql query statement
     * @param params SQL parameters
     * @return the result of query
     */
    public Object queryValue(String sql, Object... params) {
        return executeQueryTemplate(sql, params, (conn, ps, rs) -> {
            Object value = null;
            while (rs.next()) {
                value = rs.getObject(1);
            }
            return value;
        });
    }

    /**
     * Query and return a number. (One row and one column)
     * @param sql query statement
     * @param params SQL parameters
     * @return the number of query
     */
    public Number queryNumber(String sql, Object... params) {
        return (Number) queryValue(sql, params);
    }

    /**
     * Paging query.
     * @param pageNum at which page
     * @param lineAmount the line amount presented at per page
     * @return Object
     */
    public abstract Object queryPaginate(int pageNum, int lineAmount);

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
