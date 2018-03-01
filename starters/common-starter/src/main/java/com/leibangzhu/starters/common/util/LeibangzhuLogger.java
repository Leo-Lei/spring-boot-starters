package com.leibangzhu.starters.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

/**
 * Author: lili
 * Date: 2017/07/25
 * Time: 19:38
 */
public final class LeibangzhuLogger implements Logger {

    private static final String FQCN = LeibangzhuLogger.class.getName();


    public static Logger create(Class<?> logClass) {

        return new LeibangzhuLogger(LoggerFactory.getLogger(logClass));
    }

    public static Logger create(Class<?> logClass, int keepOnsiteLevel) {

        return new LeibangzhuLogger(LoggerFactory.getLogger(logClass), keepOnsiteLevel);
    }

    private final LocationAwareLogger logger;
    private int keepOnsiteLevel = 1;

    private LeibangzhuLogger(Logger logger) {

        this(logger, 1);
    }

    private LeibangzhuLogger(Logger logger, int keepOnsiteLevel) {

        this.logger = (LocationAwareLogger) logger;
        this.keepOnsiteLevel = keepOnsiteLevel;
    }

    @Override
    public String getName() {

        return logger.getName();
    }

    @Override
    public boolean isTraceEnabled() {

        return logger.isTraceEnabled();
    }

