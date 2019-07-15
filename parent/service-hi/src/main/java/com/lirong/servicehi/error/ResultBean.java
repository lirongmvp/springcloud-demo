package com.lirong.servicehi.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Title: ResultBean <br>
 * Description: ResultBean <br>
 * Date: 2019年01月21日
 *
 * @author 王东旭
 * @version 1.0.0
 * @since jdk8
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultBean {

    /**
     * 信息代码
     */
    private String code;
    /**
     * 信息说明
     */
    private String msg;

    private Integer total;
    private Long totalCount;
    /**
     * 返回数据或jqgrid中的root
     */
    private Object result;

    protected ResultBean() {

    }

    public ResultBean(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultBean(String code) {
        this.code = code;
    }

    public ResultBean(String code, String msg, Object result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public ResultBean(String code, String msg, int total, long totalCount, Object result) {
        this.code = code;
        this.msg = msg;
        this.total = total;
        this.totalCount = totalCount;
        this.result = result;
    }

    public static ResultBean ok() {
        return new ResultBean(GlobalCodeEnum.SUCCESS.getCode(), GlobalCodeEnum.SUCCESS.getMsg());
    }

    public static ResultBean ok(Object result, int total, long totalCount) {
        return new ResultBean(GlobalCodeEnum.SUCCESS.getCode(), GlobalCodeEnum.SUCCESS.getMsg(), total, totalCount,
                result);
    }

    public static ResultBean ok(Object result) {
        return new ResultBean(GlobalCodeEnum.SUCCESS.getCode(), GlobalCodeEnum.SUCCESS.getMsg(), result);
    }

    public static ResultBean error() {
        return new ResultBean(GlobalCodeEnum.SYSTEM_ERROR.getCode(), GlobalCodeEnum.SYSTEM_ERROR.getMsg());
    }

    public static ResultBean error(String msg) {
        return new ResultBean(GlobalCodeEnum.SYSTEM_ERROR.getCode(), msg);
    }

    public static ResultBean error(String code, String msg) {
        return new ResultBean(code, msg);
    }
}