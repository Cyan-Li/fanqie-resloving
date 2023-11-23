package com.licyan.utils;

import com.licyan.enums.HttpEnums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import com.licyan.pojo.Chapter;
import com.licyan.pojo.HttpHeader;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class HttpUtils {

    /**
     * get请求
     * @param url
     * @param header
     * @return
     * @throws IOException
     */
    public HttpURLConnection getMethod(String url, HttpHeader header) throws IOException {
        URL con = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) con.openConnection();
        connection.setRequestMethod("GET");
        extracted(header, connection);
        int responseCode = connection.getResponseCode();
        if (HttpEnums.SUCCESS.getCode() == responseCode) {
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
