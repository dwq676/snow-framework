/**
 *
 */
package com.zoe.snow.dao.lite;

import com.zoe.snow.dao.orm.Query;
import com.zoe.snow.dao.orm.QueryImpl;
import com.zoe.snow.model.Model;
import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

/**
 * Lite检索构造器。用于构造非级联ORM检索语句。
 *
 * @author lpw
 * @see com.zoe.snow.dao.orm.QueryImpl
 */
@Repository("snow.dao.query.lite")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LiteQuery extends QueryImpl implements Query {
    /*private String select;
    private String from;
    private StringBuffer stringBuffer = new StringBuffer();*/

    public LiteQuery() {
    }

    /**
     * 检索构造器。
     */
    /*public <T extends Model> LiteQuery(Class<T> modelClass) {
        if (modelClass == null)
            throw new NullPointerException("Model类不允许为空！");

        this.modelClass = modelClass;
    }*/
    @Override
    public String toSql() {
        return getQuerySql(this);
    }

    protected String getQuerySql(LiteQuery query) {
        StringBuilder querySql = new StringBuilder().append("SELECT ");
        //tableNameAlias = new ConcurrentHashMap<>();
        if (Validator.isEmpty(query.getFrom()))
            return "";
        if (Validator.isEmpty(this.select))
            querySql.append("*");
        else {
            String[] selects = this.select.split(",");
            StringBuffer selectBuffer = new StringBuffer();
            for (int i = 0; i < selects.length; i++) {
                if (i > 0 && selectBuffer.length() > 0) selectBuffer.append(",");
                if ("id".equals(selects[i])) {
                    selectBuffer.append(selects[i]);
                    continue;
                }
                String s = modelTables.get(query.getFrom()).getFields().get(Converter.toFirstUpperCase(selects[i]));
                if (!Validator.isEmpty(s))
                    selectBuffer.append(s);
            }
            querySql.append(selectBuffer);
        }
        querySql.append(" FROM ").append(query.getFrom().getAnnotation(Table.class).name());
        querySql.append(" as ").append(tableNameAlias.get(this.getFrom()));
        join(querySql, false);

        if (!Validator.isEmpty(query.getWhere())) {
            String whereStr = this.getWhere();
            for (Class<? extends Model> k : tableNameAlias.keySet()) {
                whereStr = whereStr.replace(k.getSimpleName(), tableNameAlias.get(k));
            }
            querySql.append(" WHERE ").append(whereStr);
        }
        if (!Validator.isEmpty(query.getGroup()))
            querySql.append(" GROUP BY ").append(query.getGroup());
        if (!Validator.isEmpty(query.getOrder()))
            querySql.append(" ORDER BY ").append(query.getOrder());
        return querySql.toString();
    }
}
