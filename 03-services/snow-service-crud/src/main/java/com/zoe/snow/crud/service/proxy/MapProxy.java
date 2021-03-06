package com.zoe.snow.crud.service.proxy;

import com.zoe.snow.crud.service.QueryService;
import com.zoe.snow.dao.orm.Query;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.PageList;
import com.zoe.snow.model.enums.Criterion;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 键值对查询代理，提供了一个所有条件为全部为and操作
 *
 * @author Dai Wenqing
 * @date 2016/7/18
 */
//@Component("snow.crud.service.proxy.map")
public class MapProxy {
    private Map<String, Criterion> map = new LinkedHashMap<>();
    private QueryService queryService;
    private int page = -1;
    private int size = -1;
    private Class<? extends Model> classZ;
    private boolean excludeDomain = false;

    public MapProxy(Class<? extends Model> classZ, QueryService queryService) {
        this.queryService = queryService;
        this.classZ = classZ;
    }

    /**
     * 添加列条件查询
     *
     * @param column    列名
     * @param criterion 条件
     * @return
     */
    public MapProxy put(String column, Criterion criterion) {
        map.put(column, criterion);
        return this;
    }

    /**
     * 默认为等值查询
     *
     * @param column
     * @return
     */
    public MapProxy put(String column) {
        map.put(column, Criterion.Equals);
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

    public <T extends Model> PageList<T> list(Object... args) {
        // QueryInfo queryInfo = QueryInfo.class.cast(query);
        Query query = queryService.mapToQuery(classZ, map, page, size, args);
        return queryService.list(query, excludeDomain);
    }

    /*public <T extends Model> T one(Object... args) {
        return one(false, args);
    }*/

    public <T extends Model> T one(Object... args) {
        Query query = queryService.mapToQuery(classZ, map, page, size, args);
        return queryService.one(query, excludeDomain);
    }

    /*public <T extends Model> PageList<T> all(Object... args) {
        return all(false, args);
    }*/

    public <T extends Model> PageList<T> all(Object... args) {
        Query query = queryService.mapToQuery(classZ, map, page, size, args);
        return queryService.all(query, excludeDomain);
    }
}
