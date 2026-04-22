package com.goncharova.labs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@SuppressWarnings("unused")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Universal response dto")
public class UniversalResponse<T> {
    @Schema(description = "Business error code", requiredMode = Schema.RequiredMode.REQUIRED)
    private int code;
    @Schema(description = "Message", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String message;
    private T data;

    public UniversalResponse(T data) {
        this.code = 0;
        this.message = "SUCCESS";
        this.data = data;
    }

    public UniversalResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

