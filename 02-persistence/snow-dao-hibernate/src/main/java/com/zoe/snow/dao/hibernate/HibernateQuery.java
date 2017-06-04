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
        if (!Validator.isEmpty(this.getSelect())) {
            if (!Validator.isEmpty(this.getTo())) {
                String[] selects = this.getSelect().split(",");
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < selects.length; i++) {
                    stringBuffer.append(selects[i]);
                    stringBuffer.append(" as ");
                    stringBuffer.append(selects[i]);
                    if (selects.length > 1 && i < selects.length - 1) {
                        stringBuffer.append(",");
                    }
                }
                hql.append("select ").append(stringBuffer.toString());
            }
        }
        from(hql.append(" FROM "), isGettingCount);
        where(hql);

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
        if (!Validator.isEmpty(this.getQueryContext().get("order")))
            hql.append(this.getQueryContext().get("order"));

        return hql;
    }

    protected void where(StringBuilder hql) {
        if (!Validator.isEmpty(this.getWhere())) {
            String whereStr = this.getWhere();
            for (Class<? extends Model> k : tableNameAlias.keySet()) {
                whereStr = whereStr.replace(k.getSimpleName(), tableNameAlias.get(k));
            }
            hql.append(" WHERE (").append(whereStr).append(")");
        }
        /*else
            hql.append(" WHERE ");*/
        boolean starting = false;

        for (String k : this.getQueryContext().keySet()) {
            if (starting || !Validator.isEmpty(this.getWhere()))
                hql.append(" and ");
            else {
                if (!starting)
                    hql.append(" WHERE ");
            }
            hql.append(k).append(Criterion.Equals.getType());
            // query.getArgs().add(query.getQueryContext().get(k));
            starting = true;
        }
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
