package com.zoe.snow.dao.hibernate.dialect;

import com.zoe.snow.dao.dialect.Dialect;

/**
 * HDialect
 *
 * @author Dai Wenqing
 * @date 2016/1/23
 */
public interface HDialect extends Dialect {
    String getHibernateDialect();
}
