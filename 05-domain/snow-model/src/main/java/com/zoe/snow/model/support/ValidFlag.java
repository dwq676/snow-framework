package com.zoe.snow.model.support;

/**
 * ValidFlag
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/26
 */
public interface ValidFlag {
    /**
     * 获取标志。
     *
     * @return 标志。
     */
    int getValidFlag();

    /**
     * 设置标志。 0 = 表示有效
     *
     * @param validFlag 标志。
     */
    void setValidFlag(int validFlag);
}
