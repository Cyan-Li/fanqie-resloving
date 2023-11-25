package com.licyan.utils;

import com.licyan.enums.HttpEnum;
import com.licyan.exception.MyConnectException;
import com.licyan.pojo.HttpHeader;

import java.io.IOException;
import java.net.*;
import java.util.Map;

/**
 * Http工具类
 */
public class HttpUtils {

    /**
     * get请求
     * @param url 请求地址
     * @param httpHeader 请求头
     * @return
     */
    public HttpURLConnection get(String url, HttpHeader httpHeader) throws MyConnectException, ProtocolException {

        URL requestUrl = null;
        try {
            requestUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new MyConnectException("请求地址不合法");
        }
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
        } catch (IOException e) {
            throw new MyConnectException("网络异常");
        }

        //设置请求头
        if(null != httpHeader){
            urlConnection.setConnectTimeout(httpHeader.getConnectTimeout());
            urlConnection.setReadTimeout(httpHeader.getReadTimeout());
            if(null != httpHeader.getHeaderMap()){
                for (Map.Entry<String, String> stringStringEntry : httpHeader.getHeaderMap().entrySet()) {
                    urlConnection.setRequestProperty(stringStringEntry.getKey(),stringStringEntry.getValue());
                }
            }
        }

        urlConnection.setRequestMethod("GET");

        int responseCode = 0;
        try {
            responseCode = urlConnection.getResponseCode();
        } catch (IOException e) {
            throw new MyConnectException("连接失败");
        }

        if(HttpEnum.SUCCESS.getCode() == responseCode){
            //请求成功
            return urlConnection;
        }else {
            throw new MyConnectException("请求失败");
        }

    }

}
