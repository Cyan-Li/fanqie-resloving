package com.licyan.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 连接网络异常
 */
@Slf4j
public class MyConnectException extends Exception{

    public MyConnectException(String message){
        super(message);
        log.error("网络异常……");
    }

}
