package ru.otus.hw09.jdbc;

import org.slf4j.LoggerFactory;

public class JDBCTemplateNotFoundObjectException extends RuntimeException {
        public JDBCTemplateNotFoundObjectException(String message) {
            super(message);
            org.slf4j.Logger logger = LoggerFactory.getLogger(JDBCTemplateException.class);
            logger.error("JDBCTemplateException message={}",message);
        }
    }

