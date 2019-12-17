package ru.otus.hw09.service;


import org.slf4j.LoggerFactory;
import ru.otus.hw09.MyException;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Map;

public class DbExecutorImp implements DbExecutor {

    private ParseObjectOrClass parseObjectOrClass;
    private String savePointName = "savePointName";
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(DbExecutorImp.class);


    @Override
    public boolean create(Connection connection, Class<?> clazz) {
        try{
            parseObjectOrClass = new ParseObjectOrClassImp(clazz);
            PreparedStatement pst = connection.prepareStatement(parseObjectOrClass.getCreateCommand());
            pst.execute();
            connection.commit();
            return true;
        }catch (SQLException e) {
            throw new MyException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean insert(Connection connection, Object object) throws SQLException {
        Savepoint savePoint = connection.setSavepoint(savePointName);
        try{
            parseObjectOrClass = new ParseObjectOrClassImp(object);
            PreparedStatement pst = connection.prepareStatement(parseObjectOrClass.getInsertCommand());
            insertMapValuesToPrepareStatement(pst,parseObjectOrClass.getInsertValues());
            pst.execute();
            connection.commit();
        return true;
        }catch (SQLException e) {
            connection.rollback(savePoint);
            throw new MyException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Object select(Connection connection, long id, Class<?> clazz) throws SQLException {
        Savepoint savePoint = connection.setSavepoint(savePointName);
        Object obj = null;
        try{
            parseObjectOrClass = new ParseObjectOrClassImp(clazz);
            PreparedStatement pst = connection.prepareStatement(parseObjectOrClass.getSelectCommand());

            pst.setObject(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    obj = clazz.getDeclaredConstructor().newInstance();

                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        String fieldName = field.getName();
                        Object value = rs.getObject(fieldName);
                        field.set(obj, value);
                    }
                }
            }
            }catch (Exception e) {
                connection.rollback(savePoint);
                throw new MyException(e.getMessage(), e.getCause());
            }
        return obj;
    }

    @Override
    public boolean update(Connection connection, Object object) throws SQLException {
        Savepoint savePoint = connection.setSavepoint(savePointName);

        try{
            parseObjectOrClass = new ParseObjectOrClassImp(object);
            PreparedStatement pst = connection.prepareStatement(parseObjectOrClass.getUpdateCommand());
            int count = insertMapValuesToPrepareStatement(pst,parseObjectOrClass.getUpdateValues());
            pst.setObject(++count, parseObjectOrClass.getFieldId().get(object));
            pst.execute();
            connection.commit();
            return true;
        }catch (Exception e) {
            connection.rollback(savePoint);
            throw new MyException(e.getMessage(), e.getCause());
        }

    }

    @Override
    public boolean createOrUpdate(Connection connection, Object object) {
        ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(object);
        try{
            if (select(connection,(Long) parseObject.getFieldId().get(object),object.getClass()) == null) {
                insert(connection, object);
            } else {
                update(connection, object);
            }
            return true;
        }catch (Exception e) {
            throw new MyException(e.getMessage(), e.getCause());
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
