package com.randolph.sisyphus.dto;

import java.io.Serial;

/**
 * @author : randolph
 * date : 2024/10/1 20:37
 */
public class SingleResponse<T> extends Response {

    @Serial
    private static final long serialVersionUID = 1L;

    public T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static SingleResponse buildSuccess(){
        SingleResponse response = new SingleResponse<>();
        response.setSuccess(true);
        return response;
    }

    public static SingleResponse buildFailure(String errCode, String errMessage){
        SingleResponse response = new SingleResponse();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static <T> SingleResponse<T> of(T data){
        SingleResponse<T> response = new SingleResponse();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }
}
