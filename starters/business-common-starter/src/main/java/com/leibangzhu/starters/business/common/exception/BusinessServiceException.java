package com.leibangzhu.starters.business.common.exception;

/**
 * Created by lili on 2016/10/11.
 */
public class BusinessServiceException extends Exception {

    private int errorCode;
    private String responseErrorMessage;

    public BusinessServiceException(){}

    public BusinessServiceException(String exceptionMessage){

        super(exceptionMessage);
    }

    public BusinessServiceException(String exceptionMessage, BusinessError error){

        super(exceptionMessage);
        setError(error);
    }

    public BusinessServiceException(String exceptionMessage, Throwable cause, BusinessError error){

        super(exceptionMessage, cause);
        setError(error);
    }

    private void setError(BusinessError error) {

        setErrorCode(error.getCode());
        setResponseErrorMessage(error.getMessge());
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getResponseErrorMessage() {
        return responseErrorMessage;
    }


    protected void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    protected void setResponseErrorMessage(String responseErrorMessage) {
        this.responseErrorMessage = responseErrorMessage;
    }
}
