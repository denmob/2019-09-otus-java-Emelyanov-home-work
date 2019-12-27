package ru.otus.hw11.hw10.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class UserDaoException extends RuntimeException {
    private final transient Logger logger = LoggerFactory.getLogger(UserDaoException.class);

    UserDaoException(Throwable cause) {
        super(cause);
        cause.printStackTrace();
        logger.error("ORMTemplateException ",cause);
    }


}
