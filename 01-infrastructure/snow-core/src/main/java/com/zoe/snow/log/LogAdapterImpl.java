package com.zoe.snow.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.helpers.BasicMarker;
import org.springframework.stereotype.Component;

/**
 * @author lpw
 */
@Component("snow.util.logger")
public class LogAdapterImpl implements LogAdapter {
    private static final String[] THROWABLES = {"< ", " > ", "    "};

    protected Logger logger;
    protected boolean debugEnable = true;
    protected boolean infoEnable;
    //protected Appender appender = Appender.Console;

    public LogAdapterImpl() {
        logger = LoggerFactory.getLogger("snow.core.logger");
        debugEnable = logger.isDebugEnabled();
        infoEnable = logger.isInfoEnabled();
    }

    @Override
    public boolean isDebugEnable() {
        return debugEnable;
    }

    @Override
    public void debug(String message, Object... args) {
        logger.debug(message(null, message), args);
    }

    @Override
    public boolean isInfoEnable() {
        return infoEnable;
    }

    @Override
    public void info(String message, Object... args) {
        logger.info(message(null, message), args);
    }

    @Override
    public void warn(Throwable throwable, String message, Object... args) {
        logger.warn(message(throwable, message), args);
    }

    @Override
    public void error(Throwable throwable, String message, Object... args) {
        logger.error(message(throwable, message), args);
    }

    protected String message(Throwable throwable, String message) {
        StringBuilder sb = new StringBuilder().append(Thread.currentThread().getStackTrace()[5].getClassName()).append("###");
        append(sb, throwable);

        if (message != null)
            sb.append(message);

        return sb.toString();
    }

    protected void append(StringBuilder sb, Throwable throwable) {
        if (throwable == null)
            return;

        sb.append(THROWABLES[0]).append(throwable.getClass().getName()).append(THROWABLES[1]).append(throwable.getMessage()).append('\n');
        for (StackTraceElement stackTrace : throwable.getStackTrace())
            sb.append(THROWABLES[2]).append(stackTrace).append('\n');

        append(sb, throwable.getCause());
    }
}