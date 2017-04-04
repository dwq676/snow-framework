package com.zoe.snow.dao.hbase;

import com.zoe.snow.dao.*;
import com.zoe.snow.dao.dialect.Dialect;
import com.zoe.snow.listener.ContextRefreshedListener;
import com.zoe.snow.log.Logger;
import com.zoe.snow.util.Validator;
import org.apache.commons.lang.NullArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * HBaseClientImpl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/3
 */
@Repository("snow.dao.hbase-client")
public class HBaseClientImpl extends ConnectionSupport<Configuration>
        implements HBaseClient, ContextRefreshedListener {
    private String dataSource = "hbase";
    private String kerberos = "Kerberos";

    @Override
    public Configuration open(String datasource, Mode mode) {
        String classPath = Configuration.class.getResource("/").getPath().substring(1);
        if (Validator.isEmpty(System.getProperty(HBaseProperty.HADOOP_HOME_DIR)))
            System.setProperty(HBaseProperty.HADOOP_HOME_DIR, classPath + HBaseProperty.HADOOP_BIN);
        System.out.println(classPath + HBaseProperty.KRB5_CONF);
        System.setProperty(HBaseProperty.SECURITY_KRB5_CONF, classPath + HBaseProperty.KRB5_CONF);
        Configuration conf = HBaseConfiguration.create();
        conf.set(HBaseProperty.HADOOP_SECURITY_AUTH, kerberos);
        // 这个hbase.keytab也是从远程服务器上copy下来的, 里面存储的是密码相关信息
        // 这样我们就不需要交互式输入密码了
        conf.set(HBaseProperty.KEY_TAB_FILE, classPath + HBaseProperty.HBASE_KEYTAB_KEY);
        UserGroupInformation.setConfiguration(conf);
        DataSourceBean dataSourceBean = DataSourceManager.getDataSourceBean(datasource);
        try {
            UserGroupInformation.loginUserFromKeytab(dataSourceBean.getWriteAbleHost().getPassword()
                    , classPath + HBaseProperty.HBASE_KEYTAB_KEY);
            //ugi = UserGroupInformation.getCurrentUser();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return conf;
    }

    @Override
    public Configuration fetch(List<Configuration> caches, String datasource, Mode mode) {
        if (caches == null)
            return null;
        try {
            for (Configuration s : caches) {
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
    public void rollback(Configuration connection) {

    }

    @Override
    public void commit(Configuration connection) throws SQLException {

    }

    @Override
    public void close(Configuration connection) {

    }

    @Override
    public Dialect getDialect(String... key) {
        return null;
    }

    @Override
    public int getContextRefreshedSort() {
        return 4;
    }

    @Override
    public void onContextRefreshed() {
        initDatasource(dataSource);
    }

    @Override
    public String getName() {
        return "hbase";
    }
}
