package com.h10.sideproject.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage<T> {
    private String msg;
    private int statusCode;
    private T data;

    public ResponseMessage(MessageCode messageCode, T data) {
        this.msg = messageCode.getMsg();
        this.statusCode = messageCode.getStatusCode();
        this.data = data;
    }
}
