package com.zoe.snow.dao.elastic;

import com.zoe.snow.dao.ConnectionManage;
import com.zoe.snow.dao.DataSourceBean;
import org.elasticsearch.client.Client;

/**
 * 获取客户端链接
 *
 * @author Dai Wenqing
 * @date 2016/9/27
 */
public interface ElasticClient extends ConnectionManage<Client> {

}
