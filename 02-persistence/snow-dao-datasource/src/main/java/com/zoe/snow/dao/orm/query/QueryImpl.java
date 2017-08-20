package com.zoe.snow.dao.orm.query;

import com.zoe.snow.dao.orm.context.OrmContextImpl;
import com.zoe.snow.dao.orm.context.WhereContext;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.JoinType;
import com.zoe.snow.model.enums.Operator;
import com.zoe.snow.model.enums.OrderBy;
import com.zoe.snow.model.mapper.ModelTables;
import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Validator;
import org.hibernate.procedure.ParameterMisuseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 查询基类
 *
 * @author Dai Wenqing
 * @date 2016/9/2
 */
@MappedSuperclass
public class QueryImpl extends OrmContextImpl implements Query {
    @Autowired
    protected ModelTables modelTables;
    //@Value("${platform}:")
    //protected Map<String, String> aliasMap = new ConcurrentHashMap<>();

    @Override
    public Query select(String select) {
        this.select = select;
        return this;
    }

    @Override
    public Query schema(String schema) {
        this.schema = schema;
        return this;
    }

    @Override
    public Query from(Class<? extends Model> from) {
        this.fromModelClass = from;
        this.originFromModelClass = from;
        this.tableNameAlias.put(from, "t" + (this.tableNameAlias.size() + 1));
        /*if (as.length > 0)
            this.as = as[0];*/
        return this;
    }

    @Override
    public Query from(String type) {
        this.formType = type;
        return this;
    }

    @Override
    public Query set(String column, Object value) {
        this.set.put(column, value);
        return this;
    }

    @Override
    public Query to(Class<?> to) {
        this.to = to;
        return this;
    }

    @Override
    public Query join(Class<? extends Model> classZ, JoinType joinType) {
        if (!Validator.isEmpty(classZ))
            joinClasses.put(classZ, joinType);
        return this;
    }

    @Override
    public Query datasource(String datasource) {
        this.datasource = datasource;
        return this;
    }

    @Override
    public Query where(String column, Object value, Operator... operator) {
        return where(column, Criterion.Equals, value, operator);
    }

    /**
     * 设置WHERE片段。
     *
     * @param column 字段名。
     * @return 当前Query实例。
     */
    @Override
    public Query where(String column, Criterion criterion, Object value, Operator... operator) {
        if (Validator.isEmpty(column))
            return this;
        if (Validator.isEmpty(value))
            if (criterion != Criterion.IsNull && criterion != Criterion.IsNotNUll)
                return this;

        WhereContext whereContext = new WhereContext();
        whereContext.setCriterion(criterion);
        whereContext.setKey(column);
        this.whereContexts.add(whereContext);

        if (operator == null)
            operator = new Operator[]{Operator.And};


        if (this.whereBuffer.length() > 0) {
            if (operator.length < 1) {
                this.whereBuffer.append(" and ");
                whereContext.setOperator(Operator.And);
            } else {
                this.whereBuffer.append(" " + operator[0].getType() + " ");
                whereContext.setOperator(operator[0]);
            }
        }
        String from = this.getFrom() != null ? this.getFrom().getSimpleName() : this.getFromType();
        this.whereBuffer.append(from).append(".");
        if (byOrm || column.equals("id"))
            this.whereBuffer.append(column);
        else if (!Validator.isEmpty(this.getFrom()))
            this.whereBuffer.append(modelTables.get(this.getFrom()).getFields().get(Converter.toFirstUpperCase(column)));
        if (!Validator.isEmpty(criterion))
            this.whereBuffer.append(criterion.getType());
        boolean hasAdd = false;
        if (criterion == Criterion.In || criterion == Criterion.NotIN) {
            int count = addArgs(value, Criterion.In, whereContext);
            this.whereBuffer.append(" (");
            for (int i = 0; i < count; i++) {
                if (i > 0)
                    this.whereBuffer.append(",");
                this.whereBuffer.append("?");
            }
            this.whereBuffer.append(") ");
            hasAdd = true;
        } else if (criterion == Criterion.Like) {
            if (value.toString().contains("%"))
                value = value.toString().replace("%", "\\%");
            else if (value.toString().equals("_"))
                value = value.toString().replace("_", "\\_");
            value = "%" + value + "%";
        } else if (criterion == Criterion.StartsWith) {
            value = value + "%";
        } else if (criterion == Criterion.EndsWith) {
            value = "%" + value;
        }
        if (!hasAdd)
            addArgs(value, criterion, whereContext);
        return this;
    }

