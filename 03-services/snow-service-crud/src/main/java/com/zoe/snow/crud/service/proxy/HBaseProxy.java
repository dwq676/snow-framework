package com.zoe.snow.crud.service.proxy;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.crud.QueryManager;
import com.zoe.snow.dao.elastic.ElasticDao;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.Operator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * 非关系型检索代理
 *
 * @author Dai Wenqing
 * @date 2016/9/27
 */
@Component("snow.crud.service.proxy.hbase")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class HBaseProxy extends ProxySupport {
    /*private Query query;
    private ExecuteService executeService;
    private QueryService queryService;*/
    private String ndx;
    @Autowired
    private QueryManager queryManager;

    /*public ElasticProxy(Query query, QueryService queryService, String... index) {
        this.query = query;
        this.queryService = queryService;
        if (index.length > 0) {
            this.query.schema(index[0]);
            this.ndx = index[0];
        }
    }*/

    public HBaseProxy() {
        this.queryName = "hbase";
    }

    public HBaseProxy from(String fromType) {
        getQuery().from(fromType);
        return this;
    }

    public HBaseProxy select(String select) {
        getQuery().select(select);
        return this;
    }

    public HBaseProxy paging(int page, int size) {
        getQuery().paging(page, size);
        return this;
    }

    public HBaseProxy where(String column, Criterion criterion, Object value, Operator... operator) {
        getQuery().where(column, criterion, value, operator);
        return this;
    }

    public HBaseProxy where(String column, Object value, Operator... operator) {
        where(column, Criterion.Equals, value, operator);
        return this;
    }

    public HBaseProxy where(Supplier<String> where) {
        getQuery().where(where);
        return this;
    }

    public JSONObject asJsonObject() {
        return queryService.asJsonObject(getQuery(), "hbase");
    }

    public JSONArray asJsonArray() {
        return queryService.asJsonArray(getQuery(), "hbase");
    }

    /*public boolean save(String type, String json, String... id) {
        return BeanFactory.getBean(ElasticDao.class).save(this.ndx, type, json, id);
    }*/

    /*public boolean update(String type, String id, String json) {
        return BeanFactory.getBean(ElasticDao.class).update(this.ndx, type, id, json);
    }*/
}
