package ru.otus.hw11.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class DbExecutorException extends RuntimeException {

    private final transient Logger logger = LoggerFactory.getLogger(DbExecutorException.class);

    DbExecutorException(Throwable cause) {
        super(cause);
        cause.printStackTrace();
        logger.error("ORMTemplateException ",cause);
    }
}
