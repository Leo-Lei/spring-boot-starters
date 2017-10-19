package com.leibangzhu.starters.common.ensure;

public class ParamEnsureFailedException extends Throwable {

    public ParamEnsureFailedException() {
    }

    public ParamEnsureFailedException(String message) {
        super(message);
    }

    public ParamEnsureFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamEnsureFailedException(Throwable cause) {
        super(cause);
    }

    public ParamEnsureFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static ParamEnsureFailedException create(){
        return new ParamEnsureFailedException();
    }

    public static ParamEnsureFailedException create(String message){
        return new ParamEnsureFailedException(message);
    }

    public static ParamEnsureFailedException create(String message, Throwable cause){
        return new ParamEnsureFailedException(message, cause);
    }

    public static ParamEnsureFailedException create(Throwable cause){
        return new ParamEnsureFailedException(cause);
    }

    public static ParamEnsureFailedException create(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        return new ParamEnsureFailedException(message, cause, enableSuppression, writableStackTrace);
    }



}
