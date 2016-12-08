package com.zoe.snow.dao.hibernate;

import com.zoe.snow.message.Message;
import com.zoe.snow.model.Model;

/**
 * 辅助验证器
 * 前置条件不满足时，不强制进行验证
 *
 * @author Dai Wenqing
 * @date 2016/10/19
 */
public interface ValidateManager {
    /**
     * 执行验证
     *
     * @param value      需要验证的值
     * @param parameters 验证所需要准备的参数，比如值范围
     * @return
     */
    void validate(Class<? extends Model> classZ, String fieldName, Object value, Object[] parameters, String... datasource);

    /**
     * 获取错误消息如果有错误的话
     *
     * @return
     */
    String getMessage();
}
