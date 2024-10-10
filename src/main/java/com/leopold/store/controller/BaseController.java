package com.leopold.store.controller;

import com.leopold.store.util.JsonResponse;
import com.leopold.store.util.ResponseCode;
import jakarta.servlet.http.HttpSession;

public abstract class BaseController {
    protected final String STATE_SUCCESS = "success";
    protected final String STATE_ERROR = "ERROR";

    public <T> JsonResponse<T> successResponse(T data) {
        JsonResponse<T> response = new JsonResponse<>();

        response.setCode(ResponseCode.CODE_200.getCode());
        response.setMessage(ResponseCode.CODE_200.getMessage());
        response.setStatus(STATE_SUCCESS);
        response.setData(data);

        return response;
    }

    public <T> JsonResponse<T> ServerErrorResponse(T data) {
        JsonResponse<T> response = new JsonResponse<>();

        response.setCode(ResponseCode.CODE_500.getCode());
        response.setMessage(ResponseCode.CODE_500.getMessage());
        response.setStatus(STATE_ERROR);
        response.setData(data);

        return response;
    }

    public <T> JsonResponse<T> generalErrorResponse(T data, Integer code, String msg) {
        JsonResponse<T> response = new JsonResponse<>();

        response.setCode(code);
        response.setMessage(msg);
        response.setStatus(STATE_ERROR);
        response.setData(data);

        return response;
    }


    protected final Integer getUIDFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}
