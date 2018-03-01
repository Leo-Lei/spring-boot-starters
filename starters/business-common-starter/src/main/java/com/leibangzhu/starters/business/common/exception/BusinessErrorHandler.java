package com.leibangzhu.starters.business.common.exception;

import com.leibangzhu.starters.common.util.StringUtil;
import org.slf4j.Logger;

/**
 * Author: lili
 * Date: 2016/10/31
 * Time: 16:34
 */

public final class BusinessErrorHandler {


    public static void handle(BusinessError error) throws BusinessServiceException {

        handle(error, null, null, null);
    }

    public static void handle(BusinessError error, Logger logger) throws BusinessServiceException {

        handle(error, logger, null, null);
    }

    public static void handle(BusinessError error, Logger logger, Throwable t) throws BusinessServiceException {

        handle(error, logger, t, null);
    }

    public static void handle(BusinessError error, Logger logger, String extraMessage) throws BusinessServiceException {

        handle(error, logger, null, extraMessage);
    }

    public static void handle(BusinessError error, Logger logger, Throwable t, String extraMessage) throws BusinessServiceException {

        String errorMessage = StringUtil.isNotEmpty(extraMessage) ? extraMessage : error.getMessge();
        if (null != logger) {

            if(null != t){

                logger.error(errorMessage, t);
            }else{

                logger.error(errorMessage);
            }
        }

        throw new BusinessServiceException(errorMessage, error);
    }
}
