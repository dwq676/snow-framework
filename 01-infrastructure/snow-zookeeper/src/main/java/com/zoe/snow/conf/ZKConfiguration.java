package com.zoe.snow.conf;

/**
 * ZKConfiguration
 *
 * @author Dai Wenqing
 * @date 2016/1/26
 */
public interface ZKConfiguration {
    /**
     * 宿主地址及端口号
     * A.B.C.D:Port
     * @return
     */
    String host();

    /**
     * 会话过期时间
     * 默认时间为120000毫秒
     * @return
     */
    default int sessionTimeOut() {
        return 120000;
    }
}
