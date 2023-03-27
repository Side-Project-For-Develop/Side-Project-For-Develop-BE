package com.h10.sideproject.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.h10.sideproject.common.exception.ErrorCode;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage<T> {
    private String msg;
    private int statusCode;
    private T data;

    public ResponseMessage(String msg, int statusCode, T data) {
        this.msg = msg;
        this.statusCode = statusCode;
        this.data = data;
    }


    public ResponseMessage(MessageCode messageCode, T data) {
        this.msg = messageCode.getMsg();
        this.statusCode = messageCode.getStatusCode();
        this.data = data;
    }

    public ResponseMessage(ErrorCode errorCode, T data) {
        this.msg = errorCode.getMsg();
        this.statusCode = errorCode.getStatusCode();
        this.data = data;
    }


}
