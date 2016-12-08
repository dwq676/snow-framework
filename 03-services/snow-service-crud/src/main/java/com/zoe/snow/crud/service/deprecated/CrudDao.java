package com.zoe.snow.crud.service.deprecated;

import com.zoe.snow.dao.orm.Query;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.PageList;
import com.zoe.snow.model.enums.Criterion;

import java.util.List;
import java.util.Map;

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
public interface CrudDao {

    <T extends Model> PageList<T> list(Query query, List<Object> args);

    <T extends Model> T one(Query query, List<Object> args);

    int count(Query query, List<Object> args);

    /**
     * 获取查询上下文
     *
     * @param <T>
     * @return
     */
    public <T extends Model> Query query();

    /**
     * 获取指定ID的数据。
     *
     * @param modelClass 目标Model类。
     * @param id         数据ID值。
     * @param <T>        Model类。
     * @return 指定ID的数据；如果不存在则返回null。
     */
    <T extends Model> T findById(Class<T> modelClass, String id);

    /**
     * 添加单条数据
     *
     * @param model 目标Model对象
     * @param <T>   Model类
     */
    <T extends Model> boolean save(T model);


    <T extends Model> boolean update(Query query, List<Object> args);

    /**
     * 根据主键删除数据
     *
     * @param modelClass
     *            目标Model类。
     * @param id
     *            主键
     * @param <T>
     *            Model类
     */
    //<T extends Model> void deleteById(Class<T> modelClass, String id);

    /**
     * 根据条件批量删除
     *
     * @param modelClass   目标Model类。
     * @param criterionMap 条件列表
     * @param args         条件值
     * @param <T>          Model类
     */
    <T extends Model> void remove(Class<T> modelClass, Map<String, Criterion> criterionMap, Object[] args);

}
