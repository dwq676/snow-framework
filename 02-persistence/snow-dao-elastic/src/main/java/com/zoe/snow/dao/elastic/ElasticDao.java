package com.zoe.snow.dao.elastic;

import com.zoe.snow.dao.orm.Orm;
import net.sf.json.JSONArray;

/**
 * ElasticDao
 *
 * @author Dai Wenqing
 * @date 2016/9/27
 */
public interface ElasticDao extends Orm<ElasticQuery> {

    boolean save(String index, String type, String json, String... id);

    //boolean update(String index, String type, String id, String json);

    JSONArray getAsJson(ElasticQuery elasticQuery);
}
