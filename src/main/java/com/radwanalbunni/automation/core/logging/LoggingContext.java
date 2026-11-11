package com.radwanalbunni.automation.core.logging;

import org.apache.logging.log4j.ThreadContext;

public final class LoggingContext {

    private static final String LOG_FILE_KEY = "logFileName";

    private LoggingContext() {
    }

    public static void setLogFileName(String name) {
        ThreadContext.put(LOG_FILE_KEY, name);
    }

    public static void clear() {
        ThreadContext.remove(LOG_FILE_KEY);
    }
}
