package com.randolph.sisyphus.idgen.exception;

import java.io.Serial;

/**
 * @author : randolph
 * date : 2024/10/3 17:26
 */
public class BaseException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private String errCode;

    public BaseException(){}

    public BaseException(String massage) {
        super(massage);
    }

    public BaseException(String errCode, String message){
        super(message);
        this.errCode = errCode;
    }

    public BaseException(String message, Throwable throwable){
        super(message, throwable);
    }

    public BaseException(String errCode, String message, Throwable throwable){
        super(message, throwable);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
