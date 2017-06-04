package com.zoe.snow.dao.orm.query;

import com.zoe.snow.dao.orm.query.Query;
import com.zoe.snow.dao.orm.query.QueryImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * elastic
 *
 * @author Dai Wenqing
 * @date 2016/9/27
 */
@Repository("snow.dao.query.elastic")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ElasticQuery extends QueryImpl implements Query {

}
