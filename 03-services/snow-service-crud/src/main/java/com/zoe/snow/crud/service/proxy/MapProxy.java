package com.zoe.snow.crud.service.proxy;

import com.zoe.snow.crud.CrudServiceHelper;
import com.zoe.snow.dao.orm.query.Query;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.PageList;
import com.zoe.snow.model.enums.Criterion;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 键值对查询代理，提供了一个所有条件为全部为and操作
 *
 * @author Dai Wenqing
 * @date 2016/7/18
 */
@Component("snow.crud.service.proxy.map")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MapProxy extends ProxySupport {
    private Map<String, Criterion> map = new LinkedHashMap<>();
    private int page = -1;
    private int size = -1;
    private Class<? extends Model> classZ;
    private boolean excludeDomain = false;
    private Object[] args;

    /*public MapProxy(Class<? extends Model> classZ, QueryService queryService) {
        this.queryService = queryService;
        this.classZ = classZ;
    }*/

    public MapProxy from(Class<? extends Model> from) {
        this.classZ = from;
        return this;
    }

    /**
     * 添加列条件查询
     *
     * @param column    列名
     * @param criterion 条件
     * @return
     */
    public MapProxy put(String column, Criterion criterion, Object... args) {
        map.put(column, criterion);
        this.args = args;
        return this;
    }

    /**
     * 默认为等值查询
     *
     * @param column
     * @return
     */
    public MapProxy put(String column, Object... args) {
        map.put(column, Criterion.Equals);
        this.args = args;
        return this;
    }

    public MapProxy paging(int page, int size) {
        this.page = page;
        this.size = size;
        return this;
    }

    /*public <T extends Model> PageList<T> list(Object... args) {
        return list(false, args);
    }*/

    public MapProxy setExcludeDomain(boolean excludeDomain) {
        this.excludeDomain = excludeDomain;
        return this;
    }

    public <T> PageList<T> pageList() {
        // QueryInfo queryInfo = QueryInfo.class.cast(query);
        Query query = CrudServiceHelper.mapToQuery(classZ, map, page, size, this.args);
        return queryService.list(query, excludeDomain);
    }

    public <T> List<T> list() {
        //pageList 一定不为空
        List<T> list = new ArrayList<>();
        list.addAll(pageList());
        return list;
    }

    /*public <T extends Model> T one(Object... args) {
        return one(false, args);
    }*/

    public <T> T one() {
        Query query = CrudServiceHelper.mapToQuery(classZ, map, page, size, this.args);
        return queryService.one(query, excludeDomain);
    }

    /*public <T extends Model> PageList<T> all(Object... args) {
        return all(false, args);
    }*/

    public <T> PageList<T> all() {
        Query query = CrudServiceHelper.mapToQuery(classZ, map, page, size, this.args);
        return queryService.all(query, excludeDomain);
    }

}
