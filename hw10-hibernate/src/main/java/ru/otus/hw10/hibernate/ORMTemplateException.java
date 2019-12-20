package ru.otus.hw10.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ORMTemplateException extends RuntimeException{

    private final Logger logger = LoggerFactory.getLogger(ORMTemplateException.class);

    public ORMTemplateException(String message, Throwable cause) {
        super(message, cause);
        cause.printStackTrace();
        logger.error("ORMTemplateException message {}",message);
    }

    public ORMTemplateException(String message) {
        super(message);
        logger.error("ORMTemplateException message {}",message);
    }

    public ORMTemplateException(Throwable cause) {
        super(cause);
        cause.printStackTrace();
        logger.error("ORMTemplateException ",cause);
    }

    public ORMTemplateException() {

    }
}