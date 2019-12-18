package ru.otus.hw09.service;

import org.slf4j.LoggerFactory;
import ru.otus.hw09.model.Id;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ParseObjectOrClassImp implements ParseObjectOrClass {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ParseObjectOrClassImp.class);

    private Object object;
    private Class<?> clazz;
    private String tableName;
    private Field[] fields;
    private String fieldsList;

    public Field getFieldId() {
        return fieldId;
    }

    private Field fieldId;
    private Field[] fieldsWithOutID;

    private String insertCommand;
    private String updateCommand;
    private String selectCommand;

    private Map<Integer,Object> insertValues;
    private Map<Integer,Object> updateValues;

    public ParseObjectOrClassImp(Object object) {

        if (object != null) {
            this.object = object;
            this.clazz = object.getClass();
            if (initParser()) {
                createSqlForObject();
                createValuesForObject();
            }
        } else {
            throw new IllegalArgumentException("Object is null!");
        }
    }

    public ParseObjectOrClassImp(Class<?> clazz) {

        if (clazz != null) {
            this.clazz = clazz;
            if (initParser()) {
                createSqlForObject();
            }
        } else {
            throw new IllegalArgumentException("Class is null!");
        }
    }

    private boolean initParser() {
        try {
            tableName = clazz.getSimpleName();
            fields = getFields(clazz);
            fieldsWithOutID = getNotIdFields(clazz);
            fieldsList = getFieldsNames(clazz);
            fieldId = getIdField(clazz);
            if (fieldId == null)  throw new IllegalStateException("Annotation Id not found!");

            return true;
        }catch (Exception e) {
            throw new ParseObjectOrClassException(e);
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
    }


    private Field[] getFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }

    private String getFieldsNames(Class<?> clazz) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Field field : getFields(clazz)) {
            String name = field.getName();
            if (!Objects.equals(field, getIdField(clazz))) {
                joiner.add(name);
            }
        }
        return joiner.toString();
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
        List<Field> list = new ArrayList<>();
        for (Field x : clazz.getDeclaredFields()) {
            if (!x.equals(getIdField(clazz))) {
                list.add(x);
            }
        }
        return list.toArray(new Field[0]);
    }

    private void createInsertCommand() {
        insertCommand =  String.format("insert into %s (%s) values (%s)",
                tableName,
                fieldsList,
                String.join(", ", Collections.nCopies(fieldsWithOutID.length, "?")));
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
        logger.debug("getInsertCommand {}",insertCommand);
        return insertCommand;
    }

    public String getUpdateCommand() {
        logger.debug("getUpdateCommand {}",updateCommand);
        return updateCommand;
    }

    public String getSelectCommand() {
        logger.debug("getSelectCommand {}",selectCommand);
        return selectCommand;
    }

    private void createInsertValues() {
        this.insertValues = getListValues(fieldsWithOutID);
    }

    private void createUpdateValues() {
        this.updateValues = getListValues(fieldsWithOutID);
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
            throw new ParseObjectOrClassException(e);
        }
        return integerStringHashMap;
    }


    public Map<Integer, Object> getInsertValues() {
        return insertValues;
    }

    public Map<Integer, Object> getUpdateValues() {
        return updateValues;
    }


    public String getCreateCommand()   {
        String sCreateCommand = String.format("create table IF NOT EXISTS %s (%s)",
                tableName, createDeclarationForCreateTableCommand(fields));
        logger.debug("getInsertCommand {}",sCreateCommand);
        return sCreateCommand;
    }

    private String createDeclarationForCreateTableCommand(Field[] fields)  {
        StringBuilder sResult = new StringBuilder();
        for (int i = 0; i <= fields.length -1 ; i++) {
            fields[i].setAccessible(true);
                 if (((int.class.equals(fields[i].getType())) ||  (Integer.class.equals(fields[i].getType()))
                     || (short.class.equals(fields[i].getType())) || (Short.class.equals(fields[i].getType()))
                     || (byte.class.equals(fields[i].getType())) || (Byte.class.equals(fields[i].getType()) )))  {
                     sResult.append(fields[i].getName()).append(" int (3) ");
                } else if ((long.class.equals(fields[i].getType())) || (Long.class.equals(fields[i].getType()))) {
                     sResult.append(fields[i].getName()).append(" bigint (20) NOT NULL auto_increment ");
                 }else if ((String.class.equals(fields[i].getType())) || (Character.class.equals(fields[i].getType()))) {
                     sResult.append(fields[i].getName()).append(" varchar(255) ");
                 } else if (BigDecimal.class.equals(fields[i].getType())) {
                     sResult.append(fields[i].getName()).append(" number ");
                 } else {
                     logger.error("Is not implemented. Type not supported! fieldName = {}, filedType={}",
                             fields[i].getName(), fields[i].getType());
                     throw new ParseObjectOrClassException("Is not implemented. Type not supported!");
                 }
                 if  (fields.length -1 != i)
                     sResult.append(",");
        }
        return  sResult.toString();
    }
}