    @Override
    public Query where(Supplier<String> where) {
        return where(where, null);
    }

    @Override
    public Query where(Supplier<String> where, Object value, Operator... operators) {
        String whereStr = where.get();
        if (!Validator.isEmpty(whereStr)) {

            if (operators == null)
                operators = new Operator[]{Operator.And};

            if (operators.length > 0)
                this.whereBuffer.append(operators[0].getType());
            else if (whereBuffer.length() > 0) {
                this.whereBuffer.append(" and ");
            } else
                this.whereBuffer.append(" ");
            this.whereBuffer.append(whereStr);
            this.whereBuffer.append(" ");
            if (whereStr.toLowerCase().indexOf("in") > -1)
                addArgs(value, Criterion.In, null);
            else if (whereStr.toLowerCase().indexOf("between") > -1)
                addArgs(value, Criterion.Between, null);
            else
                addArgs(value, Criterion.Equals, null);
        }
        return this;
    }

    /**
     * @param value
     * @param criterion
     * @return
     */
    private int addArgs(Object value, Criterion criterion, WhereContext whereContext) {
        int count = value == null ? 0 : 1;
        if (!Validator.isEmpty(value)) {
            Object[] values = null;
            if (value.getClass().isArray()) {
                values = (Object[]) value;
                count = values.length;
                Arrays.asList(values).forEach(this.args::add);
                if (whereContext != null)
                    Arrays.asList(values).forEach(whereContext::setValue);
            } else {
                if (criterion == Criterion.Between || criterion == Criterion.In) {
                    String[] valueSplit = value.toString().split(",");
                    count = valueSplit.length;
                    for (String v : valueSplit) {
                        this.args.add(v);
                        if (whereContext != null)
                            whereContext.setValue(v);
                    }
                } else {
                    this.args.add(value);
                    if (whereContext != null)
                        whereContext.setValue(value);
                }
            }
        }
        if (criterion == Criterion.Between) {
            if (count != 2)
                throw new ParameterMisuseException("参数个数不对，对于Between and 需要2个参数");
        }
        return count;
    }


    /**
     * 设置GROUP BY片段。为空则不分组。
     *
     * @param group GROUP BY片段。
     * @return 当前Query实例。
     */
    @Override
    public Query group(String group) {
        this.group = group;
        return this;
    }

    /**
     * 设置ORDER BY片段。为空则不排序。
     *
     * @param order ORDER BY片段。
     * @return 当前Query实例。
     */
    @Override
    public Query order(String order, OrderBy... orderBy) {
        if (Validator.isEmpty(order))
            return this;
        if (this.order.length() > 0)
            this.order.append(",");
        OrderBy defaultOrderBy = OrderBy.Asc;
        if (orderBy.length > 0)
            defaultOrderBy = orderBy[0];
        this.order.append(tableNameAlias.get(this.fromModelClass)).append(".").append(order).append(" " + defaultOrderBy.getType() + " ");
        return this;
    }

    @Override
    public Query order(String order, String orderBy) {
        if (Validator.isEmpty(order))
            return this;
        if (this.order.length() > 0)
            this.order.append(",");
        OrderBy defaultOrderBy = OrderBy.Asc;

        String[] orders = order.split(",");
        OrderBy[] bys = new OrderBy[orders.length];

        for (int i = 0; i < bys.length; i++) {
            bys[i] = OrderBy.Asc;
        }
        if (!Validator.isEmpty(orderBy)) {
            String[] b = orderBy.split(",");
            for (int i = 0; i < b.length; i++) {
                bys[i] = OrderBy.get(b[i]);
            }
        }

        for (int i = 0; i < orders.length; i++) {
            this.order(orders[i], bys[i]);
        }
        return this;
    }

