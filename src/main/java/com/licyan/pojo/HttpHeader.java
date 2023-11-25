package com.licyan.pojo;

import lombok.Data;

import java.util.Map;

/**
 * 请求头实体类
 */
@Data
public class HttpHeader {

    private Integer connectTimeout = 5000;

    private Integer readTimeout = 5000;

    private Map<String,String> headerMap;
}
