package com.leibangzhu.starters.common;

import java.io.Serializable;

public class Result<T> implements Serializable {

    public static Result success(){
        return new Result(true);
    }

    public static <T> Result<T> success(T result){
        return new Result<T>(true,result);
    }

    public static Result fail(int errorCode){
        Result result = new Result(true);
        result.setErrorCode(errorCode);
        return result;
    }

    public static Result fail(String errorMsg){
        Result result = new Result(false);
        result.setErrorMsg(errorMsg);
        return result;
    }


    public static Result fail(int errorCode,String errorMsg){
        Result result = new Result(false);
        result.setErrorCode(errorCode);
        result.setErrorMsg(errorMsg);
        return result;
    }

    public Result(boolean success){
        this.success = success;
    }

    public Result(boolean success,T result){
        this.success = success;
        this.result = result;
    }

    private boolean success;
    private int errorCode;
    private String errorMsg;
    private T result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}