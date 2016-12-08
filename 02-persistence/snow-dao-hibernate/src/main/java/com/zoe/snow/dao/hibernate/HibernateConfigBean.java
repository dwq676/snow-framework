package com.zoe.snow.dao.hibernate;

import org.springframework.stereotype.Repository;

/**
 * HibernateBean
 *
 * @author Dai Wenqing
 * @date 2016/10/14
 */
@Repository("snow.dao.hibernate.config.bean")
public class HibernateConfigBean {
    private boolean showSql;
    private boolean useSecondLevel=false;
    private String packageToScan;

    public boolean getShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    public boolean isUseSecondLevel() {
        return useSecondLevel;
    }

    public void setUseSecondLevel(boolean useSecondLevel) {
        this.useSecondLevel = useSecondLevel;
    }

    public String getPackageToScan() {
        return packageToScan;
    }

    public void setPackageToScan(String packageToScan) {
        this.packageToScan = packageToScan;
    }
}
