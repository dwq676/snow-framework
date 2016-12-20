package com.zoe.snow.conf.deprecated;

/**
 * ElasticConfig
 *
 * @author Dai Wenqing
 * @date 2016/9/27
 */
@Deprecated
public interface ElasticConfiguration {
    String getElasticClusterName();
    String getElasticIndexName();
    String getElasticTypeName();
    String getElasticHaIps();
    boolean getElasticSwitch();
}
