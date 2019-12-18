package ru.otus.hw09.jdbc;

import org.slf4j.LoggerFactory;

public class JDBCTemplateException extends RuntimeException {

    JDBCTemplateException(Exception e) {
        super(e);
        e.printStackTrace();
        org.slf4j.Logger logger = LoggerFactory.getLogger(JDBCTemplateException.class);
        logger.error("JDBCTemplateException message={}",e.getMessage());
        logger.error("JDBCTemplateException ()",e);
    }


}
