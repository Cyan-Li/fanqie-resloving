package com.licyan.enums;

public enum MainMenuEnum {

    FIRST(1,"单本解析 当前版本：如已存在，会重新下载"),
    SECOND(2,"批量解析 当前版本：如已存在，会重新下载"),
    THIRD(3,"更新指定小说 暂无功能，敬请期待"),
    FOURTH(4,"批量更新小说 暂无功能，敬请期待"),
    END(5,"退出");


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
