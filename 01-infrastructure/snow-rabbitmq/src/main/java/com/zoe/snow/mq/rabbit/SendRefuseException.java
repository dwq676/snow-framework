package com.zoe.snow.mq.rabbit;

/**
 * SendRefuseException
 *
 * @author Dai Wenqing
 * @date 2016/6/21
 */
public class SendRefuseException extends Throwable {
    private String message;

    public SendRefuseException(String message) {
        super();
        this.message = message;
    }

    public SendRefuseException(Throwable e) {
        super();
        this.message = e.getMessage();
    }

    public SendRefuseException(String message, Throwable e) {
        super();
        this.message = message + "," + e.getMessage();
    }
}
