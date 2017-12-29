package com.zoe.snow.crud.service.proxy;

import com.zoe.snow.crud.QueryManager;
import com.zoe.snow.crud.service.ExecuteService;
import com.zoe.snow.crud.service.QueryService;
import com.zoe.snow.dao.orm.query.Query;
import com.zoe.snow.log.Logger;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.MappedSuperclass;

/**
 * ProxySupport
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/2/27
 */
@MappedSuperclass
public class ProxySupport {
    @Autowired
    protected QueryManager queryManager;
    @Autowired
    protected QueryService queryService;
    @Autowired
    protected ExecuteService executeService;
    protected boolean excludeDomain = false;

    /*public void setQuery(Query query) {
         this.query = query;
     }*/
    protected String queryName;
    protected Query query;

    protected Query getQuery() {
        if (query == null) {
            Logger.debug("正使用的query name为：" + queryName);
            if (!Validator.isEmpty(queryName))
                query = queryManager.getQuery(queryName);
            else
                query = queryManager.getQuery();
        }
        //Logger.info(query.toString());
        return query;
    }
}
