package com.leibangzhu.starters.common.ensure;

import com.leibangzhu.starters.common.util.LeibangzhuLogger;
import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by reinhard on 01/08/2017.
 */
public class EnsureResult {

    private static final Logger logger = LeibangzhuLogger.create(EnsureResult.class);

    private static final EnsureResult successEnsureResult = new EnsureResult(true);
    private static final EnsureResult failEnsureResult = new EnsureResult(false);

    private boolean result;

    public EnsureResult(boolean result){

        this.result = result;
    }

    public <E extends Throwable> void throwExceptionIfFail(Class<E> exception, String errorMessage) throws E{

        if(false == result) {

            throwGeneric(exception, new Class[]{String.class}, new Object[]{errorMessage});
        }
    }

    public <E extends Throwable> void throwExceptionIfFail(Class<E> exception, Class[] parameterTypes, Object[] parameterValues) throws E{

        if(false == result) {

            throwGeneric(exception, parameterTypes, parameterValues);
        }
    }

    public <E extends Throwable> void throwExceptionIfFail(Class<E> exception) throws E{

        if(false == result) {

            throwCustom(exception);
        }
    }

    private <E extends Throwable> void throwCustom(Class<E> exception) throws E {

        try {

            throw exception.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private <E extends Throwable> void throwGeneric(Class<E> exception, Class[] parameterTypes, Object[] parameterValues) throws E {

        if(parameterTypes.length != parameterValues.length){

            logger.error(String.format("Size of parameter type (%s) does not match that of parameter value (%s)", parameterTypes.length, parameterValues.length));
        }

        try {

            Constructor<E>  constructor = exception.getConstructor(parameterTypes);
            if(null != constructor){

                throw constructor.newInstance(parameterValues);
            }else{

                logger.error("There is no matched constructor for given parameter types.");
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {

            logger.error("Unknown error while throw custom exception in Ensure.");
        }

    }

    public static EnsureResult pass() {

        return successEnsureResult;
    }

    public static EnsureResult fail() {

        return failEnsureResult;
    }
}
