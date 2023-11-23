package com.licyan.pojo;

import lombok.Data;

import java.util.Map;

@Data
public class HttpHeader {

    // private  connectTimeout;

    private Integer connectTimeout;

    private Integer readTimeout;

    private Map<String,String> headerMap;
}
