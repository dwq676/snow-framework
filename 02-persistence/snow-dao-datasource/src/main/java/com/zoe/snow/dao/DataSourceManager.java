package com.zoe.snow.dao;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.conf.DaoConfiguration;
import com.zoe.snow.dao.dialect.Dialect;
import com.zoe.snow.listener.ContextRefreshedListener;
import com.zoe.snow.log.Logger;
import com.zoe.snow.util.Validator;
import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源管理 只支持最多两个数据源，一个可读可写，一个只读 至于各自后的数据库的集群由集群模块进行管理
 *
 * @author Dai Wenqing
 * @date 2016/1/21
 */
@Repository("snow.dao.datasource.manager")
public class DataSourceManager implements ContextRefreshedListener {
    private static Map<String, Map<Mode, DataSource>> dataSourceMap = new ConcurrentHashMap<>();
    private static Map<String, DataSourceBean> dataSourceBeanMap = new ConcurrentHashMap<>();
    //private static Dialect dialect;
    private static String defaultDatasource = "default";
    @Autowired
    private DaoConfiguration daoConfiguration;
    @Autowired
    private DataSources dataSources;

    /*public Dialect getDialect() {
        return dialect;
    }*/

    public static String getDefaultDatasourceKey() {
        return defaultDatasource;
    }

    /**
     * 根据关键字获取数据源bean
     *
     * @param keys
     * @return
     */
    public static DataSourceBean getDataSourceBean(String... keys) {
        DataSourceBean dataSourceBean = null;
        String key = getDefaultDatasourceKey();
        if (keys.length > 0) {
            if (!Validator.isEmpty(keys[0]))
                key = keys[0];
        }
        dataSourceBean = dataSourceBeanMap.get(key);
        if (Validator.isEmpty(dataSourceBean))
            throw new DataSourceNotFundException(MessageFormat
                    .format("未找到任何key值为[{}]的数据源DataSourceBean,可能是原因是未在配置文件中配置数据源", key));
        return dataSourceBean;
    }

    /*
     * 获取数据源
     *
     * @return
     */

    public static DataSource getDatasource(Mode mode, String... dataSourceKey) {
        String key = "";
        if (dataSourceKey.length > 0) {
            if (!Validator.isEmpty(dataSourceKey[0])) {
                key = dataSourceKey[0];
            }
        }
        if (Validator.isEmpty(key))
            key = defaultDatasource;
        if (mode == null)
            mode = Mode.Read;
        return dataSourceMap.get(key).get(mode);
    }

    public static Map<String, Map<Mode, DataSource>> getDatasourceMap() {
        return dataSourceMap;
    }

    /**
     * 获取所有数据源
     *
     * @return
     */
    /*
     * public List<DataSource> getDataSources() { List<DataSource>
     * dataSourceList = new ArrayList<>(); dataSourceMap.forEach((key, value) ->
     * { dataSourceList.add(value); }); return dataSourceList; }
     */
    @Override
    public int getContextRefreshedSort() {
        return 3;
    }

    @Override
    public void onContextRefreshed() {
        if (dataSources == null || daoConfiguration == null)
            return;
        /*dialectSet.forEach(c -> {
            if (c.getName().equals(daoConfiguration.getDriver())) {
                dialect = c;
                return;
            }
        });*/
        //dialect= BeanFactory.getBean("snow.dao.dialect."+dataSources.)
        Logger.info("使用的数据连接池组件是：{}", DruidXADataSource.class.getName());
        createDataSource();
    }

    @Override
    public String getName() {
        return "DataSourceManage";
    }

