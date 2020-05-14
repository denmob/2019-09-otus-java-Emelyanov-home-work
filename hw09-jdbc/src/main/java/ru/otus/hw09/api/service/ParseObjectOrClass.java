package ru.otus.hw09.api.service;

import java.lang.reflect.Field;
import java.util.Map;

public interface ParseObjectOrClass {

     String getCreateCommand();

     String getInsertCommand();

     String getUpdateCommand();

     String getSelectCommand();

     Map<Integer, Object> getInsertValues();

     Map<Integer, Object> getUpdateValues();

     Field getFieldId();

}
