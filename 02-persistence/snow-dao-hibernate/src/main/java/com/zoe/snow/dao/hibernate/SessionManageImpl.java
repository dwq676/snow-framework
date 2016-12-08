package com.zoe.snow.dao.hibernate;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.bean.ContextRefreshedListener;
import com.zoe.snow.dao.ConnectionSupport;
import com.zoe.snow.dao.DataSourceManager;
import com.zoe.snow.dao.Mode;
import com.zoe.snow.dao.hibernate.dialect.HDialect;
import com.zoe.snow.log.Logger;
import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Validator;
import org.apache.commons.lang.NullArgumentException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话管理 获取会话
 *
 * @author Dai Wenqing
 * @date 2016/1/23
 */
@Repository("snow.dao.hibernate.session")
public class SessionManageImpl extends ConnectionSupport<Session> implements SessionManage, ContextRefreshedListener {
    /*private HDialect hDialect;
    @Autowired
    private Set<HDialect> hDialects;*/
    protected Map<String, Map<Mode, LocalSessionFactoryBean>> localSessionFactoryBeanMap = new ConcurrentHashMap<>();
    @Autowired
    private HibernateConfigBean hibernateConfigBean;

    @Override
    public int getContextRefreshedSort() {
        return 4;
    }

    @Override
    public void onContextRefreshed() {
       /* HibernateConfiguration hibernateConfiguration = BeanFactory.getBean(HibernateConfiguration.class);
        if (hDialects != null) {
            hDialects.forEach(h -> {
                if (h.getName().equals(hibernateConfiguration.getDriver())) {
                    hDialect = h;
                }
            });
        }*/
        createAllSessionFactoryBean();
    }

    protected void createAllSessionFactoryBean() {
        Map<String, Map<Mode, DataSource>> datasourceMap = DataSourceManager.getDatasourceMap();
        if (Validator.isEmpty(datasourceMap))
            throw new NullArgumentException("datasource");
        datasourceMap.forEach((k, v) -> {
            Map<Mode, LocalSessionFactoryBean> sessionFactoryBeanMap = new ConcurrentHashMap<>();
            localSessionFactoryBeanMap.put(k, sessionFactoryBeanMap);

            v.forEach((ik, iv) -> {
                LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
                sessionFactoryBeanMap.put(ik, localSessionFactoryBean);

                //HibernateConfiguration hibernateConfiguration = BeanFactory.getBean(HibernateConfiguration.class);
                // localSessionFactoryBean = new LocalSessionFactoryBean();
                localSessionFactoryBean.setHibernateProperties(createProperties(k));
                localSessionFactoryBean.setPackagesToScan(hibernateConfigBean.getPackageToScan());
                localSessionFactoryBean.setDataSource(iv);
                try {
                    localSessionFactoryBean.afterPropertiesSet();
                    Logger.info("Mode值为：[{}],datasource值为：[{}]的sessionFactoryBean初始化完毕。", ik, k);

                } catch (IOException e) {
                    Logger.error(e, "初始化Hibernate环境[{}]时发生异常！", Converter.toString(hibernateConfigBean.getPackageToScan()));
                }
            });

        });

    }

    protected Properties createProperties(String key) {
        //HibernateConfiguration hibernateConfiguration = BeanFactory.getBean(HibernateConfiguration.class);
        Properties properties = new Properties();
        properties.put("hibernate.dialect", getDialect(key).getHibernateDialect());
        if (hibernateConfigBean == null) {
            properties.put("hibernate.show_sql", false);
            properties.put("hibernate.cache.use_second_level_cache", false);
        } else {
            properties.put("hibernate.show_sql", hibernateConfigBean.getShowSql());
            properties.put("hibernate.cache.use_second_level_cache", hibernateConfigBean.getPackageToScan());
        }
        properties.put("hibernate.current_session_context_class", "thread");
        return properties;
    }

    @Override
    public HDialect getDialect(String... key) {
        String datasource = "";
        if (key.length > 0) {
            if (!Validator.isEmpty(key[0]))
                datasource = key[0];
            else
                datasource = DataSourceManager.getDefaultDatasourceKey();
        } else
            datasource = DataSourceManager.getDefaultDatasourceKey();
        return BeanFactory.getBean("snow.dao.hibernate.dialect." + DataSourceManager.getDataSourceBean(datasource).getDriver());
    }

    @Override
    public Session open(String datasource, Mode mode) {
        Session s = null;

        // 获得的Session会在事务关闭或者回滚时,需要手动去关闭
        s = localSessionFactoryBeanMap.get(datasource).get(mode).getObject().openSession();
        Logger.debug("成功建立session，会话模式为：[{}]，数据源为：[{}]", mode, datasource);

        if (transactional.get() != null) {
            if (transactional.get() && mode == Mode.Write) {
                if (!s.getTransaction().isActive())
                    s.beginTransaction();
            }
        }
        return s;
    }

    @Override
    public Session fetch(List<Session> caches, String datasource, Mode mode) {
        if (caches == null)
            return null;
        try {
            for (Session s : caches) {
                if (!Validator.isEmpty(s) && s.isOpen() && s.isConnected()) {
                    return s;
                }
            }
        } catch (Exception e) {
            Logger.error(e, "session建立过程出现了异常！");
        }
        return null;
    }

    @Override
    public void rollback(Session session) {
        if (!Validator.isEmpty(session))
            session.getTransaction().rollback();
    }

    @Override
    public void commit(Session session) {
        if (!Validator.isEmpty(session)) {
            if (session.isOpen()) {
                if (session.getTransaction().isActive()) {
                    session.flush();
                    session.getTransaction().commit();
                    if (Logger.isDebugEnable())
                        Logger.debug("进行了事务提交！");
                }
            }
        }
    }

    @Override
    public void close(Session session) {
        if (!Validator.isEmpty(session))
            if (session.isOpen())
                session.close();
    }

}
