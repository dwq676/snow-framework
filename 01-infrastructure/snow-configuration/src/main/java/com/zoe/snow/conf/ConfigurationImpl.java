package com.zoe.snow.conf;

import com.zoe.snow.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 统一配置实现类；
 * <p>
 * 1、独立部署singleton，不启动架构配置的加载容器的监听
 * <p>
 * 2、实现了默认的配置，可被WEB项目继承直接使用，与将所有配置放配在WEB项目中同一个道理，
 * 若有与默认配置不同的，可以将当前配置文件复制到当前WEB项目中，在其中修改配置值
 * <p>
 * 3、可独立部署，将配置放置分布式架构中zookeeper中， 做到统一配置，同步管理
 *
 * @author Dai Wenqing
 * @date 2016/1/19
 */
@Component("snow.conf")
public class ConfigurationImpl implements Configuration, CrudConfiguration,
        CacheConfiguration, DaoConfiguration, RabbitMqConfiguration, AuthenticationConf {
    @Value("${snow.configuration.deploy:singleton}")
    private String deploy;

    @Value("${snow.session.name}")
    private String sessionName;
    @Value("${snow.cache.name}")
    private String cacheName;
    @Value("${snow.project-name}")
    private String projectName;

    @Value("${snow.dao.orm}")
    private String ormName;
    @Value("${snow.datasource.multiplexing}")
    private String multiplexing;

    /* @Value("${snow.dao.database.driver}")
     private String driver;
     @Value("${snow.dao.database.servers}")
     private String servers;
     // @Value("${snow.dao.database.name}")
     private String databaseName;
     // @Value("${snow.dao.database.username}")
     private String databaseUserName;
     // @Value("${snow.dao.database.password}")
     private String databasePassword;*/
    @Value("${snow.dao.database.initial-size}")
    private String initialSize = "0";
    @Value("${snow.dao.database.max-active}")
    private String maxActive;
    @Value("${snow.dao.database.max-wait}")
    private String maxWait;
    @Value("${snow.dao.database.test-interval}")
    private String testInterval;
    @Value("${snow.dao.database.remove-abandoned-timeout}")
    private String removeAbandonedTimeout;

    /*@Value("${snow.dao.hibernate.use-second-level}")
    private String userSecondLevelCache;
    @Value("${snow.dao.hibernate.show-sql}")
    private String showSql;
    @Value("${snow.dao.hibernate.set-packages-to-scan}")
    private String packagesToScan;
    @Value("${snow.datasource.name}")
    private String dataSourceName;*/

    @Value("${snow.dao.remove-abandoned}")
    private String removeAbandoned;
    @Value("${snow.dao.log-abandoned}")
    private String logAbandoned;
    @Value("${snow.dao.time-between-eviction-runs-millis}")
    private String timeBetweenEvictionRunsMillis;
    @Value("${snow.dao.min-evictable-idle-time-millis}")
    private String minEvictableIdleTimeMillis;
    @Value("${snow.dao.test-while-idle}")
    private String testWhileIdle;
    @Value("${snow.dao.test-on-borrow}")
    private String testOnBorrow;
    @Value("${snow.dao.test-on-return}")
    private String testOnReturn;
    @Value("${snow.dao.pool-prepared-statements}")
    private String poolPreparedStatements;
    @Value("${snow.dao.maxPool-prepared-statement-per-connection-size}")
    private String maxPoolPreparedStatementPerConnectionSize;
    @Value("${snow.dao.filters}")
    private String filters;

    @Value("${snow-rabbitmq-rabbitmqPort}")
    private String rabbitmqPort;
    @Value("${snow-rabbitmq-rabbitmqUsername}")
    private String rabbitmqUsername;
    @Value("${snow-rabbitmq-rabbitmqPassword}")
    private String rabbitmqPassword;
    @Value("${snow-rabbitmq-defaultRabbitmq}")
    private String defaultRabbitmq;
    @Value("${snow-rabbitmq-defaultRabbitmqExchange}")
    private String defaultRabbitmqExchange;
    @Value("${snow-rabbitmq-prefetchSize}")
    private String prefetchSize;
    @Value("${snow-rabbitmq-serverHost}")
    private String serverHost;
    @Value("${snow-rabbitmq-virtualHost}")
    private String virtualHost;
    @Value("${snow-rabbitmq-connectionTimeout}")
    private String connectionTimeout;
    @Value("${snow-rabbitmq-eventMsgProcessNum}")
    private String eventMsgProcessNum;
    @Value("${snow-rabbitmq-switch}")
    private String rabbitSwitch;

    @Value("${auth.is-third-part}")
    private String authIsThirdPart;
    @Value("${auth.host:}")
    private String authHost;
    @Value("${auth.port}")
    private String authPort;
    @Value("${auth.project}")
    private String authProject;
    @Value("${auth.expired-in}")
    private String authExpiredIn;
    @Value("${auth.expired-remember}")
    private String authExpiredRemember;
    @Value("${auth.multi}")
    private String authMulti;
    @Value("${auth.protocol}")
    private String authProtocol;
    /*@Value("${snow.elastic.cluster.name}")
    private String elasticClusterName;
    @Value("${snow.elastic.index.name}")
    private String elasticIndexName;
    @Value("${snow.elastic.type.name}")
    private String elasticTypeName;
    @Value("${snow.elastic.ha.ip}")
    private String elasticHaIps;
    @Value("${snow.elastic.switch}")
    private boolean elasticSwitch;*/

    @Override
    public long getAuthExpiredIn() {
        return Converter.toLong(authExpiredIn);
    }

    @Override
    public long getAuthExpiredRemember() {
        return Converter.toLong(authExpiredRemember);
    }

    public boolean getAuthMulti() {
        return Converter.toBool(authMulti);
    }

    @Override
    public String getAuthProtocol() {
        return authProtocol;
    }

    @Override
    public boolean getAuthIsThirdPart() {
        return Converter.toBool(authIsThirdPart);
    }

    @Override
    public String getAuthHost() {
        return authHost;
    }

    @Override
    public int getAuthPort() {
        return Converter.toInt(authPort);
    }

    @Override
    public String getAuthProject() {
        return authProject;
    }

    @Override
    public String getCacheName() {
        return cacheName;
    }

    @Override
    public String getSessionName() {
        return sessionName;
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    /*@Override
    public String getServers() {
        return this.servers;
    }

    @Override
    public String getDriver() {
        return this.driver;
    }

    @Override
    public String getDatabaseUserName() {
        return this.databaseUserName;
    }

    @Override
    public String getDatabasePassword() {
        return this.databasePassword;
    }

    @Override
    public String getDatabaseName() {
        return this.databaseName;
    }*/

    @Override
    public int getMultiplexing() {
        return Converter.toInt(this.multiplexing);
    }

    @Override
    public int getInitialSize() {
        return Converter.toInt(this.initialSize);
    }

    @Override
    public int getMaxWait() {
        return Converter.toInt(this.maxWait);
    }

    @Override
    public int getMaxActive() {
        return Converter.toInt(this.maxActive);
    }

    @Override
    public int getTestInterval() {
        return Converter.toInt(this.testInterval);
    }

    @Override
    public int getRemoveAbandonedTimeout() {
        return Converter.toInt(this.removeAbandonedTimeout);
    }

    /*@Override
    public String getDataSourceName() {
        return null;
    }*/

    /*@Override
    public boolean showSql() {
        return this.showSql.toLowerCase().equals("true");
    }

    @Override
    public boolean useSecondLevelCache() {
        return this.userSecondLevelCache.toLowerCase().equals("true");
    }

    @Override
    public String getPackagesToScan() {
        return this.packagesToScan;
    }*/

    @Override
    public boolean getRemoveAbandoned() {
        return Converter.toBool(removeAbandoned);
    }

    @Override
    public boolean getLogAbandoned() {
        return Converter.toBool(logAbandoned);
    }

    @Override
    public int getTimeBetweenEvictionRunsMillis() {
        return Converter.toInt(timeBetweenEvictionRunsMillis);
    }

    @Override
    public int getMinEvictableIdleTimeMillis() {
        return Converter.toInt(minEvictableIdleTimeMillis);
    }

    @Override
    public boolean getTestWhileIdle() {
        return Converter.toBool(testWhileIdle);
    }

    @Override
    public boolean getTestOnBorrow() {
        return Converter.toBool(testOnBorrow);
    }

    @Override
    public boolean getTestOnReturn() {
        return Converter.toBool(testOnReturn);
    }

    @Override
    public boolean getPoolPreparedStatements() {
        return Converter.toBool(poolPreparedStatements);
    }

    @Override
    public int getMaxPoolPreparedStatementPerConnectionSize() {
        return Converter.toInt(maxPoolPreparedStatementPerConnectionSize);
    }

    @Override
    public String getFilters() {
        return filters;
    }

    @Override
    public String getOrmName() {
        return ormName;
    }

    @Override
    public int getRabbitMQPort() {
        return Converter.toInt(rabbitmqPort);
    }

    @Override
    public String getDefaultRabbitmq() {
        return defaultRabbitmq;
    }

    @Override
    public String getDefaultRabbitmqExchange() {
        return defaultRabbitmqExchange;
    }

    @Override
    public String getRabbitMQUsername() {
        return rabbitmqUsername;
    }

    @Override
    public String getRabbitMQPassword() {
        return rabbitmqPassword;
    }

    @Override
    public int getPrefetchSize() {
        return Converter.toInt(prefetchSize);
    }

    @Override
    public String getServerHost() {
        return serverHost;
    }

    @Override
    public String getVirtualHost() {
        return virtualHost;
    }

    @Override
    public int getConnectionTimeout() {
        return Converter.toInt(connectionTimeout);
    }

    @Override
    public int getEventMsgProcessNum() {
        return Converter.toInt(eventMsgProcessNum);
    }

    @Override
    public boolean getRabbitmqSwitch() {
        return Converter.toBool(rabbitSwitch);
    }

    /*@Override
    public String getElasticClusterName() {
        return this.elasticClusterName;
    }

    @Override
    public String getElasticIndexName() {
        return this.elasticIndexName;
    }

    @Override
    public String getElasticTypeName() {
        return this.elasticTypeName;
    }

    @Override
    public String getElasticHaIps() {
        return this.elasticHaIps;
    }

    @Override
    public boolean getElasticSwitch() {
        return this.elasticSwitch;
    }*/
}
