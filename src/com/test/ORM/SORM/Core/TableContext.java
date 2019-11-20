package com.test.ORM.SORM.Core;

import com.test.ORM.SORM.Bean.ColumnInfo;
import com.test.ORM.SORM.Bean.TableInfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
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
     * key: table name
     * value: table information object
     */
    public static Map<String, TableInfo>
            tables = new HashMap<String,TableInfo>();
    /**
     * Associate the po class Object with the table information Object, for easy reuse.
     */
    public static  Map<Class,TableInfo> poClassTableMap = new HashMap<>();

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

                TableInfo ti = new TableInfo(tableName, new ArrayList<ColumnInfo>()
                        ,new HashMap<String, ColumnInfo>());
                tables.put(tableName, ti);

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
                    ColumnInfo ci2 = (ColumnInfo) ti.getColumns().get(set2.getObject("COLUMN_NAME"));
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
    }

    public static Map<String, TableInfo> getTables() {
        return tables;
    }

    public static void main(String[] args) {
        Map<String,TableInfo>  tables = TableContext.getTables();
        System.out.println(tables);
    }
}
