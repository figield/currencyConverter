package com.currency.rest.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Log {

    public static void info(String format, Object object){
        log.info(format, object);
    }

    public static void error(String format, Object object){
        log.error(format, object);
    }
}
