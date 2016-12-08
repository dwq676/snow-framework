package com.zoe.snow.dao.hibernate.dialect;

import com.zoe.snow.dao.dialect.mysql.MysqlDialect;
import org.springframework.stereotype.Repository;

/**
 * MysqlHDialect
 *
 * @author Dai Wenqing
 * @date 2016/1/23
 */
@Repository("snow.dao.hibernate.dialect.mysql")
public class MysqlHDialect extends MysqlDialect implements  HDialect {
    @Override
    public String getHibernateDialect() {
        return "org.hibernate.dialect.MySQLDialect";
    }
}
