package com.zoe.snow.cache.strategy.redis;

import com.zoe.snow.cache.Cache;
import com.zoe.snow.cache.CacheItemPriority;
import com.zoe.snow.cache.Element;
import com.zoe.snow.cache.ExpirationWay;
import com.zoe.snow.cache.strategy.CacheStrategy;
import com.zoe.snow.listener.ContextRefreshedListener;
import com.zoe.snow.util.Converter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * RedisCache
 *
 * @author Dai Wenqing
 * @date 2016/2/25
 */
@Service("snow.cache.strategy.redis")
public class RedisCache implements CacheStrategy, ContextRefreshedListener {

    @Value("${snow.cache.name:}")
    private String name;
    @Value("${snow.cache.redis.port:6379}")
    private int port;
    @Value("${snow.cache.redis.password}")
    private String password;
    @Value("${snow.cache.redis.host:localhost}")
    private String host;
    @Value("${snow.cache.redis.max-total:500}")
    private int total;
    @Value("${snow.cache.redis.max-idle:5}")
    private int idle;
    @Value("${snow.cache.redis.max-wait:500}")
    private long wait;
    private JedisPool pool;

    @Override
    public String getName() {
        return "RedisCache";
    }

    @Override
    public <T> void put(String key, T o) {
        put(key, o, ExpirationWay.SlidingTime, -1, CacheItemPriority.Default);
    }

    @Override
    public <T> void put(String key, T o, ExpirationWay expirationWay, long time) {
        put(key, o, expirationWay, time, CacheItemPriority.Default);

    }

    @Override
    public <T> void put(String key, T o, ExpirationWay expirationWay, long time, CacheItemPriority cacheItemPriority) {
        Jedis jedis = pool.getResource();
        jedis.select(2);
        Element element = Cache.getElement(key, o, expirationWay, time, CacheItemPriority.Default);
        jedis.set(key, Converter.toString(element));

        if (time > 0) {
            int t = (int) time;
            jedis.expire(key, t / 1000);
        }
        jedis.close();
    }

    @Override
    public <T> T get(String key) {
        Jedis jedis = pool.getResource();
        jedis.select(2);
        Element element = Converter.fromJson(jedis.get(key), Element.class);
        return (T) element.getValue();
    }

    @Override
    public void remove(String key) {
        Jedis jedis = pool.getResource();
        jedis.select(2);
        String value = jedis.get(key);
        jedis.del(key);
    }

    @Override
    public int getContextRefreshedSort() {
        return 7;
    }

    @Override
    public void onContextRefreshed() {
        /*if (!getName().equals(name))
            return;*/

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(total);
        config.setMaxIdle(idle);
        config.setMaxWaitMillis(wait);
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, host, port, 1000, password);
    }
}
