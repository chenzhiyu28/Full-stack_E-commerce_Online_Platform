package com.leopold.store.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponse<T> implements Serializable {
    private Integer code;
    private String status;
    private String message;
    private T data;

    public JsonResponse(Integer code) {
        this.code = code;
    }

    public JsonResponse(Throwable e) {
        this.message = e.getMessage();
    }

    public JsonResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
    }
}
