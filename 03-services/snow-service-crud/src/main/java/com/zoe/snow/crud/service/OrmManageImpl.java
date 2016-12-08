package com.zoe.snow.crud.service;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.bean.ContextRefreshedListener;
import com.zoe.snow.conf.CrudConfiguration;
import com.zoe.snow.dao.orm.Orm;
import com.zoe.snow.dao.orm.Query;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * CrudOrmManageImpl
 *
 * @author Dai Wenqing
 * @date 2016/3/25
 */
@Component("snow.crud.dao.orm.manage")
public class OrmManageImpl implements OrmManage, ContextRefreshedListener {

    @Autowired(required = false)
    private Set<Orm> ormSet;
    @Autowired(required = false)
    private Set<Query> querySet;

    @Autowired
    private CrudConfiguration crudConfiguration;

    private Orm<Query> orm;

    @Override
    public Orm<Query> getOrm() {
        return orm;
    }

    @Override
    public Orm<Query> getOrm(String ormName) {
        if (Validator.isEmpty(ormName)) return orm;
        for (Orm o : ormSet) {
            if (o.getOrmName().equals(ormName))
                return o;
        }
        return orm;
    }

    @Override
    public Query getQuery() {
        return BeanFactory.getBean("snow.dao.query." + crudConfiguration.getOrmName());
    }

    @Override
    public Query getQuery(String ormName) {
        if (Validator.isEmpty(ormName)) return BeanFactory.getBean("snow.dao.query." + crudConfiguration.getOrmName());
        return BeanFactory.getBean("snow.dao.query." + ormName);
    }

    @Override
    public int getContextRefreshedSort() {
        return 6;
    }

    @Override
    public void onContextRefreshed() {
        if (ormSet != null) {
            ormSet.forEach(o -> {
                if (o.getOrmName().equals(crudConfiguration.getOrmName())) {
                    orm = o;
                }
            });
        }
        /*
         * if (Logger.isDebugEnable()) Logger.debug("orm框架初始化完毕，使用的orm是{}",
         * orm.getOrmName());
         */
    }
}
