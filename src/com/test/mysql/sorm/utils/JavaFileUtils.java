package com.test.mysql.sorm.utils;

import com.jmc.io.Files;
import com.jmc.lang.extend.Strs;
import com.test.mysql.sorm.conn.DBManager;
import com.test.mysql.sorm.converter.TypeConverter;
import com.test.mysql.sorm.info.ColumnInfo;
import com.test.mysql.sorm.info.TableInfo;

import java.util.Map;

/**
 * Package the regular operations of creating Java Files.
 * @author Jmc
 */
public class JavaFileUtils {
    /**
     * According to the table information, create the Java src.
     * @param tableInfo table info
     * @param converter type converter
     * @return Java source code
     */
    public static String createJavaSrc(TableInfo tableInfo, TypeConverter converter) {
        Map<String, ColumnInfo> columns = tableInfo.getColumns();

        var src = new StringBuilder();

        //create the package statement
        src.append("package ").append(DBManager.getConf().getPojoPackage()).append(";\n\n");

        //create the import statement
        src.append("import lombok.Data;\n\n");

        //create the class statement
        src.append("@Data\npublic class ").append(Strs.capitalize(tableInfo.getTableName() + " {\n"));

        //create the field list
        columns.forEach((name, info) -> {
            String javaFieldType = converter.databaseType2JavaType(info.getDataType());
            src.append("\tprivate ").append(javaFieldType).append(" ").append(info.getName()).append(";\n");
        });

        src.append("}\n");

        return src.toString();
    }

    /**
     * According to the table information, create the Java PO File.
     * @param tableInfo table info
     * @param converter type converter
     */
    public static void createJavaPojoFile(TableInfo tableInfo, TypeConverter converter) {
        String src = createJavaSrc(tableInfo, converter);

        String srcPath = ".src/";
        String packagePath = DBManager.getConf().getPojoPackage().replace(".", "/");

        var path = srcPath + packagePath + "/" + Strs.capitalize(tableInfo.getTableName()) + ".java";
        Files.out(src, path);

        System.err.println("表：" + tableInfo.getTableName()
                + " -> 类：" + Strs.capitalize(tableInfo.getTableName()) + ".java");
    }
}
