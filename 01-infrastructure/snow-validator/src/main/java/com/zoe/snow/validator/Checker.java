package com.zoe.snow.validator;


import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.message.Message;
import com.zoe.snow.message.MessageTool;
import com.zoe.snow.message.ReplyHelper;
import com.zoe.snow.message.ReplyUseless;
import com.zoe.snow.model.enums.Rules;

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
    boolean validate(Object value, Object... parameters);

    /**
     * 输出错误消息
     *
     * @param args 代码参数
     * @return 错误消息
     */
    default Message illegal(Object... args) {
        MessageTool messageTool = BeanFactory.getBean(MessageTool.class);
        return Message.BadRequest.setArgs(messageTool.get("error.parameter." + this.getName(), args));
    }

    /**
     * 获取验证器的名称
     *
     * @return
     */
    String getName();

    default Checker getChecker(Rules rules) {
        return BeanFactory.getBean(rules.getType());
    }
}
