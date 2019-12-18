package ru.otus.hw09.service;


import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Map;

public class DbExecutorImp<T> implements DbExecutor<T> {

    private ParseObjectOrClass parseObjectOrClass;
    private String savePointName = "savePointName";
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(DbExecutorImp.class);



    @Override
    public boolean createTable(Connection connection, Class<?> clazz) {
        if (connection == null) throw new IllegalArgumentException("connection is null");
        if (clazz == null) throw new IllegalArgumentException("clazz is null");

        try{
            parseObjectOrClass = new ParseObjectOrClassImp(clazz);
            try (PreparedStatement pst = connection.prepareStatement(parseObjectOrClass.getCreateCommand())) {
                pst.execute();
                connection.commit();
                return true;
            }
        }catch (SQLException e) {
            throw new DbExecutorException(e);
        }
    }

    @Override
    public T insert(Connection connection, T object) throws SQLException {
        if (connection == null) throw new IllegalArgumentException("connection is null");
        if (object == null) throw new IllegalArgumentException("object is null");
        Savepoint savePoint = connection.setSavepoint(savePointName);
        try{
            parseObjectOrClass = new ParseObjectOrClassImp(object);
            try (PreparedStatement pst = connection.prepareStatement(parseObjectOrClass.getInsertCommand(), Statement.RETURN_GENERATED_KEYS)) {
                insertMapValuesToPrepareStatement(pst, parseObjectOrClass.getInsertValues());
                pst.executeUpdate();

                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        Object id = rs.getObject(1);
                        Field idField = parseObjectOrClass.getFieldId();
                        if (idField == null) logger.debug("{}: {}", "null", id);
                        else logger.debug("{}: {}", idField.getName(), id);
                        if (idField != null && id != null) {
                            idField.set(object, id);
                        }
                    }
                }
            }
        }catch (Exception e) {
            connection.rollback(savePoint);
            throw new DbExecutorException(e);
        }
        return object;
    }

    @Override
    public T select(Connection connection, long id, Class<?> clazz) throws SQLException {
        if (connection == null) throw new IllegalArgumentException("connection is null");
        if (clazz == null) throw new IllegalArgumentException("clazz is null");
        if (id<1) throw new IllegalArgumentException("id < 1 ");

        Savepoint savePoint = connection.setSavepoint(savePointName);
        T obj = null;
        try{
            parseObjectOrClass = new ParseObjectOrClassImp(clazz);
            try (PreparedStatement pst = connection.prepareStatement(parseObjectOrClass.getSelectCommand())) {

                pst.setObject(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        obj = (T) clazz.getDeclaredConstructor().newInstance();

                        Field[] fields = clazz.getDeclaredFields();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            String fieldName = field.getName();
                            String fieldName1 = parseObjectOrClass.getFieldId().getName();
                            if  (!fieldName.equals(fieldName1)) {
                                Object value = rs.getObject(fieldName);
                                field.set(obj, value);
                            } else {
                                field.set(obj, id);
                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
                connection.rollback(savePoint);
                throw new DbExecutorException(e);
        }
        return obj;
    }

    @Override
    public T update(Connection connection, T object) throws SQLException {
        if (connection == null) throw new IllegalArgumentException("connection is null");
        if (object == null) throw new IllegalArgumentException("object is null");

        Savepoint savePoint = connection.setSavepoint(savePointName);

        try{
            parseObjectOrClass = new ParseObjectOrClassImp(object);
            try (PreparedStatement pst = connection.prepareStatement(parseObjectOrClass.getUpdateCommand())) {
                int count = insertMapValuesToPrepareStatement(pst, parseObjectOrClass.getUpdateValues());
                pst.setObject(++count, parseObjectOrClass.getFieldId().get(object));
                pst.execute();
            }
            connection.commit();
            return object;
        }catch (Exception e) {
            connection.rollback(savePoint);
            throw new DbExecutorException(e);
        }

    }

    @Override
    public T insertOrUpdate(Connection connection, T object) {
        ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(object);
        try{
            if (parseObject.getFieldId().get(object) == null) {
                return insert(connection, object);
            } else {
               return update(connection, object);
            }
        }catch (Exception e) {
            throw new DbExecutorException(e);
        }
    }

    private int insertMapValuesToPrepareStatement(PreparedStatement pst, Map<Integer,Object> integerObjectMap) throws SQLException {
        int count =0;
        for (int i=1; i<= integerObjectMap.size(); i++) {
            pst.setObject(i, integerObjectMap.get(i));
            logger.debug("parameterIndex {}, value {}",i,integerObjectMap.get(i));
            count++;
        }
        return count;
    }
}
