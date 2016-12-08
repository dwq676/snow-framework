package com.zoe.snow.dao.orm.split;

import com.zoe.snow.dao.orm.OrmContext;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.Operator;

/**
 * Where
 *
 * @author Dai Wenqing
 * @date 2016/5/4
 */
public interface Where {
    /**
     * 设置WHERE片段。
     *
     * @param column
     *            字段
     * @return 当前Query实例。
     */

    void where(OrmContext ormContext, String column, Criterion criterion, Object[] value, Operator... operator);
}
