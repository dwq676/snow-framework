/**
 *
 */
package com.zoe.snow.dao.dialect.mysql;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.zoe.snow.dao.dialect.Dialect;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository("snow.dao.dialect.mysql")
public class MysqlDialect implements Dialect {
    @Override
    public String getName() {
        return "mysql";
    }

    @Override
    public String getDriver() {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    public String getUrl(String ip, String schema) {
        return "jdbc:mysql://" + ip + "/" + schema + "?useUnicode=true&characterEncoding=utf-8";
    }

    @Override
    public String getValidationQuery() {
        return "SELECT CURRENT_DATE";
    }

    /*
     * @Override public String getHibernateDialect() { return
     * "org.hibernate.dialect.MySQLDialect"; }
     */

    @Override
    public String appendPagination(String sql, int size, int page) {
        StringBuffer buffer = new StringBuffer(sql);
        buffer.append(" LIMIT ").append(size * (page - 1)).append(',').append(size);
        return buffer.toString();
    }

    @Override
    public String getDateConverter(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "to_date('{" + formatter.format(date) + "}','%Y-%m-%d %H:%i:%s')";
    }

    @Override
    public String getColumnSql() {
        return "select * from information_schema.TABLES t LEFT JOIN information_schema.COLUMNS c  ON t.TABLE_NAME = c.TABLE_NAME where c.TABLE_SCHEMA =? AND t.TABLE_SCHEMA =? and t.table_name =?";
    }

}
