package ru.otus.hw09;

import org.slf4j.LoggerFactory;
import ru.otus.hw09.jdbc.JDBCTemplateImp;

public class MyException extends RuntimeException {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(JDBCTemplateImp.class);

    public MyException(String msg) {
        super(msg);
        logger.error(msg);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Message {}, Cause {}",message,cause);
    }
}