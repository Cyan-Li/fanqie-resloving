package com.licyan.enums;

public enum HttpEnum {

    SUCCESS(200,"请求成功"),
    ;

    private Integer code;

    private String Message;

    HttpEnum(Integer code, String message) {
        this.code = code;
        Message = message;
    }

    HttpEnum() {
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return Message;
    }
}
