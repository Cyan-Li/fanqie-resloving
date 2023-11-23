package com.licyan.enums;

public enum MainMenuEnum {

    FIRST(1,"单本解析"),
    END(2,"退出");


    private Integer code;

    private String operate;

    MainMenuEnum(Integer code, String operate) {
        this.code = code;
        this.operate = operate;
    }

    MainMenuEnum() {
    }

    public Integer getCode() {
        return code;
    }

    public String getOperate() {
        return operate;
    }
}
