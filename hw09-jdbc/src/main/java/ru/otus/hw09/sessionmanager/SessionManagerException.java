package ru.otus.hw09.sessionmanager;

import org.slf4j.LoggerFactory;

public class SessionManagerException extends RuntimeException{
    SessionManagerException(Exception e) {
        super(e);
        e.printStackTrace();
        org.slf4j.Logger logger = LoggerFactory.getLogger(SessionManagerException.class);
        logger.error("SessionManagerException message= {}",e.getMessage());
        logger.error("SessionManagerException ()",e);
    }
}
