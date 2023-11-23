package com.licyan.enums;

public enum DownloadOneMenuEnum {

    FIRST(1,"继续单本解析"),
    END(2,"返回上一级");

    private Integer code;

    private String operate;

    DownloadOneMenuEnum(Integer code, String operate) {
        this.code = code;
        this.operate = operate;
    }

    DownloadOneMenuEnum() {
    }

    public Integer getCode() {
        return code;
    }

    public String getOperate() {
        return operate;
    }

}