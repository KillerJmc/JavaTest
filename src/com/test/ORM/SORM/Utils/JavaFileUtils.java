package com.test.ORM.SORM.Utils;

import com.jmc.io.Files;
import com.test.Main.Tools;
import com.test.ORM.SORM.Bean.ColumnInfo;
import com.test.ORM.SORM.Bean.JavaFieldGetSet;
import com.test.ORM.SORM.Bean.TableInfo;
import com.test.ORM.SORM.Core.DBManager;
import com.test.ORM.SORM.Core.MySQLConverter;
import com.test.ORM.SORM.Core.TableContext;
import com.test.ORM.SORM.Core.TypeConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import static com.test.ORM.SORM.Utils.StringUtils.*;

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
                + firstChar2UpperCase(column.getName())
                + "() {\n");
        getSrc.append("\t\treturn " + column.getName() + ";\n");
        getSrc.append("\t}\n");
        jfgs.setGetInfo(getSrc.toString());

        //public void setName(String name) {this.name = name;}
        var setSrc = new StringBuilder();
        setSrc.append("\tpublic void set"
                + firstChar2UpperCase(column.getName())
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

        //create the class end statement
        src.deleteCharAt(src.lastIndexOf("\n"));
        src.append("}\n");

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

        var path = srcPath + packagePath + "/" + firstChar2UpperCase(tableInfo.getTableName()) + ".java";
        Files.out(src, path, false);
        System.out.println("建立表：" + tableInfo.getTableName()
                + "\n对应的类文件：" + firstChar2UpperCase(tableInfo.getTableName()) + ".java\n");
    }

    public static void main(String[] args) {
        /*var ci = new ColumnInfo("name", "varchar", 0);
        JavaFieldGetSet jfgs = createFieldGetSetSRC(ci, new MySQLConverter());
        System.out.println(jfgs);*/
        TableContext.updateJavaPoFile();
    }
}
