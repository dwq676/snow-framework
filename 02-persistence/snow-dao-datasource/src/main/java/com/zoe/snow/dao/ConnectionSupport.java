package com.zoe.snow.dao;

import com.zoe.snow.conf.DaoConfiguration;
import com.zoe.snow.dao.dialect.Dialect;
import com.zoe.snow.log.Logger;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.MappedSuperclass;
import java.sql.SQLException;
import java.util.*;

/**
 * ConnectionSupport
 *
 * @author Dai Wenqing
 * @date 2016/8/26
 */
@MappedSuperclass()
public class ConnectionSupport<T> implements ConnectionManage<T> {
    protected ThreadLocal<Map<String, Map<Mode, List<T>>>> connections = new ThreadLocal<>();
    // ThreadLocal 只是对线程进行安全隔离，每个线程都需要重新创建对象
    protected ThreadLocal<Boolean> transactional = new ThreadLocal<>();
    protected ThreadLocal<Boolean> localTransactionalIsCommit = new ThreadLocal<>();
    //线程可以复用计算器
    protected ThreadLocal<Integer> sessionMultiplexingCount = new ThreadLocal<>();
    //默认可以复用数为1000
    protected int defaultCount = 1000;
    protected int minCount = 20;
    @Autowired
    protected DaoConfiguration daoConfiguration;

    @Override
    public boolean isCommit() {
        return localTransactionalIsCommit.get() == null ? false : localTransactionalIsCommit.get();
    }

    @Override
    public boolean hasBeginTransaction() {
        return transactional.get() == null ? false : transactional.get();
    }

    @Override
    public void beginTransaction() {
        transactional.set(true);
        localTransactionalIsCommit.set(false);
    }

    @Override
    public synchronized T get(Mode mode, String... datasource) {
        Logger.debug("开始建立会话........");
        T t = null;
        String key = DataSourceManager.getDefaultDatasourceKey();

        if (datasource.length > 0) {
            if (!Validator.isEmpty(datasource[0])) {
                key = datasource[0];
            }
        }
        Map<String, Map<Mode, List<T>>> openConnection = connections.get();
        Map<Mode, List<T>> modeTMap = null;
        List<T> tList = null;
        if (Validator.isEmpty(openConnection)) {
            openConnection = new HashMap<>();
            connections.set(openConnection);
        } else
            modeTMap = openConnection.get(key);
        if (Validator.isEmpty(modeTMap)) {
            modeTMap = new HashMap<>();
            openConnection.put(key, modeTMap);
        } else
            tList = modeTMap.get(mode);
        if (Validator.isEmpty(tList)) {
            tList = new ArrayList<T>();
            modeTMap.put(mode, tList);
        }

        t = fetch(tList, key, mode);
        if (t == null) {
            T openT = open(key, mode);
            t = openT;
            tList.add(t);
        } else {
            int count = daoConfiguration.getMultiplexing();
            int left = 0;
            //为空表明此线程是初始化状态
            if (sessionMultiplexingCount.get() == null) {
                if (count == 0)
                    count = defaultCount;
                    //最少复用数必须大于3
                else if (count < minCount)
                    count = minCount;
                count -= 2;
                sessionMultiplexingCount.set(count);
            } else {
                //为空表明计数已经用完了
                left = sessionMultiplexingCount.get();
                if (left == 0) {
                    try {
                        commit(t);
                    } catch (Exception e) {
                        Logger.error(e, "事务提交失败！");
                        rollback();
                        throw new TransactionException();
                    }

                    close(t);
                    //关闭原有会话，重新打开一个新的会话
                    t = open(key, mode);
                    tList.add(t);
                    sessionMultiplexingCount.set(count);
                } else {
                    left--;
                    sessionMultiplexingCount.set(left);
                }
            }
        }

        return t;
    }

    @Override
    public T open(String datasource, Mode mode) {
        return null;
    }

    @Override
    public T fetch(List<T> caches, String datasource, Mode mode) {
        return null;
    }

    @Override
    public void rollback() {
        if (connections.get() != null) {
            connections.get().forEach((key, modeSessionMap) -> {
                try {
                    if (!Validator.isEmpty(modeSessionMap)) {
                        modeSessionMap.forEach((k, v) -> {
                            if (!Validator.isEmpty(v))
                                v.forEach(c -> rollback(c));
                        });
                        //throw new TransactionException("事务提交失败");
                    }

                } catch (Exception ex) {
                    Logger.error(ex, "事务回滚失败！");
                }
                if (Logger.isDebugEnable())
                    Logger.debug("进行了事务回滚！");
            });
        }
        //transactional.remove();
        //connections.remove();
    }

    @Override
    public void rollback(T connection) {
    }

    @Override
    public void commit() {
        if (connections.get() != null) {
            connections.get().forEach((key, modeSessionMap) -> {
                try {
                    if (!Validator.isEmpty(modeSessionMap)) {
                        for (List<T> connections : modeSessionMap.values()) {
                            if (!Validator.isEmpty(connections)) {
                                for (T t : connections) {
                                    commit(t);
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    Logger.error(ex, "事务提交失败！");
                    rollback();
                    throw new TransactionException();
                }

            });
            localTransactionalIsCommit.set(true);
        }
    }

    @Override
    public void commit(T connection) throws SQLException {
    }

    @Override
    public void close(T connection) {
    }

    @Override
    public void close() {
        //有开启事务都没有提交，不能关闭会话
        if (transactional.get() != null)
            if (transactional.get() && !isCommit())
                return;
        if (connections.get() != null) {
            connections.get().forEach((key, modeSessionMap) -> {
                try {
                    if (!Validator.isEmpty(modeSessionMap)) {
                        modeSessionMap.forEach((k, connection) -> {
                            if (!Validator.isEmpty(connection))
                                connection.forEach(c -> close(c));
                        });
                    }

                } catch (Exception ex) {
                    Logger.error(ex, ex.getMessage());
                }
                Logger.debug("hibernate关闭了数据库链接会话,数据源为[{}]", key);
            });
            connections.get().clear();
        }
        transactional.remove();
        connections.remove();

    }

    @Override
    public Dialect getDialect(String... key) {
        return null;
    }
}
