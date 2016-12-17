package com.zoe.snow.validator;


import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.message.Message;
import com.zoe.snow.message.ReplyUseless;

/**
 * 验证器接口
 *
 * @author Dai Wenqing
 * @date 2015/12/7
 */
public interface Checker {

    /**
     * 验证
     *
     * @param value      参数值
     * @param parameters 参数
     * @return
     */
    boolean validate(Object value, Object[] parameters);

    /**
     * 输出错误消息
     *
     * @param message 错误代码
     * @param args    代码参数
     * @return 错误消息
     */
    default Object failure(Message message, Object... args) {
        return BeanFactory.getBean(ReplyUseless.class).failure(message, null, args);
    }

    /**
     * 获取验证器的名称
     *
     * @return
     */
    String getName();
}
