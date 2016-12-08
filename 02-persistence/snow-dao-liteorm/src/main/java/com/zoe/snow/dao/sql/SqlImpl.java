package com.zoe.snow.dao.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Repository;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.dao.Mode;
import com.zoe.snow.log.Logger;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.PageList;
import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Validator;

/**
 * @author lpw
 */
@Repository("snow.dao.sql")
public class SqlImpl extends JdbcSupport<PreparedStatement> implements Sql {
    @Override
    public SqlTable query(String sql, int size, int page, Object[] args, String... datasource) {

        try {
            if (page > 0)
                sql = connectionManage.getDialect(datasource).appendPagination(sql, size, page);
            PreparedStatement pstmt = newPreparedStatement(Mode.Read, sql);
            setArgs(pstmt, args);
            SqlTable sqlTable = query(pstmt.executeQuery());
            pstmt.close();

            if (Logger.isDebugEnable())
                Logger.debug("执行SQL[{}:{}]检索操作。", sql, Converter.toString(args));

            return sqlTable;
        } catch (SQLException e) {
            Logger.warn(e, "执行SQL[{}:{}]检索时发生异常！", sql, Converter.toString(args));

            throw new RuntimeException(e);
        }
    }

    @Override
    public JSONArray queryAsJson(String sql, int size, int page, Object[] args, String... datasource) {
        try {
            if (size > 0)
                sql = connectionManage.getDialect(datasource).appendPagination(sql, size, page);
            PreparedStatement pstmt = newPreparedStatement(Mode.Read, sql, datasource);
            setArgs(pstmt, args);
            JSONArray array = queryAsJson(pstmt.executeQuery());
            pstmt.close();

            if (Logger.isDebugEnable())
                Logger.debug("执行SQL[{}:{}]检索操作。", sql, Converter.toString(args));

            return array;
        } catch (SQLException e) {
            Logger.warn(e, "执行SQL[{}:{}]检索时发生异常！", sql, Converter.toString(args));

            throw new RuntimeException(e);
        }
    }

    @Override
    public int[] update(String sql, List<Object[]> args, String... datasource) {
        if (Logger.isDebugEnable())
            Logger.debug("成功执行SQL[{}:{}]批量更新操作。", sql, Converter.toString(args));

        if (Validator.isEmpty(args))
            return new int[]{update(sql, new Object[0])};

        if (args.size() == 1)
            return new int[]{update(sql, args.get(0))};

        try {
            PreparedStatement pstmt = newPreparedStatement(Mode.Write, sql);
            for (Object[] array : args) {
                setArgs(pstmt, array);
                pstmt.addBatch();
            }
            int[] array = pstmt.executeBatch();
            pstmt.close();
            return array;
        } catch (SQLException e) {
            Logger.warn(e, "执行SQL[{}:{}]更新时发生异常！", sql, Converter.toString(args));

            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean execute(String sql, Object[] args, String... datasource) {
        if (Logger.isDebugEnable())
            Logger.debug("执行SQL[{}:{}]。", sql, Converter.toString(args));

        try {
            PreparedStatement pstmt = newPreparedStatement(Mode.Read, sql);
            setArgs(pstmt, args);

            /*
             * pstmt.addBatch(); int[] array = pstmt.executeBatch();
             * pstmt.close(); if (array.length > 0) return array[0] > 0;
             */
            return pstmt.executeUpdate() >= 0;
        } catch (SQLException e) {
            Logger.warn(e, "执行SQL[{}:{}]检索时发生异常！", sql, Converter.toString(args));
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T extends Model> PageList<T> getList(Class<? extends Model> classZ, String sql, int size, int page, Object[] args, String... datasource) {
        if (Validator.isEmpty(sql) || Validator.isEmpty(classZ))
            return BeanFactory.getBean(PageList.class);
        int count = 0;
        if (page == 1) {
            count = getCount(sql, false, args);
        }
        return sqlTableToPageList(classZ, query(sql, size, page, args), count, size, page);
    }

    @Override
    public int getCount(String sql, boolean isComplex, Object[] args, String... datasource) {
        StringBuilder querySql = new StringBuilder().append("SELECT COUNT(*)");
        if (isComplex) {
            String[] sqls = sql.split("\\s+");
            List<String> fromList = new ArrayList();
            for (String s : sqls) {
                if (s.toLowerCase().equals("from"))
                    fromList.add(s);
            }
            if (fromList.size() < 1)
                return 0;
            sqls = sql.split(fromList.get(fromList.size() - 1));
            querySql.append(" from ").append(sqls[1]);
        } else {
            querySql.append(" from (").append(sql).append(") t");
        }
        SqlTable sqlTable = query(querySql.toString(), -1, -1, args);
        return Converter.toInt(sqlTable.get(0, 0));
    }

    protected PreparedStatement newPreparedStatement(Mode mode, String sql, String... datasource) throws SQLException {
        return getConnection(mode, datasource).prepareStatement(sql);
    }

}
