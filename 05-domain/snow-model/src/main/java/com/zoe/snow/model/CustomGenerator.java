package com.zoe.snow.model;

import java.io.Serializable;

/**
 * 主键算定义生成器
 *
 * @author Dai Wenqing
 * @date 2016/10/26
 */
public interface CustomGenerator {
    /**
     * 生成接口
     *
     * @return
     */
    Serializable generate();
}
