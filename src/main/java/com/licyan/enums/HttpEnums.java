package com.licyan.enums;

public enum HttpEnums {

    SUCCESS(200,"请求成功"),
    ;

    private Integer code;

    private String Message;

    HttpEnums(Integer code, String message) {
        this.code = code;
        Message = message;
    }

    HttpEnums() {
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return Message;
    }
}
