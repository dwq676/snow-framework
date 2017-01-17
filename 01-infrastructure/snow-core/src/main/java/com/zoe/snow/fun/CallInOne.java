package com.zoe.snow.fun;

/**
 * 传入一个参数的调用
 * 做相应的逻辑处理将参数再返回
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2016/12/27
 */
@FunctionalInterface
public interface CallInOne<T> {
    T one(T param);
}
