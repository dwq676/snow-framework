package com.zoe.snow.dao.orm.query;

import com.zoe.snow.dao.orm.query.Query;
import com.zoe.snow.dao.orm.query.QueryImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * HBaseQuery
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/3
 */
@Repository("snow.dao.query.hbase")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class HBaseQuery  extends QueryImpl implements Query {
}
