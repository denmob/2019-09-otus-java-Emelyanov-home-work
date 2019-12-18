package ru.otus.hw09.service;

import org.slf4j.LoggerFactory;


public class DbExecutorException extends RuntimeException {

        DbExecutorException(Exception e) {
            super(e);
            e.printStackTrace();
            org.slf4j.Logger logger = LoggerFactory.getLogger(DbExecutorException.class);
            logger.error("DbExecutorException message={}",e.getMessage());
            logger.error("DbExecutorException ()",e);
        }

}
