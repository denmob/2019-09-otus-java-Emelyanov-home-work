package ru.otus.hw11.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ORMServiceException extends RuntimeException{

  private final transient Logger logger = LoggerFactory.getLogger(ORMServiceException.class);

  ORMServiceException(Throwable cause) {
    super(cause);
    cause.printStackTrace();
    logger.error("ORMTemplateException ",cause);
  }
}