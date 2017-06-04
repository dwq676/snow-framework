package com.zoe.snow.crud;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.conf.CrudConfiguration;
import com.zoe.snow.dao.orm.query.Query;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * QueryManager
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/2/27
 */
@Component("snow.crud.query.manager")
public class QueryManager {
    @Autowired
    private CrudConfiguration crudConfiguration;

    public Query getQuery() {
        return BeanFactory.getBean("snow.dao.query." + crudConfiguration.getOrmName());
    }

    public Query getQuery(String ormName) {
        if (Validator.isEmpty(ormName)) return BeanFactory.getBean("snow.dao.query." + crudConfiguration.getOrmName());
        return BeanFactory.getBean("snow.dao.query." + ormName);
    }
}
