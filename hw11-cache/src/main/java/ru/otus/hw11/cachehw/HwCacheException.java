package ru.otus.hw11.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HwCacheException extends RuntimeException{

    private final transient Logger logger = LoggerFactory.getLogger(HwCacheException.class);

    HwCacheException(Throwable cause) {
        super(cause);
        cause.printStackTrace();
        logger.error("ORMTemplateException ",cause);
    }
}
