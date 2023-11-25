package com.licyan.enums;

public enum ConnectExceptionEnum {


    CONNECT_LOST(1001,"连接丢失");

    private Integer code;

    private String message;

    ConnectExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
