package com.cos.aopdemo.domain;

import lombok.Data;

@Data
public class CommonDto<T> {
    private int statusCode;
    private T data;

    public CommonDto(int statusCode) {
        this.statusCode = statusCode;
    }

    public CommonDto(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }
}
