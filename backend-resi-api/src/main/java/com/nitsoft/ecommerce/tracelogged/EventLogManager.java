/*
 */
package com.nitsoft.ecommerce.tracelogged;

import org.apache.log4j.*;

/**
 * @Class Name: EventLogManager.java
 * @brief: This class is implementation for managing event log.
 */

public class EventLogManager {

    private static EventLogManager instance = null;

    private Logger log;

    public static EventLogManager getInstance() {
        if (instance == null) {
            instance = new EventLogManager();
        }
        return instance;
    }

    private EventLogManager() {
        try {
            log = Logger.getRootLogger();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(EventLogManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     * @brief: This method is used to log message to file.
     * @param message
     */
    public void log(String logMessage) {
        //String strLevel = StewConfig.getInstance().logLevel;
        //log.fatal("StewConfig.getInstance().logLevel=" + strLevel);      
        //setLevel(Level.toLevel(strLevel));
        if (logMessage != null && !"".equals(logMessage)) {
            Level loglevel = log.getLevel();

            if (loglevel.equals(Level.TRACE)) {
                log.trace(logMessage);
            }
            if (loglevel.equals(Level.DEBUG)) {
                log.debug(logMessage);
            }
            if (loglevel.equals(Level.INFO)) {
                log.info(logMessage);
            }
            if (loglevel.equals(Level.WARN)) {
                log.warn(logMessage);
            }
            if (loglevel.equals(Level.ERROR)) {
                log.error(logMessage);
            }
            if (loglevel.equals(Level.FATAL)) {
                log.fatal(logMessage);
            }
        }
    }

        /// <summary>
    /// Log message as debug
    /// </summary>
    /// <param name="message">message to log</param>
    public void debug(Object message) {
        log.debug(message);
    }

        /// <summary>
    /// Log message as debug
    /// </summary>
    /// <param name="message">message to log</param>
    /// <param name="exception">exception log to file</param>
    public void debug(Object message, Exception exception) {
        log.debug(message, exception);
    }

        /// <summary>
    /// Log message as info
    /// </summary>
    /// <param name="message">message to log</param>
    public void info(Object message) {
        log.info(message);
    }

        /// <summary>
    /// Log message as info
    /// </summary>
    /// <param name="message">message to log</param>
    /// <param name="exception">exception log to file</param>
    public void info(Object message, Exception exception) {
        log.info(message, exception);
    }

        /// <summary>
    /// Log message as warning
    /// </summary>
    /// <param name="message">message to log</param>
    public void warn(Object message) {
        log.warn(message);
    }

        /// <summary>
    /// Log message as warning
    /// </summary>
    /// <param name="message">message to log</param>
    /// <param name="exception">exception log to file</param>
    public void warn(Object message, Exception exception) {
        log.warn(message, exception);
    }

        /// <summary>
    /// Log message as error
    /// </summary>
    /// <param name="message">message to log</param>
    public void error(Object message) {
        log.error(message);
    }

        /// <summary>
    /// Log message as error
    /// </summary>
    /// <param name="message">message to log</param>
    /// <param name="exception">exception log to file</param>
    public void error(Object message, Exception exception) {
        log.error(message, exception);
    }

        /// <summary>
    /// Log message as fatal
    /// </summary>
    /// <param name="message">message to log</param>
    public void fatal(Object message) {
        log.fatal(message);
    }

        /// <summary>
    /// Log message as fatal
    /// </summary>
    /// <param name="message">message to log</param>
    /// <param name="exception">exception log to file</param>
    public void fatal(Object message, Exception exception) {
        log.fatal(message, exception);
    }
}
