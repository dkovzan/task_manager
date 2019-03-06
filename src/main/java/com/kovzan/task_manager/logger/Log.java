package com.kovzan.task_manager.logger;

import java.util.logging.Logger;

public class Log {

    public static final Logger logger;

    private Log() {}

    static {
        logger = Logger.getLogger(Log.class.getName());
    }


}
