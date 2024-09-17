package com.example.hm.handler_exception;

public class CustomException extends RuntimeException {
    private final String code;

    public CustomException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
