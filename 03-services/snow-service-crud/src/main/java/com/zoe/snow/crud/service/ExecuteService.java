package com.zoe.snow.crud.service;

import com.zoe.snow.crud.enums.DeleteType;
import com.zoe.snow.dao.orm.Query;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.enums.Criterion;

import java.util.Map;

/**
 * 删除服务
 *
 * @author Dai Wenqing
 * @date 2016/7/15
 */
public interface ExecuteService {

    /**
     * 从收回站清除数据，在界面上无法恢复，可以通过数据库途径进行恢复
     *
     * @param id
     *            对象id
     * @return
     */
    //<T extends Model> Boolean remove(Class<T> classZ, String id);
    // <T extends Model> Boolean remove(Class<T> classZ, Query query);

    /**
     * 从回收站回收数据
     *
     * @param id
     * @return
     */

    //<T extends Model> Boolean recycle(Class<T> classZ, String id);

    /**
     * 被删除，但放入回收站数据，可从界面上恢复为正常数据
     *
     *            对象id
     * @return
     */
    //<T extends Model> Boolean delete(Class<T> classZ, String id);

    // <T extends Model> Boolean delete(Class<T> classZ, Query query);

    //<T extends Model> Boolean clear(Class<T> classZ, Map<String, Criterion> criterionMap, Object[] args);

    <T extends Model> boolean deleteOrRecycle(Query query, DeleteType deleteType);

    boolean update(Query query);
}
