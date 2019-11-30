package com.test.ORM.SORM.Core;

import com.test.ORM.SORM.Bean.ColumnInfo;
import com.test.ORM.SORM.Po.Emp;
import com.test.ORM.SORM.Utils.JDBCUtils;
import com.test.ORM.SORM.Utils.ReflectUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Be responsible for MySQL database query.
 * @author Jmc
 */
public class MySQLQuery implements Query {
    @Override
    public int executeDML(String sql, Object... params) {
        var conn = DBManager.getConn();
        int count = 0;
        try (var ps = conn.prepareStatement(sql)) {
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
        Object priKeyValue = ReflectUtils.invokeGet(onlyPriKey.getName(), obj);

        delete(c, priKeyValue);
    }

    @Override
    public int update(Object obj, String[] fieldNames) {
        return 0;
    }

    @Override
    public List queryRows(String sql, Class c, Object[] params) {
        return null;
    }

    @Override
    public Object queryUniqueRow(String sql, Class c, Object[] params) {
        return null;
    }

    @Override
    public Object queryValue(String sql, Object[] params) {
        return null;
    }

    @Override
    public Number queryNumber(String sql, Object[] params) {
        return null;
    }

    public static void main(String[] args) {
        Emp e = new Emp();
        e.setId(4);
        new MySQLQuery().delete(e);
    }
}
