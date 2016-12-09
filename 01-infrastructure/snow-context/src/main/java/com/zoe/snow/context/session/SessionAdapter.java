package com.zoe.snow.context.session;

/**
 * SessionAdapter
 *
 * @author Dai Wenqing
 * @date 2016/3/2
 */
public interface SessionAdapter {
    String getSessionId();

    String getName();

    long getExpiration();

    /**
     * @param key
     * @param <T>
     * @return
     */
    <T> T get(String key);

    /**
     * @param key
     * @param o
     * @param <T>
     */
    <T> void put(String key, Object o);

    /**
     * @param key
     * @param <T>
     */
    <T> void remove(String key);
}
