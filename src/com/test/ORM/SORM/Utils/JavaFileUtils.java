package com.test.ORM.SORM.Utils;

import com.test.ORM.SORM.Bean.ColumnInfo;
import com.test.ORM.SORM.Bean.JavaFieldGetSet;
import com.test.ORM.SORM.Core.MySQLConverter;
import com.test.ORM.SORM.Core.TypeConverter;
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

    public static void main(String[] args) {
        var ci = new ColumnInfo("name", "varchar", 0);
        JavaFieldGetSet fgss = createFieldGetSetSRC(ci, new MySQLConverter());
        System.out.println(fgss);
    }
}
