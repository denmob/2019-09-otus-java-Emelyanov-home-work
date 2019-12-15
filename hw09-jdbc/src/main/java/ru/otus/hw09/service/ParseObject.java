package ru.otus.hw09.service;

import org.slf4j.LoggerFactory;
import ru.otus.hw09.Id;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ParseObject {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ParseObject.class);

    private Object object;
    private Class<?> clazz;


    private String tableName;
    private Field[] fields;
    private String fieldsList;
    private Field fieldId;
    private Field[] fieldsWithOutID;

    private String insertCommand;
    private String updateCommand;
    private String selectCommand;

    private Map<Integer,Object> insertValues;
    private Map<Integer,Object> updateValues;
    private Map<Integer,Object> selectValues;

    public ParseObject(Object object) {

        if (object != null) {
            this.object = object;
            this.clazz = object.getClass();
            if (initParse()) {
                createSqlForObject();
                createValuesForObject();
            }
        } else {
            logger.error("Object is null!");
            throw new IllegalArgumentException("Object is null!");
        }
    }

    private boolean initParse() {
        try {
            tableName = clazz.getSimpleName();
            fields = getFields(clazz);
            fieldsList = getFieldsNames(clazz);
            fieldId = getIdField(clazz);
            if (fieldId == null)  throw new IllegalArgumentException("Annotation Id not found!");
            fieldsWithOutID = getNotIdFields(clazz);

            return true;
        }catch (Exception e) {
           logger.error("initParse Exception",e);
           return false;
        }
    }

    private void createSqlForObject() {
          createInsertCommand();
          createUpdateCommand();
          createSelectCommand();
    }

    private void createValuesForObject() {
        createInsertValues();
        createUpdateValues();
        createSelectValues();
    }


    private Field[] getFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }

    private String getFieldsNames(Class<?> clazz) {
        return Arrays.stream(getFields(clazz))
                .map(Field::getName)
                .collect(Collectors.joining(", "));
    }

    private Field getIdField(Class<?> clazz) {
        if (!clazz.isPrimitive()) {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getDeclaredAnnotation(Id.class) != null) {
                    return field;
                }
            }
        }
        return null;
    }

    private Field[] getNotIdFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(x -> !x.equals(getIdField(clazz)))
                .toArray(Field[]::new);
    }

    private void createInsertCommand() {
        insertCommand =  String.format("insert into %s (%s) values (%s)",
                tableName,
                fieldsList,
                String.join(", ", Collections.nCopies(fields.length, "?")));
    }

    private void createUpdateCommand() {
        updateCommand =  String.format("update %s set %s where %s = ?",
                tableName,
                Arrays.stream(fieldsWithOutID)
                        .map(x -> x.getName() + " = ?")
                        .collect(Collectors.joining(", ")),
                fieldId.getName());
    }

    private void createSelectCommand() {
        selectCommand =  String.format("select %s from %s where %s = ?",
                fieldsList, tableName, fieldId.getName());
    }


    public String getInsertCommand() {
        return insertCommand;
    }

    public String getUpdateCommand() {
        return updateCommand;
    }

    public String getSelectCommand() {
        return selectCommand;
    }

    private void createInsertValues() {
        this.insertValues = getListValues(fields);
    }

    private void createUpdateValues() {
        this.updateValues = getListValues(fieldsWithOutID);
    }

    private void createSelectValues() {
        this.selectValues = getListValues(new Field[]{fieldId});
    }


    private Map<Integer,Object>  getListValues(Field[] fields) {
        Map<Integer,Object>  integerStringHashMap= new HashMap<>();
        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Object value = fields[i].get(object);
                integerStringHashMap.put(i+1, value);
            }
        } catch (Exception e) {
            logger.error("getListValues Exception",e);
        }
        return integerStringHashMap;
    }


    public Map<Integer, Object> getInsertValues() {
        return insertValues;
    }

    public Map<Integer, Object> getUpdateValues() {
        return updateValues;
    }

    public Map<Integer, Object> getSelectValues() {
        return selectValues;
    }

    public String getCreateCommand()   {
        return String.format("create table %s (%s)",
                tableName, createDeclarationForCreateTableCommand(fields));
    }

    private String createDeclarationForCreateTableCommand(Field[] fields) {
        StringBuilder sResult = new StringBuilder();
        for (int i = 0; i <= fields.length -1 ; i++) {
            fields[i].setAccessible(true);
                 if (int.class.equals(fields[i].getType())) {
                     sResult.append(fields[i].getName()).append(" int (3) ");
                } else if (long.class.equals(fields[i].getType())) {
                     sResult.append(fields[i].getName()).append(" bigint (20) NOT NULL auto_increment ");
                 }else if (String.class.equals(fields[i].getType())) {
                     sResult.append(fields[i].getName()).append(" varchar(255) ");
                 } else if (BigDecimal.class.equals(fields[i].getType())) {
                     sResult.append(fields[i].getName()).append(" number ");
                 }
                 if  (fields.length -1 != i)
                     sResult.append(",");
        }
        return  sResult.toString();
    }
}