    /**
     * 创建可能存在的多数据源
     */
    private void createDataSource() {
        if (dataSources != null) {
            if (dataSources.getDataSourceBeanMap() != null) {
                //JSONArray jsonArray = JSONArray.fromObject(daoConfiguration.getServers());
                if (dataSources.getDataSourceBeanMap().keySet() != null) {
                    dataSources.getDataSourceBeanMap().forEach((k, v) -> {
                        //JSONObject jsonObject = (JSONObject) s;
                        if (Validator.isEmpty(v))
                            return;
                        dataSourceBeanMap.put(k, v);
                        String key = k;
                        Map<Mode, DataSource> datasource = new ConcurrentHashMap<>();
                        DataSource readOnly = createDatasourceWR(v, v.getReadOnlyHost());
                        DataSource writeAble = createDatasourceWR(v, v.getWriteAbleHost());
                        Dialect dialect = BeanFactory.getBean("snow.dao.dialect." + v.getDriver());
                        //如果本地不为空，说明此数据源为关系型数据源，此时必须要求配置文件一定要正确
                        if (Validator.isEmpty(writeAble)) {
                            if (!Validator.isEmpty(dialect))
                                throw new NullArgumentException("可读可写的主机节点不能为空，请机会bean of dataSources");
                            else
                                return;
                        }

                        datasource.put(Mode.Write, writeAble);
                        if (!Validator.isEmpty(readOnly))
                            datasource.put(Mode.Read, readOnly);
                        else
                            datasource.put(Mode.Read, writeAble);
                        if (Validator.isEmpty(key)) {
                            key = defaultDatasource;
                        }
                        if (Logger.isDebugEnable())
                            Logger.debug("名称为[{}]的数据源[{}]初始化完毕", key, k);
                        dataSourceMap.put(key, datasource);
                    });
                }
            } else
                throw new NullArgumentException("找不到任何数据源");
        } else
            throw new NullArgumentException("never fund bean of dataSources");


    }

    /**
     * 创建单个关键字的数据源，包括其中的读与写的数据源
     */
    protected DataSource createDatasourceWR(DataSourceBean dataSourceBean, DataSourceHost dataSourceHost) {
        //JSONArray jsonArray = jsonObject.getJSONArray("ips");
        if (!Validator.isEmpty(dataSourceBean) && !Validator.isEmpty(dataSourceHost)) {
            //String[] serverProperties = s.toString().split(":");
            DruidXADataSource dataSource = new DruidXADataSource();
            Dialect dialect = BeanFactory.getBean("snow.dao.dialect." + dataSourceBean.getDriver());
            if (Validator.isEmpty(dialect))
                return null;

            dataSource.setDriverClassName(dialect.getDriver());
                /*if (serverProperties == null || serverProperties.length < 2)
                    return;*/
            if (Validator.isEmpty(dataSourceHost.getIp()))
                throw new NullArgumentException("ip地址不能为空");

            if (dataSourceHost.getPort() > 0)
                dataSource.setUrl(dialect.getUrl(dataSourceHost.getIp() + ":" + dataSourceHost.getPort(), dataSourceBean.getSchema()));
            else
                dataSource.setUrl(dialect.getUrl(dataSourceHost.getIp(), dataSourceBean.getSchema()));
            dataSource.setUsername(dataSourceHost.getUser());
            dataSource.setPassword(dataSourceHost.getPassword());
            dataSource.setInitialSize(daoConfiguration.getInitialSize());
            dataSource.setMaxActive(daoConfiguration.getMaxActive());
            // dataSource.setMaxIdle(daoConfiguration.getMaxActive());
            dataSource.setMaxWait(daoConfiguration.getMaxWait());
            dataSource.setTestWhileIdle(daoConfiguration.getTestWhileIdle());
            dataSource.setTestOnBorrow(daoConfiguration.getTestOnBorrow());
            dataSource.setTestOnReturn(daoConfiguration.getTestOnReturn());
            dataSource.setValidationQuery(dialect.getValidationQuery());
            dataSource.setValidationQueryTimeout(daoConfiguration.getMaxWait());
            dataSource.setTimeBetweenEvictionRunsMillis(daoConfiguration.getTimeBetweenEvictionRunsMillis());
            // dataSource.setNumTestsPerEvictionRun(daoConfiguration.getMaxActive());
            dataSource.setRemoveAbandoned(daoConfiguration.getRemoveAbandoned());
            dataSource.setRemoveAbandonedTimeout(daoConfiguration.getRemoveAbandonedTimeout());
            dataSource.setLogAbandoned(daoConfiguration.getLogAbandoned());
            dataSource.setPoolPreparedStatements(daoConfiguration.getPoolPreparedStatements());
            dataSource.setMinEvictableIdleTimeMillis(daoConfiguration.getMinEvictableIdleTimeMillis());

            // druid 特有的属性 后期再考虑 重构
            try {
                dataSource.setFilters(daoConfiguration.getFilters());
            } catch (SQLException ex) {
            }

            /*if (serverProperties[2].equals("0"))
                dataSourceMap.put(Mode.Write, dataSource);
            else
                dataSourceMap.put(Mode.Read, dataSource);

            if (dataSourceMap.get(Mode.Read) == null)
                dataSourceMap.put(Mode.Read, dataSourceMap.get(Mode.Write));*/
            return dataSource;
        }
        return null;
    }
}
