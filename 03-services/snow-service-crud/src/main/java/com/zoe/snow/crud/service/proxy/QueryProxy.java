package com.zoe.snow.crud.service.proxy;

import com.zoe.snow.crud.QueryManager;
import com.zoe.snow.crud.service.QueryService;
import com.zoe.snow.dao.orm.OrmContext;
import com.zoe.snow.dao.orm.Query;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.PageList;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.JoinType;
import com.zoe.snow.model.enums.Operator;
import com.zoe.snow.model.enums.OrderBy;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

/**
 * 提供一个全方位的，更灵活的查询操作
 *
 * @author Dai Wenqing
 * @date 2016/3/31
 */
@Component("snow.crud.service.proxy.query")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class QueryProxy extends ProxySupport {
    private boolean excludeDomain = false;

    public QueryProxy setQueryName(String queryName) {
        this.queryName = queryName;
        return this;
    }

    public QueryProxy select(String select) {
        getQuery().select(select);
        return this;
    }

    public QueryProxy from(Class<? extends Model> from, String... as) {
        getQuery().from(from);
        return this;
    }

    public QueryProxy to(Class<?> to) {
        getQuery().to(to);
        return this;
    }

    public QueryProxy join(Class<? extends Model> classZ, JoinType joinType) {
        getQuery().join(classZ, joinType);
        return this;
    }

    public QueryProxy where(String column, Criterion criterion, Object value, Operator... operator) {
        getQuery().where(column, criterion, value, operator);
        return this;
    }

    public QueryProxy where(String column, Object value, Operator... operator) {
        where(column, Criterion.Equals, value, operator);
        return this;
    }

    public QueryProxy where(Supplier<String> where) {
        getQuery().where(where);
        return this;
    }

    public QueryProxy where(Supplier<String> where, Object args, Operator... operators) {
        getQuery().where(where, args);
        return this;
    }

    public QueryProxy order(String order, OrderBy... orderBy) {
        getQuery().order(order, orderBy);
        return this;
    }

    public QueryProxy group(String group) {
        getQuery().group(group);
        return this;
    }

    public QueryProxy setExcludeDomain(boolean excludeDomain) {
        this.excludeDomain = excludeDomain;
        return this;
    }

    public QueryProxy paging(int page, int size) {
        getQuery().paging(page, size);
        return this;
    }

    /**
     * 获取sql语句
     *
     * @return
     */
    public String getSql() {
        return getQuery().toSql();
    }

    public Object[] getArgs() {
        OrmContext ormContext = OrmContext.class.cast(getQuery());
        return ormContext.getArgs().toArray();
    }

    /*public <T extends Model> PageList<T> list(Object... args) {
        return list(false, args);
    }*/

    public <T extends Model> PageList<T> pageList() {
        // QueryInfo queryInfo = QueryInfo.class.cast(query);
        return queryService.list(getQuery(), excludeDomain);
    }

    public <T extends Model> List<T> list() {
        //pageList 一定不为空
        return (List<T>) pageList().getList();
    }

    /*public <T extends Model> T one(Object... args) {
        return one(false, args);
    }*/

    public <T extends Model> T one() {
        return queryService.one(getQuery(), excludeDomain);
    }

    /*public <T extends Model> PageList<T> all(Object... args) {
        return all(false, args);
    }*/

    public <T extends Model> PageList<T> all() {
        // QueryInfo queryInfo = QueryInfo.class.cast(query);
        return queryService.all(getQuery(), excludeDomain);
    }

    public int count() {
        return queryService.count(getQuery(), excludeDomain);
    }
}
