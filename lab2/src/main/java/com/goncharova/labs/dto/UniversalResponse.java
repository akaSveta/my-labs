package com.goncharova.labs.dto;

public class UniversalResponse<T> {
    private int code;
    private String message;
    private T data;

    public UniversalResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public UniversalResponse(T data) {
        this.code = 0;
        this.message = "Success";
        this.data = data;
    }

    public int getCode() { return code; }
    //public void setCode(int code) { this.code = code; }

    public String getMessage() { return message; }
    //public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    //public void setData(T data) { this.data = data; }
}