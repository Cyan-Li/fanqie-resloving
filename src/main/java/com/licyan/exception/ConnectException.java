package com.licyan.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 连接网络异常
 */
@Slf4j
public class ConnectException extends Exception{

    public ConnectException(String message){
        super(message);
        log.error("连接网络异常，异常信息为：" + message);
    }

}
