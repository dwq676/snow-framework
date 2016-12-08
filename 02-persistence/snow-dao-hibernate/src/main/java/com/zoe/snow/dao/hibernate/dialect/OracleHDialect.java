package com.zoe.snow.dao.hibernate.dialect;

import com.zoe.snow.dao.dialect.oracle.OracleDialect;
import org.springframework.stereotype.Repository;

/**
 * OracleHDialect
 *
 * @author Dai Wenqing
 * @date 2016/1/23
 */
@Repository("snow.dao.hibernate.dialect.oracle")
public class OracleHDialect extends OracleDialect implements HDialect {
    @Override
    public String getHibernateDialect() {
        return "org.hibernate.dialect.Oracle10gDialect";
    }
}
