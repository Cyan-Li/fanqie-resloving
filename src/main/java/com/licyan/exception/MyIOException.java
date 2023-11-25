package com.licyan.exception;

public class MyIOException extends Exception{

    public MyIOException (String message){
        super(message);
        System.out.println("文件读写异常……");
    }

}
