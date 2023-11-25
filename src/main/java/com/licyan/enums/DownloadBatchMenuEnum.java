package com.licyan.enums;

public enum DownloadBatchMenuEnum {

    FIRST(1,"继续批量解析"),
    END(2,"返回上一级");

    private Integer code;

    private String operate;

    DownloadBatchMenuEnum(Integer code, String operate) {
        this.code = code;
        this.operate = operate;
    }

    DownloadBatchMenuEnum() {
    }

    public Integer getCode() {
        return code;
    }

    public String getOperate() {
        return operate;
    }


}