    @Override
    public void trace(String msg) {

        logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, msg, null, null);
    }

    @Override
    public void trace(String format, Object arg) {

        if (logger.isTraceEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg});
            logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {

        if (logger.isTraceEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg1, arg2});
            logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void trace(String format, Object... arguments) {

        if (logger.isTraceEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void trace(String msg, Throwable t) {

        String concatenatedErrorMessage = concatErrorMessage(t);

        trace("Error: <{}> -- {} -- on site: {}, errors' chain: {}", t.getClass().getName(), msg, getOnsite(t), concatenatedErrorMessage);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {

        return logger.isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String msg) {

        logger.log(marker, FQCN, LocationAwareLogger.TRACE_INT, msg, null, null);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {

        if (logger.isTraceEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg});
            logger.log(marker, FQCN, LocationAwareLogger.TRACE_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {

        if (logger.isTraceEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg1, arg2});
            logger.log(marker, FQCN, LocationAwareLogger.TRACE_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {

        if (logger.isTraceEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
            logger.log(marker, FQCN, LocationAwareLogger.TRACE_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {

        String concatenatedErrorMessage = concatErrorMessage(t);

        trace(marker, "Exception: <{}> -- {} -- On-site: {}, details' chain: {}", t.getClass().getName(), msg, getOnsite(t), concatenatedErrorMessage);
    }

    @Override
    public boolean isDebugEnabled() {

        return logger.isDebugEnabled();
    }

    @Override
    public void debug(String msg) {

        logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, msg, null, null);
    }

    @Override
    public void debug(String format, Object arg) {

        if (logger.isDebugEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg});
            logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {

        if (logger.isDebugEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg1, arg2});
            logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void debug(String format, Object... arguments) {

        if (logger.isDebugEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, ft.getMessage(), arguments, null);
        }
    }

    @Override
    public void debug(String msg, Throwable t) {

        String concatenatedErrorMessage = concatErrorMessage(t);

        debug("Error: <{}> -- {} -- On-site: {}, errors' chain: {}", t.getClass().getName(), msg, getOnsite(t), concatenatedErrorMessage);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {

        return logger.isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String msg) {

        logger.log(marker, FQCN, LocationAwareLogger.DEBUG_INT, msg, null, null);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {

        if (logger.isDebugEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg});
            logger.log(marker, FQCN, LocationAwareLogger.DEBUG_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {

        if (logger.isDebugEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg1, arg2});
            logger.log(marker, FQCN, LocationAwareLogger.DEBUG_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {

        if (logger.isDebugEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, ft.getMessage(), null, null);

        }
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {

        String concatenatedErrorMessage = concatErrorMessage(t);

        debug(marker, "Exception: <{}> -- {} -- On-site: {}, details' chain: {}", t.getClass().getName(), msg, getOnsite(t), concatenatedErrorMessage);
    }

    @Override
    public boolean isInfoEnabled() {

        return logger.isInfoEnabled();
    }

    @Override
    public void info(String msg) {

        logger.log(null, FQCN, LocationAwareLogger.INFO_INT, msg, null, null);
    }

    @Override
    public void info(String format, Object arg) {

        if (logger.isInfoEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg});
            logger.log(null, FQCN, LocationAwareLogger.INFO_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {

        if (logger.isInfoEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg1, arg2});
            logger.log(null, FQCN, LocationAwareLogger.INFO_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void info(String format, Object... arguments) {

        if (logger.isInfoEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            logger.log(null, FQCN, LocationAwareLogger.INFO_INT, ft.getMessage(), arguments, null);
        }
    }

    @Override
    public void info(String msg, Throwable t) {

        String concatenatedErrorMessage = concatErrorMessage(t);

        info("Error: <{}> -- {} -- On-site: {}, errors' chain: {}", t.getClass().getName(), msg, getOnsite(t), concatenatedErrorMessage);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {

        return logger.isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String msg) {

        logger.log(marker, FQCN, LocationAwareLogger.INFO_INT, msg, null, null);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {

        if (logger.isInfoEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg});
            logger.log(marker, FQCN, LocationAwareLogger.INFO_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {

        if (logger.isInfoEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg1, arg2});
            logger.log(marker, FQCN, LocationAwareLogger.INFO_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {

        if (logger.isInfoEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            logger.log(null, FQCN, LocationAwareLogger.INFO_INT, ft.getMessage(), arguments, null);
        }
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {

        String concatenatedErrorMessage = concatErrorMessage(t);

        info(marker, "Exception: <{}> -- {} -- On-site: {}, details' chain: {}", t.getClass().getName(), msg, getOnsite(t), concatenatedErrorMessage);
    }

    @Override
    public boolean isWarnEnabled() {

        return logger.isWarnEnabled();
    }

    @Override
    public void warn(String msg) {

        logger.log(null, FQCN, LocationAwareLogger.WARN_INT, msg, null, null);
    }

    @Override
    public void warn(String format, Object arg) {

        if (logger.isWarnEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg});
            logger.log(null, FQCN, LocationAwareLogger.WARN_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void warn(String format, Object... arguments) {

        if (logger.isWarnEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            logger.log(null, FQCN, LocationAwareLogger.WARN_INT, ft.getMessage(), arguments, null);
        }
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {

        if (logger.isWarnEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg1, arg2});
            logger.log(null, FQCN, LocationAwareLogger.WARN_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void warn(String msg, Throwable t) {

        String concatenatedErrorMessage = concatErrorMessage(t);

        warn("Error: <{}> -- {} -- On-site: {}, errors' chain: {}", t.getClass().getName(), msg, getOnsite(t), concatenatedErrorMessage);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {

        return logger.isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String msg) {

        logger.log(marker, FQCN, LocationAwareLogger.WARN_INT, msg, null, null);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {

        if (logger.isWarnEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg});
            logger.log(marker, FQCN, LocationAwareLogger.WARN_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {

        if (logger.isWarnEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg1, arg2});
            logger.log(marker, FQCN, LocationAwareLogger.WARN_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {

        if (logger.isWarnEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            logger.log(null, FQCN, LocationAwareLogger.WARN_INT, ft.getMessage(), arguments, null);
        }
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {

        String concatenatedErrorMessage = concatErrorMessage(t);

        warn(marker, "Exception: <{}> -- {} -- On-site: {}, details' chain: {}", t.getClass().getName(), msg, getOnsite(t), concatenatedErrorMessage);
    }

    @Override
    public boolean isErrorEnabled() {

        return logger.isErrorEnabled();
    }

    @Override
    public void error(String msg) {

        logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, msg, null, null);
    }

    @Override
    public void error(String format, Object arg) {

        if (logger.isWarnEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg});
            logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {

        if (logger.isWarnEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg1, arg2});
            logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void error(String format, Object... arguments) {

        if (logger.isWarnEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, ft.getMessage(), arguments, null);
        }
    }

    @Override
    public void error(String msg, Throwable t) {

        String concatenatedErrorMessage = concatErrorMessage(t);

        error("Error: <{}> -- {} -- On-site: {}, errors' chain: {}", t.getClass().getName(), msg, getOnsite(t), concatenatedErrorMessage);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {

        return logger.isDebugEnabled(marker);
    }

    @Override
    public void error(Marker marker, String msg) {

        logger.log(marker, FQCN, LocationAwareLogger.ERROR_INT, msg, null, null);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {

        if (logger.isWarnEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg});
            logger.log(marker, FQCN, LocationAwareLogger.ERROR_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {

        if (logger.isWarnEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, new Object[]{arg1, arg2});
            logger.log(marker, FQCN, LocationAwareLogger.ERROR_INT, ft.getMessage(), null, null);
        }
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {

        if (logger.isWarnEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, ft.getMessage(), arguments, null);
        }
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {

        String concatenatedErrorMessage = concatErrorMessage(t);

        error(marker, "Exception: <{}> -- {} -- On-site: {}, details' chain: {}", t.getClass().getName(), msg, getOnsite(t), concatenatedErrorMessage);
    }

    private String concatErrorMessage(Throwable t) {

        String errorMessage = null != t.getMessage() ? t.getMessage() : "N/A";
        if (null != t.getCause()) {

            errorMessage = String.format("%s->[%s]", errorMessage, concatErrorMessage(t.getCause()));
        }

        return String.format("<%s> -- %s -- On-site: %s", t.getClass().getName(), errorMessage, getOnsite(t));
    }

    private String getOnsite(Throwable t) {

        StackTraceElement[] stackTraces = t.getStackTrace();
        if(0 == stackTraces.length){

            return "N/A";
        }

        StringBuilder builder = new StringBuilder();
        for(int index = 0; index < stackTraces.length && index < keepOnsiteLevel; index++){

            builder.append(String.format("Level(%s): %s;   ", index + 1, stackTraces[index].toString()));
        }

        return builder.toString();
    }

}
