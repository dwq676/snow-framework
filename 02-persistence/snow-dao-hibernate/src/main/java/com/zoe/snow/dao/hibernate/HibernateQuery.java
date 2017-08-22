package com.zoe.snow.dao.hibernate;

import com.zoe.snow.dao.orm.query.Query;
import com.zoe.snow.dao.orm.query.QueryImpl;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.util.Validator;
import org.hibernate.procedure.ParameterMisuseException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Hibernate检索构造器。用于构造HibernateORM检索语句。
 *
 * @author lpw
 */
@Repository("snow.dao.query.hibernate")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class HibernateQuery extends QueryImpl implements Query {

    public HibernateQuery() {
        byOrm = true;
    }

    /**
     * 检索构造器。     *
     *
     * @see QueryImpl
     */
    /*public <T extends Model> HibernateQuery(Class<T> modelClass) {
        this.whereBuffer = new StringBuffer();

        if (modelClass == null)
            throw new NullPointerException("Model类不允许为空！");

        this.modelClass = modelClass;
    }*/
    @Override
    public String toSql() {
        return getQuerySql(false).toString();
    }

    protected StringBuilder getQuerySql(boolean isGettingCount) {
        StringBuilder hql = new StringBuilder();
        //aliasMap = new ConcurrentHashMap<>();
        //tableNameAlias = new ConcurrentHashMap<>();
        StringBuffer selectBuffer = new StringBuffer();
        if (!Validator.isEmpty(this.getSelect())) {
            if (!Validator.isEmpty(this.getTo())) {
                String[] selects = this.getSelect().split(",");
                for (int i = 0; i < selects.length; i++) {
                    selectBuffer.append(selects[i]);
                    selectBuffer.append(" as ");
                    selectBuffer.append(selects[i]);
                    if (selects.length > 1 && i < selects.length - 1) {
                        selectBuffer.append(",");
                    }
                }
                hql.append("select ").append(selectBuffer.toString());
            }
        }
        /*boolean theFirst = false;
        int ndx = 0;
        if (minOrMaxFieldMap.keySet().size() > 0) {
            if (selectBuffer.length() == 0) {
                theFirst = true;
                hql.append("select ");
            }
            for (String key : minOrMaxFieldMap.keySet()) {
                if (theFirst && ndx == 0)
                    hql.append(minOrMaxFieldMap.get(key));
                else
                    hql.append(",").append(minOrMaxFieldMap.get(key));
                ndx++;
            }
        }*/
        from(hql.append(" FROM "), isGettingCount);
        String whereHql = where(hql);

        for (String k : queryContext.keySet()) {
            if (k.startsWith("$")) {
                String v = queryContext.get(k).toString();
                v = v.toString().replace("$WHERE", whereHql);
                String hqlStr = hql.toString().replace(k, v.toString());
                hql = new StringBuilder();
                hql.append(hqlStr);
                List<Object> a = new ArrayList<>();
                a.addAll(this.args);
                a.forEach(this.args::add);
            }
        }

        String orderString = this.getOrder();
        for (Class<? extends Model> k : tableNameAlias.keySet()) {
            if (!Validator.isEmpty(this.getGroup()))
                this.group = this.group.replace(k.getSimpleName(), tableNameAlias.get(k));
            if (!Validator.isEmpty(orderString))
                orderString = orderString.replace(k.getSimpleName(), tableNameAlias.get(k));
        }

        if (!Validator.isEmpty(this.getGroup()))
            hql.append(" GROUP BY ").append(this.getGroup());
        if (!Validator.isEmpty(orderString))
            hql.append(" ORDER BY ").append(orderString);
        /*if (!Validator.isEmpty(this.getQueryContext().get("order")))
            hql.append(this.getQueryContext().get("order"));*/

        return hql;
    }

    protected String where(StringBuilder hql) {
        StringBuffer whereHql = new StringBuffer();
        if (!Validator.isEmpty(this.getWhere())) {
            String whereStr = this.getWhere();
            String whereStrWithout$ = getWhereWithOut$();
            for (Class<? extends Model> k : tableNameAlias.keySet()) {
                whereStr = whereStr.replace(k.getSimpleName(), tableNameAlias.get(k));
                whereStrWithout$ = whereStrWithout$.replace(k.getSimpleName(), "").replace(".", "");
            }
            hql.append(" WHERE (").append(whereStr).append(")");
            whereHql.append(" WHERE (").append(whereStrWithout$).append(")");
        }
        /*else
            hql.append(" WHERE ");*/
        boolean starting = false;

        for (String k : this.getQueryContext().keySet()) {
            if (k.startsWith("$"))
                continue;
            StringBuffer innerBuff = new StringBuffer();
            if (starting || !Validator.isEmpty(this.getWhere()))
                innerBuff.append(" and ");
            else {
                if (!starting)
                    innerBuff.append(" WHERE ");
            }
            innerBuff.append(k).append(Criterion.Equals.getType());
            starting = true;
            hql.append(innerBuff);
        }
        //hql.append(whereHql);
        return whereHql.toString();
    }

    protected StringBuilder from(StringBuilder hql, boolean isGettingCount) {
        Class<? extends Model> classZ = this.getFrom();
        if (Validator.isEmpty(classZ))
            throw new ParameterMisuseException("can not get modelClass");
        hql.append(classZ.getSimpleName());

        //tableNameAlias.put(this.getFrom(), "t0");
        hql.append(" as ").append(tableNameAlias.get(this.getFrom()));
        if (!Validator.isEmpty(modelTables)) {
            join(hql, isGettingCount);
        }
        return hql;
    }

}
