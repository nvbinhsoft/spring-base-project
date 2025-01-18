package com.tech.springbaseproject.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;

    public BaseResponse() {}

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
