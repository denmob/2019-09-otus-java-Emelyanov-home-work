package ru.otus.hw10.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class ORMTemplateException extends RuntimeException{
    ORMTemplateException(Exception e) {
        super(e);
        e.printStackTrace();
        Logger logger = LoggerFactory.getLogger(ORMTemplateException.class);
        logger.error("ORMTemplateException message= {}",e.getMessage());
        logger.error("ORMTemplateException ()",e);
    }
}