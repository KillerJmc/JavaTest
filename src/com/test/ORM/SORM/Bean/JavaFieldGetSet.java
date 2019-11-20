package com.test.ORM.SORM.Bean;

/**
 * Package java attributes and the source code of get and set methods.
 */
//attributes：属性
public class JavaFieldGetSet {
    /**
     * field src information, e.g., private int userId;
     */
    private String fieldInfo;
    /**
     * get src information, e.g., public int getUserId(){}
     */
    private String getInfo;
    /**
     * set src information, e.g., public void setUserId(int id){this.id = id;}
     */
    private String setInfo;

    public JavaFieldGetSet(String fieldInfo, String getInfo, String setInfo) {
        this.fieldInfo = fieldInfo;
        this.getInfo = getInfo;
        this.setInfo = setInfo;
    }

    public JavaFieldGetSet() {

    }

    public String getFieldInfo() {
        return fieldInfo;
    }

    public void setFieldInfo(String fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public String getGetInfo() {
        return getInfo;
    }

    public void setGetInfo(String getInfo) {
        this.getInfo = getInfo;
    }

    public String getSetInfo() {
        return setInfo;
    }

    public void setSetInfo(String setInfo) {
        this.setInfo = setInfo;
    }
}
