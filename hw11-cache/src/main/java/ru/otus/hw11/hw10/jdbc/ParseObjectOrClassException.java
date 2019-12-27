package ru.otus.hw11.hw10.jdbc;

import org.slf4j.LoggerFactory;

public class ParseObjectOrClassException extends RuntimeException{

    private org.slf4j.Logger logger = LoggerFactory.getLogger(DbExecutorException.class);

    ParseObjectOrClassException(Exception e) {
        super(e);
        e.printStackTrace();
        logger.error("ParseObjectOrClassException message={}",e.getMessage());
        logger.error("ParseObjectOrClassException ()",e);
    }


    public ParseObjectOrClassException(String message) {
        super(message);
        logger.error("ParseObjectOrClassException message={}",message);
    }
}
