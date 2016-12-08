package com.zoe.snow.dao;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据源对象
 *
 * @author Dai Wenqing
 * @date 2016/10/12
 */
@Repository("snow.dao.data.sources")
//@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DataSources {
    private Map<String, DataSourceBean> dataSourceBeanMap;

    public Map<String, DataSourceBean> getDataSourceBeanMap() {
        return dataSourceBeanMap;
    }

    public void setDataSourceBeanMap(Map<String, DataSourceBean> dataSourceBeanMap) {
        this.dataSourceBeanMap = dataSourceBeanMap;
    }

}
