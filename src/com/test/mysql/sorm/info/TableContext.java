package com.test.mysql.sorm.info;

import com.jmc.lang.Strs;
import com.jmc.lang.Tries;
import com.test.mysql.sorm.conn.DBManager;
import com.test.mysql.sorm.converter.MySQLConverter;
import com.test.mysql.sorm.utils.JavaFileUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Be responsible for getting and managing the relations
 * of all table and Class structures in the database,
 * and create Class structure according to table structure.
 * Complete by coping. (This is not very important)
 * @author Jmc
 */
public class TableContext {
    /**
     * table name -> table information object
     */
    public static List<TableInfo> tables = new ArrayList<>();

    /**
     * Associate the Pojo class Object with the table information Object, for easy reuse.
     */
    public static Map<Class<?>, TableInfo> pojoClassTableMap = new HashMap<>();

    private TableContext(){

    }

    static {
        try {
            //Init and get the table information
            Connection con = DBManager.getConn();
            DatabaseMetaData dbmd = con.getMetaData();

            ResultSet tableRet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});

            while(tableRet.next()){
                String tableName = (String) tableRet.getObject("TABLE_NAME");

                TableInfo ti = new TableInfo();
                ti.setTableName(tableName);
                tables.add(ti);

                ResultSet set = dbmd.getColumns(null, "%", tableName, "%");

                //enquire the all fields in the table
                while(set.next()){
                    ColumnInfo ci = new ColumnInfo(set.getString("COLUMN_NAME"),
                            set.getString("TYPE_NAME"), 0);
                    ti.getColumns().put(set.getString("COLUMN_NAME"), ci);
                }

                ResultSet set2 = dbmd.getPrimaryKeys(null, "%", tableName);

                //enquire the priKey in the table
                while(set2.next()){
                    ColumnInfo ci2 = ti.getColumns().get(set2.getString("COLUMN_NAME"));
                    ci2.setKeyType(1);
                    //set priKey type
                    ti.getPriKeys().add(ci2);
                }

                if(ti.getPriKeys().size() > 0) {
                    //get the only priKey for easy reuse, if they are multi-keys, then is NULL.
                    ti.setOnlyPriKey(ti.getPriKeys().get(0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //update the class structure
        updateJavaPoFile();

        //load the all classes in the Pojo package for easy reuse and greater efficiency
        loadPojoTables();
    }

    /**
     * According to the table structure, update the Java Objects in the Po package.
     */
    public static void updateJavaPoFile() {
        tables.forEach(tableInfo ->
                JavaFileUtils.createJavaPojoFile(tableInfo, new MySQLConverter())
        );
        System.out.println();
    }

    /**
     * Load the Classes in the Pojo package
     */
    public static void loadPojoTables() {
        tables.forEach(tableInfo -> Tries.tryThis(() -> {
            var c = Class.forName(DBManager.getConf().getPojoPackage() + "."
                    + Strs.capitalize(tableInfo.getTableName()));
            pojoClassTableMap.put(c, tableInfo);
        }));
    }
}
