package com.zoe.snow.crud.service.proxy;

import com.zoe.snow.crud.service.QueryService;
import com.zoe.snow.dao.orm.OrmContext;
import com.zoe.snow.dao.orm.Query;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.PageList;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.JoinType;
import com.zoe.snow.model.enums.Operator;
import com.zoe.snow.model.enums.OrderBy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 提供一个全方位的，更灵活的查询操作
 *
 * @author Dai Wenqing
 * @date 2016/3/31
 */
//@Component("snow.crud.service.proxy.query")
public class QueryProxy {
    private Query query;
    @Autowired
    private QueryService queryService;
    private boolean excludeDomain = false;

    public QueryProxy(Query query, QueryService queryService) {
        this.query = query;
        this.queryService = queryService;
    }

   /*public void setQuery(Query query) {
        this.query = query;
    }*/

    public QueryProxy select(String select) {
        query.select(select);
        return this;
    }

    public QueryProxy from(Class<? extends Model> from, String... as) {
        query.from(from);
        return this;
    }

    public QueryProxy to(Class<?> to) {
        query.to(to);
        return this;
    }

    public QueryProxy join(Class<? extends Model> classZ, JoinType joinType) {
        query.join(classZ, joinType);
        return this;
    }

    public QueryProxy where(String column, Criterion criterion, Object value, Operator... operator) {
        query.where(column, criterion, value, operator);
        return this;
    }

    public QueryProxy where(String column, Object value, Operator... operator) {
        where(column, Criterion.Equals, value, operator);
        return this;
    }

    public QueryProxy where(String where) {
        query.where(where);
        return this;
    }

    public QueryProxy where(String where, Object[] args, Operator... operators) {
        query.where(where, args);
        return this;
    }

    public QueryProxy order(String order, OrderBy... orderBy) {
        query.order(order, orderBy);
        return this;
    }

    public QueryProxy group(String group) {
        query.group(group);
        return this;
    }

    public QueryProxy setExcludeDomain(boolean excludeDomain) {
        this.excludeDomain = excludeDomain;
        return this;
    }

    public QueryProxy paging(int page, int size) {
        query.paging(page, size);
        return this;
    }

    /**
     * 获取sql语句
     *
     * @return
     */
    public String getSql() {
        return query.toSql();
    }

    public Object[] getArgs() {
        OrmContext ormContext = OrmContext.class.cast(query);
        return ormContext.getArgs().toArray();
    }

    /*public <T extends Model> PageList<T> list(Object... args) {
        return list(false, args);
    }*/

    public <T extends Model> PageList<T> list(Object... args) {
        // QueryInfo queryInfo = QueryInfo.class.cast(query);
        return queryService.list(query, excludeDomain, args);
    }

    /*public <T extends Model> T one(Object... args) {
        return one(false, args);
    }*/

    public <T extends Model> T one(Object... args) {
        return queryService.one(query, excludeDomain, args);
    }

    /*public <T extends Model> PageList<T> all(Object... args) {
        return all(false, args);
    }*/

    public <T extends Model> PageList<T> all(Object... args) {
        // QueryInfo queryInfo = QueryInfo.class.cast(query);
        return queryService.all(query, excludeDomain, args);
    }

    public int count(Object... args) {
        return queryService.count(query, excludeDomain, args);
    }
}
