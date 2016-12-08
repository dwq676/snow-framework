/**
 *
 */
package com.zoe.snow.dao.hibernate;

import com.zoe.snow.dao.orm.Orm;
import com.zoe.snow.dao.orm.QueryImpl;
import com.zoe.snow.model.Model;

import java.util.Iterator;

/**
 * HibernateORM。主要提供基于Hibernate的ORM支持。
 *
 * @author lpw
 */
public interface HibernateOrm extends Orm<HibernateQuery> {
    /**
     * 检索满足条件的数据。
     *
     * @param query
     *            检索条件
     * @return Model实例集。
     */
    <T extends Model> Iterator<T> iterate(HibernateQuery query);
}