    /**
     * 设置当前显示的页码。只有当size大于0时页码数才有效。如果页码小于1，则默认为1。
     *
     * @param page 当前显示的页码。
     * @return 当前Query实例。
     */
    @Override
    public Query paging(int page, int size) {
        this.page = page;
        this.size = size;
        return this;
    }

    @Override
    public String toSql() {
        return "";
    }

    /**
     * 关联查询
     *
     * @param hql
     * @param isGettingCount
     */
    protected void join(StringBuilder hql, boolean isGettingCount) {
        //int ndx = 1;
        //获取包含JoinColumn注解的所有类型
        Map<Class<? extends Model>, JoinColumn> joinColumnMap = modelTables.get(this.getFrom()).getJoinColumn();
        //对类型设置别名
        for (Class<? extends Model> c : this.getJoinClasses().keySet()) {
            if (tableNameAlias.containsKey(c))
                continue;
            tableNameAlias.put(c, "t" + tableNameAlias.size() + 1);
        }
        //记录已经做了关联的类型
        List<Class<? extends Model>> hasJoinClassList = new ArrayList<>();
        hasJoinClassList.add(this.getFrom());
        if (this.getJoinClasses().size() > 0) {
            for (Class<? extends Model> c : this.getJoinClasses().keySet()) {
                JoinColumn joinColumn = joinColumnMap.get(c);
                Class<? extends Model> joinModelClass = this.getFrom();
                if (Validator.isEmpty(joinColumn))
                    joinModelClass = getJoinModelClass(c);
                join(joinModelClass, c, hql, this.getJoinClasses().get(c), joinColumn, hasJoinClassList, isGettingCount);
            }
        }
    }

    private Class<? extends Model> getJoinModelClass(Class<? extends Model> forJoinClass) {
        for (Class<? extends Model> c : this.getJoinClasses().keySet()) {
            Map<Class<? extends Model>, JoinColumn> joinColumnMap = modelTables.get(c).getJoinColumn();
            JoinColumn joinColumn = joinColumnMap.get(forJoinClass);
            if (!Validator.isEmpty(joinColumn))
                return c;
        }
        return null;
    }

    //  FROM UserModel as t0 inner join  open t0.school as t1 WHERE

    private void join(Class<? extends Model> OrinClass, Class<? extends Model> forJoinClass,
                      StringBuilder hql, JoinType joinType, JoinColumn forJoinColumn,
                      List<Class<? extends Model>> hasJoinClassList, boolean isGettingCount) {
        // 此表已经做了关联
        if (hasJoinClassList.contains(forJoinClass))
            return;
        // 原始表都未做关联，需要先关联原表
        if (!hasJoinClassList.contains(OrinClass))
            join(this.getFrom(), OrinClass, hql, this.getJoinClasses().get(OrinClass)
                    , forJoinColumn, hasJoinClassList, isGettingCount);
        if (!Validator.isEmpty(OrinClass)) {
            String fetch = " ";
            if (!isGettingCount)
                fetch = " fetch ";
            String forAlias = tableNameAlias.get(forJoinClass);
            String originAlias = tableNameAlias.get(OrinClass);
            //aliasMap.put(forJoinClass.getSimpleName(), tableNameAlias.get(forJoinClass));
            hql.append(joinType.getType());
            if (this.byOrm)
                hql.append(fetch);
            hql.append(tableNameAlias.get(OrinClass)).append(".");
            if (this.byOrm)
                hql.append(Converter.toFirstLowerCase(forJoinClass.getSimpleName()));
            else
                hql.append(forJoinClass.getAnnotation(Table.class).name());
            hql.append(" as ").append(forAlias);
            if (!this.byOrm) {
                hql.append(" on ");
                hql.append(forAlias).append(".id =").append(originAlias).append(".").append(forJoinColumn.name());
            }
            hasJoinClassList.add(forJoinClass);
        }
    }

}
