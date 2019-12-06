package com.test.ORM.SORM.Bean;

/**
 * Manage the configuration information.
 * @author Jmc
 */
public class Configuration {
    /**
     * JDBC url
     */
    private String url;
    /**
     * Database user
     */
    private String user;
    /**
     * Database password
     */
    private String pwd;
    /**
     * Use which database
     */
    private String usingDB;
    /**
     * Project src path
     */
    private String srcPath;
    /**
     * Scan the generated java packages. (Persistence Object)
     */
    //持久化对象
    private String poPackage;

    /**
     * The class used for database query.
     */
    private String queryClass;

    /**
     * The max connection amount in the connection pool.
     */
    private int poolMaxSize;

    /**
     * The min connection amount in the connection pool.
     */
    private int poolMinSize;

    public Configuration(String url, String user, String pwd, String usingDB, String srcPath, String poPackage, String queryClass, int poolMaxSize, int poolMinSize) {
        this.url = url;
        this.user = user;
        this.pwd = pwd;
        this.usingDB = usingDB;
        this.srcPath = srcPath;
        this.poPackage = poPackage;
        this.queryClass = queryClass;
        this.poolMaxSize = poolMaxSize;
        this.poolMinSize = poolMinSize;
    }

    public Configuration() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUsingDB() {
        return usingDB;
    }

    public void setUsingDB(String usingDB) {
        this.usingDB = usingDB;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getPoPackage() {
        return poPackage;
    }

    public void setPoPackage(String poPackage) {
        this.poPackage = poPackage;
    }

    public String getQueryClass() {
        return queryClass;
    }

    public void setQueryClass(String queryClass) {
        this.queryClass = queryClass;
    }

    public int getPoolMaxSize() {
        return poolMaxSize;
    }

    public void setPoolMaxSize(int poolMaxSize) {
        this.poolMaxSize = poolMaxSize;
    }

    public int getPoolMinSize() {
        return poolMinSize;
    }

    public void setPoolMinSize(int poolMinSize) {
        this.poolMinSize = poolMinSize;
    }
}
