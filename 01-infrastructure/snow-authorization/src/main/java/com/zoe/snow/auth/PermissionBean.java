package com.zoe.snow.auth;

/**
 * 验证权限对象，可是URL对象，也可以是按钮
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/11/29
 */
public class PermissionBean {
    /**
     * 权限名称
     * url button or a mapping key
     *
     * @return
     */
    private String url;

    /**
     * permission type
     *
     * @return
     */
    private String type;

    /**
     * get put post delete
     *
     * @return
     */
    private String method;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
