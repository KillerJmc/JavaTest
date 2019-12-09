package com.test.ORM.SORM.utils;

import com.jmc.io.Files;
import com.test.ORM.SORM.bean.ColumnInfo;
import com.test.ORM.SORM.bean.JavaFieldGetSet;
import com.test.ORM.SORM.bean.TableInfo;
import com.test.ORM.SORM.core.DBManager;
import com.test.ORM.SORM.core.TypeConverter;

import java.util.ArrayList;
import java.util.Map;

/**
 * Package the regular operations of creating Java Files.
 * @author Jmc
 */
public class JavaFileUtils {
    /**
     * Create field src information, e.g., private int userId and set, get methods;
     * @param column field information
     * @param converter type converter
     * @return field src information and set, get src code
     */
    public static JavaFieldGetSet createFieldGetSetSRC(ColumnInfo column, TypeConverter converter) {
        var jfgs = new JavaFieldGetSet();

        String javaFieldType = converter.databaseType2JavaType(column.getDataType());

        //private String name;
        jfgs.setFieldInfo("\tprivate " + javaFieldType + " " + column.getName() + ";\n");

        //public String getName() {return name;}
        var getSrc = new StringBuilder();
        getSrc.append("\tpublic "
                + javaFieldType
                + " get"
                + StringUtils.firstChar2UpperCase(column.getName())
                + "() {\n");
        getSrc.append("\t\treturn " + column.getName() + ";\n");
        getSrc.append("\t}\n");
        jfgs.setGetInfo(getSrc.toString());

        //public void setName(String name) {this.name = name;}
        var setSrc = new StringBuilder();
        setSrc.append("\tpublic void set"
                + StringUtils.firstChar2UpperCase(column.getName())
                + "(");
        setSrc.append(javaFieldType + " " + column.getName() + ") {\n");
        setSrc.append("\t\tthis." + column.getName() + " = " + column.getName() + ";\n");
        setSrc.append("\t}\n");
        jfgs.setSetInfo(setSrc.toString());
        return jfgs;
    }

    /**
     * According to the table information, create the Java src.
     * @param tableInfo table info
     * @param converter type converter
     * @return Java source code
     */
    public static String createJavaSrc(TableInfo tableInfo, TypeConverter converter) {
        Map<String, ColumnInfo> columns = tableInfo.getColumns();
        var javaFields = new ArrayList<JavaFieldGetSet>();

        for (ColumnInfo c : columns.values()) {
            javaFields.add(createFieldGetSetSRC(c, converter));
        }

        var src = new StringBuilder();

        //create the package statement
        src.append("package " + DBManager.getConf().getPoPackage() + ";\n\n");

        //create the import statement
        src.append("import java.sql.*;\n");
        src.append("import java.util.*;\n\n");

        //create the class statement
        src.append("public class " + StringUtils.firstChar2UpperCase(tableInfo.getTableName() + " {\n"));

        //create the field list
        for (var t : javaFields) src.append(t.getFieldInfo());
        src.append("\n");

        //create the get and set fn list
        for (var t : javaFields) {
            src.append(t.getGetInfo() + "\n");
            src.append(t.getSetInfo() + "\n");
        }

        //create toString method (original by myself)
        //    @Override
        //    public String toString() {
        //        return "Emp{" +
        //                "birthday=" + birthday +
        //                ", bonus=" + bonus +
        //                ", name=" + name  +
        //                ", deptId=" + deptId +
        //                ", id=" + id +
        //                ", salary=" + salary +
        //                ", age=" + age +
        //                '}';
        //    }
        var columnNames = new String[columns.size()];
        columns.keySet().toArray(columnNames);
        src.append("\t@Override\n");
        src.append("\tpublic String toString() {\n");
        src.append("\t\treturn \"" + StringUtils.firstChar2UpperCase(tableInfo.getTableName() + "{\" +\n"));
        src.append("\t\t\t\t\"" + columnNames[0] + "=\" + " + columnNames[0] + " +\n");
        for (int i = 1; i < columnNames.length; i++) {
            src.append("\t\t\t\t\", " + columnNames[i] +"=\" + " + columnNames[i] + " +\n");
        }
        src.append("\t\t\t\t'}';\n\t}");

        //create the class end statement
        src.append("\n}\n");

        return src.toString();
    }

    /**
     * According to the table information, create the Java PO File.
     * @param tableInfo table info
     * @param converter type converter
     */
    public static void createJavaPoFile(TableInfo tableInfo, TypeConverter converter) {
        String src = createJavaSrc(tableInfo, converter);

        String srcPath = DBManager.getConf().getSrcPath() + "/";
        String packagePath = DBManager.getConf().getPoPackage().replace(".", "/");

        var path = srcPath + packagePath + "/" + StringUtils.firstChar2UpperCase(tableInfo.getTableName()) + ".java";
        Files.out(src, path, false);
        System.out.println("建立表：" + tableInfo.getTableName()
                + "\n对应的类文件：" + StringUtils.firstChar2UpperCase(tableInfo.getTableName()) + ".java\n");
    }
}
