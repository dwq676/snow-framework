package com.zoe.snow.dao.orm.split;

import com.zoe.snow.dao.orm.OrmContext;

/**
 * GroupBy
 *
 * @author Dai Wenqing
 * @date 2016/5/4
 */
public interface GroupBy {
    void group(OrmContext ormContext, String group);
}
