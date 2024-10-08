package com.randolph.sisyphus.dto;

import java.io.Serial;

/**
 * Response with single record to return
 * <p/>
 * Created by Danny.Lee
 *
 * @author <a href = "mailto:randolph_lu@163.com">randolph<a/>
 * @since 5.0.0
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

    public static <T> SingleResponse<T> fastFailure(String errCode, String errMessage){
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static <T> SingleResponse<T> of(T data){
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }
}
