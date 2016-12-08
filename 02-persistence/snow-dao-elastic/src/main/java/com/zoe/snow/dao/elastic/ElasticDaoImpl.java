package com.zoe.snow.dao.elastic;

import com.zoe.snow.conf.deprecated.ElasticConfiguration;
import com.zoe.snow.dao.DataSourceManager;
import com.zoe.snow.dao.DataSources;
import com.zoe.snow.dao.Mode;
import com.zoe.snow.dao.orm.WhereContext;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.PageList;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.Operator;
import com.zoe.snow.util.Validator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static org.elasticsearch.index.query.QueryBuilders.*;


/**
 * ElasticDaoImpl
 *
 * @author Dai Wenqing
 * @date 2016/9/27
 */
@Repository("snow.dao.elastic")
public class ElasticDaoImpl implements ElasticDao {
    @Autowired
    private ElasticClient elasticClient;
    @Autowired
    private DataSources dataSources;

    /**
     * 保存数据
     * 判断文档信息是否存在，如不存在，直接写入
     * 如果存在，判断当前文档信息的contentMD5于索引内的MD5是否一致，如不一致重新写入，如一致，直接返回
     *
     * @return 返回id
     */
    @Override
    public boolean save(String index, String type, String json, String... id) {
        index = getIndex(index);
        type = getType(type);

        if (Validator.isEmpty(elasticClient.get(Mode.Write)))
            return false;

        if (id.length > 0) {
            UpdateResponse response = elasticClient.get(Mode.Write).prepareUpdate(index, type, id[0])
                    .setDoc(json)
                    .get();
            return response.isCreated();
        } else {
            IndexResponse response = elasticClient.get(Mode.Write).prepareIndex(index, type)
                    //.setId(new String().valueOf(new Random().nextLong()))
                    .setSource(json)
                    .execute().actionGet();
            return response.isCreated();
        }
    }

    @Override
    public String getOrmName() {
        return "elastic";
    }

    @Override
    public <T extends Model> T findById(Class<T> modelClass, String id, String... datasource) {
        return null;
    }

    @Override
    public <T extends Model> T findOne(ElasticQuery query) {
        return null;
    }

    @Override
    public <T extends Model> PageList<T> query(ElasticQuery query) {
        return null;
    }

    @Override
    public int count(ElasticQuery query) {
        SearchResponse searchResponse = getSearchResponse(query);
        if (Validator.isEmpty(searchResponse))
            return 0;
        return searchResponse.getHits().hits().length;
    }

    @Override
    public <T extends Model> boolean save(T model, String... datasource) {
        return false;
    }

    @Override
    public boolean update(ElasticQuery query) {
        //elasticClient.get(Mode.Read).prepareUpdate().setId(query.getFromType())

        return false;
    }

    @Override
    public <T extends Model> boolean delete(T model, String... datasource) {
        return false;
    }

    @Override
    public boolean delete(ElasticQuery query) {
        return false;
    }

    @Override
    public JSONArray getAsJson(ElasticQuery elasticQuery) {
        JSONArray jsonArray = new JSONArray();
        SearchResponse searchResponse = getSearchResponse(elasticQuery);
        if (Validator.isEmpty(searchResponse))
            return jsonArray;
        if (searchResponse != null) {
            if (Validator.isEmpty(elasticQuery.getGroup())) {
                searchResponse.getHits().forEach(searchHitFields -> {
                    JSONObject jsonObject = JSONObject.fromObject(searchHitFields.getSourceAsString());
                    jsonObject.put(ElasticType.DOC_ID, searchHitFields.getId());
                    jsonObject.put(ElasticType.TYPE, searchHitFields.getType());
                    jsonObject.put(ElasticType.INDEX, searchHitFields.getIndex());
                    jsonArray.add(jsonObject);
                });
            } else {
                JSONObject jsonObject = new JSONObject();
                searchResponse.getAggregations().asList().forEach(c -> {
                    Terms terms = ((Terms) c);
                    if (!Validator.isEmpty(terms)) {
                        JSONArray ja = new JSONArray();
                        //jsonObject.put(c.getName(), ja);
                        terms.getBuckets().forEach(n -> {
                            JSONObject jo = new JSONObject();
                            MultiBucketsAggregation.Bucket bucket = (MultiBucketsAggregation.Bucket) n;
                            if (!Validator.isEmpty(bucket)) {
                                jo.put("key", bucket.getKey());
                                jo.put("count", bucket.getDocCount());
                                ja.add(jo);
                            }
                        });
                        jsonObject.element(c.getName(),ja);
                        jsonArray.add(jsonObject);
                    }
                });
            }
        }
        return jsonArray;
    }

