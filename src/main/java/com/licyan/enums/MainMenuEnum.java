package com.licyan.enums;

public enum MainMenuEnum {

    FIRST(1,"单本解析"),
    SECOND(2,"批量解析"),
    THIRD(3,"更新指定小说"),
    FOURTH(4,"批量更新小说"),
    END(3,"退出");


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
