package com.zoe.snow.auth;

/**
 * StatusEnum
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/5/26
 */
public enum StatusEnum {

    Normal(1), Locked(2);

    private int status;

    StatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

}
