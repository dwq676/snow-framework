package com.zoe.snow.dao.elastic;

import com.zoe.snow.dao.orm.Query;
import com.zoe.snow.dao.orm.QueryImpl;
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
