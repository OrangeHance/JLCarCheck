package com.mvp.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final int code;

    public BusinessException(String message) {
        super(message);
        this.code = 500; // 默认500
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
}