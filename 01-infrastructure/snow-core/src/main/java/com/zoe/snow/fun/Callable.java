package com.zoe.snow.fun;

/**
 * 回调用接口，带有特定类型返回值的匿名执行方法
 *
 * @author Dai Wenqing
 * @date 2016/6/17
 */
@FunctionalInterface
public interface Callable<T> {
    T call();
}
