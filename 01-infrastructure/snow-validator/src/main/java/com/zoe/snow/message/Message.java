package com.zoe.snow.message;

/**
 * Message
 *
 * @author Dai Wenqing
 * @date 2015/11/28
 */
public enum Message {

    /**
     * 成功操作
     */
    Success("msg.200"),

    NoContent("msg.204"),

    Failed("msg.424"),

    BadRequest("msg.400"),
    /**
     * 未授权
     */
    UnAuthorized("msg.401"),

    LoginLock("msg.423"),

    InvalidToken("msg.403"),

    MethodNotAllow("msg.405"),

    /**
     * 信息已经存在
     */
    Exist("msg.40011"),

    /**
     * 非法输入
     */
    InputIllegal("msg.40012"),

    /**
     * 业务逻辑相关
     */
    //Business("msg.40001"),
    /**
     * 通用错误提醒，可追加原因
     */
    Error("error"),

    /* 调用服务失败 */
    ServiceError("msg.50001"),

    NoExist("msg.20051"),

    MustNotEmpty("msg.40010"),

    HasDependency("msg.40013"),

    /**
     * 添加失败
     *//*
    AddError("error.add"),

    *//**
     * 更新失败
     *//*
    UpdateError("error.update"),

    *//**
     * 删除失败
     *//*
    DeleteError("error.delete"),*/
    /* 当前位置的参数为空 */
    ParameterPositionInHolderIsnull("error.parameter.position-in-holder-is-null"),
    /**
     * 无法查询到相关记录
     */
    SelectNoAnyRecord("msg.20052"),

    /**
     * 重复操作
     */
    RepeatDoWork("error.repeat.dowork"),

    IllegalOperate("error.illegal.operate"),

    NoTellAnyReason("error.not.tell.any.reason"),

    Busy("error.system.dispatch.busy"),

    ExistsRecycle("error.exists.recycle"),

    ExistsRecycleFailure("error.exists.recycle.failure"),

    CheckerNotFound("error.checker.not.find"),

    LoginError("msg.40002");

    private String type;
    private String[] args;

    Message(String type) {
        this.type = type;
        this.args = args;
    }

    public String[] getArgs() {
        return this.args;
    }

    public Message setArgs(String... args) {
        this.args = args;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
