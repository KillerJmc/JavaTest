package com.test.ORM.SORM.Core;

import com.test.ORM.SORM.Bean.ColumnInfo;
import com.test.ORM.SORM.Utils.CloseUtils;
import com.test.ORM.SORM.Utils.JDBCUtils;
import com.test.ORM.SORM.Utils.ReflectUtils;
import com.test.ORM.SORM.Utils.TryUtils;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Be responsible for MySQL database query.
 * @author Jmc
 */
public class MySQLQuery implements Query {
    @Override
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

    @Override
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

    @Override
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

    @Override
    public void delete(Object obj) {
        Class c = obj.getClass();
        var tableInfo = TableContext.poClassTableMap.get(c);
        //only support one PriKey
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();

        //use reflection to invoke the get/set fn associates to the field
        Object priKeyValue = ReflectUtils.invokeGet(obj, onlyPriKey.getName());

        delete(c, priKeyValue);
    }

    @Override
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

    @Override
    public List queryRows(String sql, Class c, Object... params) {
        var conn = DBManager.getConn();
        var list = new ArrayList<>();

        TryUtils.tryThis(() -> {
            var ps = conn.prepareStatement(sql);
            JDBCUtils.handleParams(ps, params);
            var rs = ps.executeQuery();
            var metaData = rs.getMetaData();

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
            CloseUtils.closeAll(rs, ps, conn);
        });
        return list.size() != 0 ? list : null;
    }

    @Override
    public Object queryUniqueRow(String sql, Class c, Object... params) {
        var list = queryRows(sql, c, params);
        return list != null ? list.get(0) : null;
    }

    @Override
    public Object queryValue(String sql, Object... params) {
        var conn = DBManager.getConn();

        return TryUtils.tryAndReturn(() -> {
            var ps = conn.prepareStatement(sql);
            JDBCUtils.handleParams(ps, params);
            var rs = ps.executeQuery();
            Object value = null;

            while (rs.next()) {
                value = rs.getObject(1);
            }

            CloseUtils.closeAll(rs, ps, conn);
            return value;
        });
    }

    @Override
    public Number queryNumber(String sql, Object... params) {
        return (Number) queryValue(sql, params);
    }
}
