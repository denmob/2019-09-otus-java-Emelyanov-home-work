package ru.otus.hw09.service;

import java.util.Map;

public interface ParseObjectOrClass {

     String getCreateCommand();

     String getInsertCommand();

     String getUpdateCommand();

     String getSelectCommand();

     Map<Integer, Object> getInsertValues();

     Map<Integer, Object> getUpdateValues();

}
