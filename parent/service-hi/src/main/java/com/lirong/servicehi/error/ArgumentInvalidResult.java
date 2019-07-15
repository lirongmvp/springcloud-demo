package com.lirong.servicehi.error;

/**
 * Title: ArgumentInvalidResult <br>
 * Description: ArgumentInvalidResult <br>
 * Date: 2019年02月01日
 *
 * @author LiRong
 * @version 1.0.0
 * @since jdk8
 */
public class ArgumentInvalidResult {
    /**
     * 属性
     */
    private String field;
    /**
     * 原始值
     */
    private Object rejectedValue;
    /**
     * 错误信息
     */
    private String defaultMessage;

    public ArgumentInvalidResult(String field, Object rejectedValue, String defaultMessage) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.defaultMessage = defaultMessage;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String toString() {
        return defaultMessage;
    }
}
