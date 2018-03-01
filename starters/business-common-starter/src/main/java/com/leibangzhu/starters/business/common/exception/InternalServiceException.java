package com.leibangzhu.starters.business.common.exception;

import com.leibangzhu.starters.common.resource.ResourceManager;

/**
 * Created by reinhard on 02/08/2017.
 */
public class InternalServiceException extends BusinessServiceException {

    public static final int INTERNAL_ERROR_CODE = -101;

    public InternalServiceException(String errorMessage){

        super(errorMessage);

        setErrorCode(INTERNAL_ERROR_CODE);
        setResponseErrorMessage(errorMessage);
    }

    public InternalServiceException(String errorMessage, IErrorCodeProvider errorCodeProvider){

        super(errorMessage);

        setErrorCode(errorCodeProvider.getErrorCode());

        ResourceManager resourceManager = new ResourceManager();
        setResponseErrorMessage(resourceManager.getResourceString("ServiceError", errorCodeProvider.getErrorCode()));
    }
}
