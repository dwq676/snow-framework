/**
 *
 */
package com.zoe.snow.dao.dialect.oracle;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.zoe.snow.dao.dialect.Dialect;
import org.springframework.stereotype.Repository;

/**
 * @author dwq
 */
@Repository("snow.dao.dialect.oracle")
public class OracleDialect implements Dialect {
    @Override
    public String getName() {
        return "oracle";
    }

    @Override
    public String getDriver() {
        return "oracle.jdbc.driver.OracleDriver";
    }

    @Override
    public String getUrl(String ip, String schema) {
        return "jdbc:oracle:thin:@" + ip + ":" + schema;
    }

    @Override
    public String getValidationQuery() {
        return "SELECT SYSDATE FROM DUAL";
    }

    /*
     * @Override public String getHibernateDialect() { return
     * "org.hibernate.dialect.Oracle10gDialect"; }
     */

    @Override
    public String appendPagination(String sql, int size, int page) {
        StringBuffer buffer = new StringBuffer(sql);
        buffer.insert(0, "SELECT * FROM (SELECT oracle_pagination_1.*, ROWNUM AS rowno FROM (").append(") oracle_pagination_1 WHERE ROWNUM<=")
                .append(size * page).append(") oracle_pagination_2 WHERE oracle_pagination_2.rowno>").append(size * (page - 1));
        return buffer.toString();
    }

    @Override
    public String getDateConverter(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "to_date('{" + formatter.format(date) + "}','yyyy-mm-dd hh24:mi:ss')";
    }

    @Override
    public String getColumnSql() {
        return "ALL_TAB_COMMENTS T LEFT JOIN ALL_COL_COMMENTS C ON T.TABLE_NAME = C.TABLE_NAME AND T.OWNER = C.OWNER " +
                "LEFT JOIN ALL_TAB_COLS A ON C.TABLE_NAME = A.TABLE_NAME AND C.COLUMN_NAME = A.COLUMN_NAME " +
                "AND C.OWNER = A.OWNER LEFT JOIN (SELECT COL.* FROM ALL_CONSTRAINTS CON, ALL_CONS_COLUMNS COL " +
                "WHERE CON.CONSTRAINT_NAME = COL.CONSTRAINT_NAME AND COL.OWNER = CON.OWNER AND CON.CONSTRAINT_TYPE = 'P') P " +
                "ON C.TABLE_NAME = P.TABLE_NAME AND C.COLUMN_NAME = P.COLUMN_NAME AND C.OWNER = P.OWNER" +
                "where C.TABLE_NAME =?";
    }
}
