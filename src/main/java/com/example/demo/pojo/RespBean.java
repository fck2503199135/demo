package com.example.demo.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * 接口统一返回的bean
 */
@Data
@ToString
public class RespBean {
    private Integer status;
    private String msg;
    private Object data;

    private RespBean() {
    }

    public static RespBean build() {
        return new RespBean();
    }

    public static RespBean ok(String msg, Object data) {
        return new RespBean(200, msg, data);
    }

    public static RespBean ok(String msg) {
        return new RespBean(200, msg, null);
    }

    public static RespBean error(String msg, Object data) {
        return new RespBean(500, msg, data);
    }

    public static RespBean error(String msg) {
        return new RespBean(500, msg, null);
    }

    private RespBean(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
