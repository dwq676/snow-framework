package com.zoe.snow.listener;

import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 监听器启动列表
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/3/27
 */

public interface StartListenerList {
    Collection<String> getListenerList();
}
