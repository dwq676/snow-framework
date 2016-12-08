package com.zoe.snow.dao.elastic;

import com.zoe.snow.Global;
import com.zoe.snow.bean.ContextRefreshedListener;
import com.zoe.snow.dao.*;
import com.zoe.snow.log.Logger;
import com.zoe.snow.util.Validator;
import org.apache.commons.lang.NullArgumentException;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.InetAddress;
import java.util.List;

/**
 * ElasticClientImpl
 *
 * @author Dai Wenqing
 * @date 2016/9/27
 */
@Repository("snow.dao.elastic.client")
public class ElasticClientImpl extends ConnectionSupport<Client> implements ElasticClient, ContextRefreshedListener {
    @Autowired
    private DataSources dataSources;
    private DataSourceHost dataSourceHost = null;

    @Override
    public Client open(String datasource, Mode mode) {
        //ElasticConfiguration elasticConfiguration = BeanFactory.getBean(ElasticConfiguration.class);

        Settings settings = Settings.settingsBuilder().put("cluster.name", dataSources.getDataSourceBeanMap()
                .get("elastic").getCluster())
                .build();
        TransportClient client = null;
        try {
            client = TransportClient.builder().settings(settings).build();
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(dataSourceHost.getIp())
                    , Integer.valueOf(dataSourceHost.getPort())));
            /*JSONArray jsonArray = JSONArray.fromObject(dataSourceHost.getIp());
            if (!Validator.isEmpty(jsonArray)) {
                for (Object o : jsonArray) {
                    String ip = String.valueOf(o);
                    String[] ips = ip.split(":");
                    if (ips.length < 2) {
                        Logger.error(new Exception("ip can not resolve"), "ip地址格式有问题，正常格式是127.0.0.1:9300");
                        return null;
                    }
                    client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ips[0]), Integer.valueOf(ips[1])));
                }
            }*/
            Global.elasticReady = true;
        } catch (Exception e) {
            Logger.error(new Exception("can new the elastic client"), "构建elastic客户端时出错");
        }
        return client;
    }

    @Override
    public Client fetch(List<Client> caches, String datasource, Mode mode) {
        if (caches == null)
            return null;
        try {
            for (Client s : caches) {
                if (!Validator.isEmpty(s)) {
                    return s;
                }
            }
        } catch (Exception e) {
            Logger.error(e, "client获取过程出现了异常！");
        }
        return null;
    }

    @Override
    public void rollback(Client client) {

    }

    @Override
    public void commit(Client client) {
        if (!Validator.isEmpty(client)) {

        }
    }

    @Override
    public void close(Client client) {
        if (!Validator.isEmpty(client))
            client.close();
    }

    @Override
    public int getContextRefreshedSort() {
        return 4;
    }

    @Override
    public void onContextRefreshed() {
        if (Validator.isEmpty(dataSources) || Validator.isEmpty(dataSources.getDataSourceBeanMap()))
            throw new NullArgumentException("DataSources相关的Bean节点不能为空");

        if (Validator.isEmpty(dataSources.getDataSourceBeanMap().get("elastic")))
            throw new NullArgumentException("没有找到key='elastic'相关的数据源配置！");


        dataSourceHost = dataSources.getDataSourceBeanMap().get("elastic").getWriteAbleHost();
        if (Validator.isEmpty(dataSourceHost))
            throw new NullArgumentException("未找到elastic可读可写的主机节点！");
        if (dataSourceHost.getHostSwitch())
            get(Mode.Write, "elastic");

        dataSourceHost = dataSources.getDataSourceBeanMap().get("elastic").getReadOnlyHost();
        if (Validator.isEmpty(dataSourceHost))
            dataSourceHost = dataSources.getDataSourceBeanMap().get("elastic").getWriteAbleHost();
        if (dataSourceHost.getHostSwitch())
            get(Mode.Read, "elastic");
    }
}
