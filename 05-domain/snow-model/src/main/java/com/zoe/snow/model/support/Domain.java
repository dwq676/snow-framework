package com.zoe.snow.model.support;

/**
 * Domain
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/26
 */
public interface Domain {
    /**
     * 获取域。
     *
     * @return 域。
     */
    String getDomain();

    /**
     * 设置域。
     *
     * @param domain 域。
     */
    void setDomain(String domain);

    default String getDomainName() {
        return "domain";
    }
}
