package com.zoe.snow.dao.orm;

import com.zoe.snow.dao.orm.Orm;
import com.zoe.snow.dao.orm.query.ElasticQuery;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * ElasticDao
 *
 * @author Dai Wenqing
 * @date 2016/9/27
 */
public interface ElasticDao extends Orm<ElasticQuery> {

    boolean save(String index, String type, String json, String... id);

    //boolean update(String index, String type, String id, String json);

    JSONObject getAsJsonObject(ElasticQuery elasticQuery);

    JSONArray getAsJsonArray(ElasticQuery elasticQuery);
}
