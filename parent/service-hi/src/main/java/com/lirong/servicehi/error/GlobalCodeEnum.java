package com.lirong.servicehi.error;

/**
 * 系统全局编码
 *
 * @Description -1-99, 避开HTTP状态码100-600
 */
public enum GlobalCodeEnum {

    /**
     * 成功
     */
    SUCCESS("0", "成功"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR("1", "系统错误"),
    /**
     * 请求参数无效
     */
    INVALID_PARAMS("100", "请求参数无效"),

    USER_AUTHENTICATION_ERROR("104", "用户认证错误"),
    /**
     * 未知URI
     */
    UNSUPPORTED_URI("3", "未知的方法"),
    /**
     * 接口调用次数已达到设定的上限
     */
    TOUCH_API_LIMIT("4", "接口调用次数已达到设定的上限"),

    UNAUTHORIZED_IP("5", "请求来自未经授权的IP地址"),

    /**
     * 无访问权限
     */
    NO_AUTHORIZATION("6", "无访问权限"),
    /**
     * 数据库异常
     */
    // 持久层错误
    DB_ERROR("20", "数据库异常"),
    /**
     * 搜索引擎异常
     */
    SOLR_ERROR("21", "搜索引擎异常"),
    /**
     * 消息中间件异常
     */
    // 消息中间件错误
    MQ_ERROR("30", "消息中间件异常");

    private String code;
    private String msg;

    private GlobalCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
