package com.licyan.utils;

import com.licyan.enums.HttpEnum;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.licyan.exception.ConnectException;
import com.licyan.pojo.HttpHeader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpUtils {

    /**
     * get请求
     * @param url
     * @param header
     * @return
     * @throws IOException
     */
    public HttpURLConnection getMethod(String url, HttpHeader header) throws IOException, ConnectException {
        URL con = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) con.openConnection();
        connection.setRequestMethod("GET");
        extracted(header, connection);

        //初始化状态码
        int responseCode;

        try{
            responseCode = connection.getResponseCode();
        }catch (Exception e){
            throw new ConnectException("连接网络异常: " + e.getMessage());
        }

        if (HttpEnum.SUCCESS.getCode() == responseCode) {
            return connection;
        } else {
            log.error("请求失败，状态码为：{}", responseCode);
            throw new RuntimeException("请求失败");
        }
    }

    /**
     * 组装请求头
     * @param header
     * @param connection
     */
    private void extracted(HttpHeader header, HttpURLConnection connection) {
        if (null != header.getConnectTimeout()) {
            connection.setConnectTimeout(header.getConnectTimeout());
        }
        if (null != header.getReadTimeout()) {
            connection.setReadTimeout(header.getReadTimeout());
        }
        Map<String, String> headerMap = header.getHeaderMap();
        if(null != headerMap && !headerMap.isEmpty()){
            for (Map.Entry<String, String> stringStringEntry : header.getHeaderMap().entrySet()) {
                connection.setRequestProperty(stringStringEntry.getKey(),stringStringEntry.getValue());
            }
        }
    }
}
