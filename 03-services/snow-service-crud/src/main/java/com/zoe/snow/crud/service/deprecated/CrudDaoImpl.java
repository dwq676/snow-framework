package com.zoe.snow.crud.service.deprecated;

import java.util.List;
import java.util.Map;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.conf.CrudConfiguration;
import com.zoe.snow.crud.OrmManage;
import com.zoe.snow.dao.orm.Query;
import com.zoe.snow.dao.orm.OrmContext;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.PageList;
import com.zoe.snow.model.annotation.Datasource;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.InterventionType;
import com.zoe.snow.model.enums.Operator;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 封装增、删、改、查数据访问层 与orm框架进行耦合，隔离了服务层与orm的关系,
 * <p>
 * 若有多数据源，此对象负责动态设置数据源 datasource
 * <p>
 * 动态创建query
 *
 * @author Dai Wenqing
 * @date 2015/10/11
 */
@Deprecated
@Repository("snow.crud.dao")
public class CrudDaoImpl implements CrudDao {
    @Autowired
    protected OrmManage crudOrmManage;
    @Autowired
    protected CrudConfiguration crudConfiguration;

    @Override
    public <T extends Model> Query query() {
        return BeanFactory.getBean("snow.dao.query." + crudConfiguration.getOrmName());
    }

    private <T extends Model> String getDatasource(Class<T> modelClass) {
        Datasource datasource = modelClass.getAnnotation(Datasource.class);
        if (Validator.isEmpty(datasource))
            return "";
        return datasource.value();
    }

    public <T extends Model> PageList<T> list(Query query, List<Object> args) {
        PageList<T> pageList = BeanFactory.getBean(PageList.class);
        if (Validator.isEmpty(query))
            return pageList;
        OrmContext ormContext = OrmContext.class.cast(query);
        if (Validator.isEmpty(ormContext.getFrom()))
            return pageList;
        query.datasource(getDatasource(ormContext.getFrom()));
        return crudOrmManage.getOrm().query(query);
    }

    private Object[] getArgs(List<Object> args) {
        Object[] array;
        if (args == null)
            array = new Object[]{};
        else
            array = args.toArray();
        return array;
    }

    public <T extends Model> T one(Query query, List<Object> args) {
        if (Validator.isEmpty(query))
            return null;
        OrmContext ormContext = OrmContext.class.cast(query);
        if (Validator.isEmpty(ormContext.getFrom()))
            return null;
        query.datasource(getDatasource(ormContext.getFrom()));
        return crudOrmManage.getOrm().findOne(query);
    }

    @Override
    public int count(Query query, List<Object> args) {
        return 0;
    }

    @Override
    public <T extends Model> T findById(Class<T> modelClass, String id) {
        return crudOrmManage.getOrm().findById(modelClass, id, getDatasource(modelClass));
    }

    /**
     * 添加单条数据
     * <p>
     * 数据源
     *
     * @param model 目标Model对象
     */
    @Override
    public <T extends Model> boolean save(T model) {
        return crudOrmManage.getOrm().save(model, InterventionType.NOTHING, getDatasource(model.getClass()));
    }

    @Override
    public <T extends Model> boolean update(Query query, List<Object> args) {
        if (Validator.isEmpty(query))
            return false;
        OrmContext ormContext = OrmContext.class.cast(query);
        if (Validator.isEmpty(ormContext.getFrom()))
            return false;
        query.datasource(getDatasource(ormContext.getFrom()));
        return crudOrmManage.getOrm().update(query);
    }


    /**
     * 根据主键删除数据
     *
     * 数据源
     *
     * @param modelClass
     *            目标Model类。
     * @param id
     *            主键

     @Override public <T extends Model> void deleteById(Class<T> modelClass, String id) {
     // Query query = query(modelClass);
     crudOrmManage.getOrm().delete(query().from(modelClass).where(" id =?"), new Object[] { id });
     }*/

    /**
     * 根据条件批量删除
     * <p>
     * 数据源
     *
     * @param modelClass   目标Model类。
     * @param criterionMap 条件列表
     * @param args         条件值
     */
    @Override
    public <T extends Model> void remove(Class<T> modelClass, Map<String, Criterion> criterionMap, Object[] args) {
        StringBuilder where = setCriterionMap(criterionMap);
        if (args == null)
            args = new Object[]{};
        crudOrmManage.getOrm().delete(query().from(modelClass).where(() -> where.toString()));
    }

    protected StringBuilder setCriterionMap(Map<String, Criterion> criterionMap, Operator... operator) {
        StringBuilder where = new StringBuilder();
        int ndx = 0;
        for (String name : criterionMap.keySet()) {
            if (where.length() > 0) {
                if (operator != null) {
                    if (operator.length > (ndx - 1)) {
                        where.append(operator[ndx - 1].getType());
                    } else {
                        where.append(" and ");
                    }
                } else {
                    where.append(" and ");
                }
            }
            where.append(name).append(criterionMap.get(name).getType());
            ndx++;
        }
        return where;
    }

    protected StringBuilder setSet(List<String> setList) {
        StringBuilder setBuilder = new StringBuilder();
        if (setList != null) {
            setList.forEach(s -> {
                if (setBuilder.length() > 0) {
                    setBuilder.append(",");
                }
                setBuilder.append(s).append("=?");
            });

        }
        return setBuilder;
    }
}