    protected SearchResponse getSearchResponse(ElasticQuery elasticQuery) {
        SearchResponse searchResponse = null;
        if (!Validator.isEmpty(elasticQuery)) {
            String index = getIndex(elasticQuery.getSchema());
            SearchRequestBuilder searchRequestBuilder = elasticClient.get(Mode.Read).prepareSearch(index)
                    .setTypes(getType(elasticQuery.getFromType()))
                    .setFrom(elasticQuery.getPage()).setSize(elasticQuery.getSize());

            searchRequestBuilder.setQuery(createBoolQuery(elasticQuery));
            if (!Validator.isEmpty(elasticQuery.getGroup()))
                getGroupBy(searchRequestBuilder, elasticQuery);
            try {
                searchResponse = searchRequestBuilder.execute().actionGet();
            } catch (Exception e) {

            }
        }
        return searchResponse;
    }

    protected void getGroupBy(SearchRequestBuilder searchRequestBuilder, ElasticQuery elasticQuery) {
        searchRequestBuilder.addAggregation(AggregationBuilders.terms("group_by_" + elasticQuery.getGroup())
                .field(elasticQuery.getGroup()));
    }

    protected String getIndex(String index) {
        //String index;
        if (!Validator.isEmpty(index))
            return index;
        else
            return DataSourceManager.getDataSourceBean("elastic").getSchema();
    }

    protected String getType(String type) {
        //String type;
        if (!Validator.isEmpty(type))
            return type;
        else
            return DataSourceManager.getDataSourceBean("elastic").getType();
    }

    protected QueryBuilder createBoolQuery(ElasticQuery elasticQuery) {
        int ndx = 0;
        BoolQueryBuilder qb = boolQuery();
        for (WhereContext whereContext : elasticQuery.getWhereContexts()) {
            if (!Validator.isEmpty(whereContext.getValue())) {
                if (whereContext.getValue().length > 0) {
                    if (whereContext.getValue()[0] instanceof String) {

                        if (ndx == 0 || whereContext.getOperator() == Operator.And) {
                            if (whereContext.getCriterion() == Criterion.Like)
                                qb.must(matchQuery(whereContext.getKey(), whereContext.getValue()[0]));
                            else
                                qb.must(termQuery(whereContext.getKey(), whereContext.getValue()[0]));
                        } else if (whereContext.getOperator() == Operator.Or) {
                            if (whereContext.getCriterion() == Criterion.Like)
                                qb.should(termQuery(whereContext.getKey(), whereContext.getValue()[0]));
                            else
                                qb.should(matchQuery(whereContext.getKey(), whereContext.getValue()[0]));
                        }
                    } else {
                        //if (Validator.isNumeric(whereContext.getValue()))
                        RangeQueryBuilder rb = rangeQuery(whereContext.getKey());
                        String value = String.valueOf(whereContext.getValue()[0]);
                        switch (whereContext.getCriterion()) {
                            case GreaterThan:
                                rb.gt(value);
                                break;
                            case GreaterThanOrEquals:
                                rb.gte(value);
                                break;
                            case LessThan:
                                rb.lt(value);
                                break;
                            case LessThanOrEquals:
                                rb.lte(value);
                                break;
                            default:
                                Equals:
                                rb.equals(value);
                                break;
                            case Between: {
                                rb.from(whereContext.getValue()[0]);
                                rb.to(whereContext.getValue()[1]);
                                break;
                            }
                        }
                        if (ndx == 0 || whereContext.getOperator() == Operator.And)
                            qb.must(rb);
                        else
                            qb.should(rb);
                    }
                }
                ndx++;
            }
        }
        return qb;
    }
}

