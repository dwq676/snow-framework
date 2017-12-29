package com.zoe.snow.conf;

/**
 * Configuration
 *
 * @author Dai Wenqing
 * @date 2016/1/19
 */
public interface Configuration {
    default String getSessionName() {
        return "http";
    }

    default String getDeploy() {
        return "web";
    }

    default String getProjectName() {
        return "";
    }
}
