package com.DarkKeks.drm;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
    private static Logger logger = Logger.getLogger(Log.class.getName());

    public static void info(String line){
        logger.info(line);
    }

    public static void error(String line){
        logger.warning(line);
    }

    public static void logException(Throwable th){
        logger.log(Level.WARNING, "Exception: ", th);
    }
}
