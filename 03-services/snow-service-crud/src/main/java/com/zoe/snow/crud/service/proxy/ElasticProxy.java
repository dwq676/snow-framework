package com.zoe.snow.crud.service.proxy;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.crud.service.ExecuteService;
import com.zoe.snow.crud.service.QueryService;
import com.zoe.snow.dao.elastic.ElasticDao;
import com.zoe.snow.dao.orm.Query;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.Operator;
import com.zoe.snow.util.Validator;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Component;

/**
 * 非关系型检索代理
 *
 * @author Dai Wenqing
 * @date 2016/9/27
 */
//@Component("snow.crud.service.proxy.elastic")
public class ElasticProxy {
    private Query query;
    private ExecuteService executeService;
    private QueryService queryService;
    private String ndx;

    public ElasticProxy(Query query, QueryService queryService, String... index) {
        this.query = query;
        this.queryService = queryService;
        if (index.length > 0) {
            this.query.schema(index[0]);
            this.ndx = index[0];
        }
    }

    public ElasticProxy from(String fromType) {
        query.from(fromType);
        return this;
    }

    public ElasticProxy paging(int page, int size) {
        query.paging(page, size);
        return this;
    }

    public ElasticProxy group(String field) {
        query.group(field);
        return this;
    }


    public ElasticProxy where(String column, Criterion criterion, Object value, Operator... operator) {
        query.where(column, criterion, value, operator);
        return this;
    }

    public ElasticProxy where(String column, Object value, Operator... operator) {
        where(column, Criterion.Equals, value, operator);
        return this;
    }

    public ElasticProxy where(String where) {
        query.where(where);
        return this;
    }

    public JSONArray asJson() {
        return queryService.asJson(query, "elastic");
    }

    public boolean save(String type, String json, String... id) {
        return BeanFactory.getBean(ElasticDao.class).save(this.ndx, type, json, id);
    }

    /*public boolean update(String type, String id, String json) {
        return BeanFactory.getBean(ElasticDao.class).update(this.ndx, type, id, json);
    }*/
}
