package ru.otus.hw11.sessionmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SessionManagerException extends RuntimeException{

    private final transient Logger logger = LoggerFactory.getLogger(SessionManagerException.class);

    SessionManagerException(String message) {
        super(message);
        logger.error("ORMTemplateException message {}",message);
    }

    SessionManagerException(Throwable cause) {
        super(cause);
        cause.printStackTrace();
        logger.error("ORMTemplateException ",cause);
    }

}
