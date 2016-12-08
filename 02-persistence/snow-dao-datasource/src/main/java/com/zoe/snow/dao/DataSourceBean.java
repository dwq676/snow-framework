package com.zoe.snow.dao;

import org.springframework.stereotype.Repository;

/**
 * DataSourceNode
 *
 * @author Dai Wenqing
 * @date 2016/10/12
 */
@Repository("snow.dao.datasource.bean")
public class DataSourceBean {
    //private String key;
    private String cluster;
    private String driver;
    private String schema;
    private String type;
    private DataSourceHost readOnlyHost;
    private DataSourceHost writeAbleHost;
    //是否开启验证器,后会自动从数据库中取表信息进行验证
    private boolean toValidate = false;

    public boolean getToValidate() {
        return toValidate;
    }

    public void setToValidate(boolean toValidate) {
        this.toValidate = toValidate;
    }

    public DataSourceHost getWriteAbleHost() {
        return writeAbleHost;
    }

    public void setWriteAbleHost(DataSourceHost writeAbleHost) {
        this.writeAbleHost = writeAbleHost;
    }

    /*public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }*/

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public DataSourceHost getReadOnlyHost() {
        return readOnlyHost;
    }

    public void setReadOnlyHost(DataSourceHost readOnlyHost) {
        this.readOnlyHost = readOnlyHost;
    }
}
