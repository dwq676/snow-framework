package com.zoe.snow.crud.service;

import com.zoe.snow.dao.orm.Orm;
import com.zoe.snow.dao.orm.Query;

/**
 * 获取orm框架，当前
 *
 * @author Dai Wenqing
 * @date 2016/3/25
 */
public interface OrmManage {
    Orm<Query> getOrm();

    Orm<Query> getOrm(String ormName);

    Query getQuery();

    Query getQuery(String ormName);
}
