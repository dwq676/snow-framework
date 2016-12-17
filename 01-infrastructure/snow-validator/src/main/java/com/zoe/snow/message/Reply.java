package com.zoe.snow.message;

/**
 * ReplyImpl
 *
 * @author Dai Wenqing
 * @date 2015/12/2
 */
public class Reply {
    /**
     * 封装执行成功返回结果说明。
     *
     * @param data 数据。
     * @return 执行成功返回结果说明。
     */

    public static Object success(Object data) {
        return ReplyHelper.replyTo(Message.Success.getType(), data);
    }


    public static Object success() {
        return ReplyHelper.replyTo(Message.Success.getType());
    }

    /**
     * 返回成功的标识代码,OK
     *
     * @return
     */
/*    @Override
    public Object ok() {
        return ReplyHelper.replyTo(success.OK);
    }

    @Override
    public Object ok(Object data) {
        return ReplyHelper.replyTo(success.OK, data);
    }*/

    public static Object failure(Message message, Object data) {
        return ReplyHelper.replyTo(message, data, -1);
    }

    public static Object failure(Message message, Object data, Object... args) {
        return ReplyHelper.replyTo(message.getType(), data, args);
    }

    public static Object failure(int code) {
        return ReplyHelper.replyTo(Message.Error, null, code);
    }

    public static Object failure(int code, Object... args) {
        return ReplyHelper.replyTo(Message.Error, null, code, args);
    }

    public static Object failure(Message message) {
        return ReplyHelper.replyTo(message);
    }

    public static Object failure() {
        return ReplyHelper.replyTo(Message.Error);
    }
}
