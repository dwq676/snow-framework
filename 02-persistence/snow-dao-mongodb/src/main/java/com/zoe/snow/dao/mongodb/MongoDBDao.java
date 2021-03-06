package com.zoe.snow.dao.mongodb;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import java.util.ArrayList;

/**
 * 类名： MongoDBDao 包名： com.newsTest.dao 作者： Dai Wenqing 时间： 2014-8-30 下午03:46:55
 * 描述： TODO(这里用一句话描述这个类的作用)
 */
public interface MongoDBDao {
    /**
     *
     * 描述：获取指定的mongodb数据库
     * 
     * @param dbName
     * @return
     */
    public DB getDb(String dbName);

    /**
     *
     * 描述：获取指定mongodb数据库的collection集合
     * 
     * @param dbName
     *            数据库名
     * @param collectionName
     *            数据库集合
     * @return
     */
    public DBCollection getCollection(String dbName, String collectionName);

    /**
     *
     * 描述：向指定的数据库中添加给定的keys和相应的values
     * 
     * @param dbName
     * @param collectionName
     * @param keys
     * @param values
     * @return
     */
    public boolean insert(String dbName, String collectionName, String[] keys, Object[] values);

    /**
     *
     * 描述：删除数据库dbName中，指定keys和相应values的值
     * 
     * @param dbName
     * @param collectionName
     * @param keys
     * @param values
     * @return
     */
    public boolean delete(String dbName, String collectionName, String[] keys, Object[] values);

    /**
     *
     * 描述：从数据库dbName中查找指定keys和相应values的值
     * 
     * @param dbName
     * @param collectionName
     * @param keys
     * @param values
     * @param num
     * @return
     */
    public ArrayList<DBObject> find(String dbName, String collectionName, String[] keys, Object[] values, int num);

    /**
     *
     * 描述：更新数据库dbName，用指定的newValue更新oldValue
     * 
     * @param dbName
     * @param collectionName
     * @param oldValue
     * @param newValue
     * @return
     */
    public boolean update(String dbName, String collectionName, DBObject oldValue, DBObject newValue);

    /**
     *
     * 描述：判断给定的keys和相应的values在指定的dbName的collectionName集合中是否存在
     * 
     * @param dbName
     * @param collectionName
     * @param key
     * @param value
     * @return
     */
    public boolean isExit(String dbName, String collectionName, String key, Object value);
}
