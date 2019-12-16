package ru.otus.hw09.jdbc;

import org.slf4j.LoggerFactory;
import ru.otus.hw09.service.ParseObjectOrClassImp;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Map;

public class JDBCTemplateImp<T> implements JDBCTemplate<T> {

    private final Connection connection;
    private static final String sSavepoint = "JDBCTemplate";
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JDBCTemplateImp.class);


    public JDBCTemplateImp(Connection connection) {
        this.connection = connection;
    }


    private void createTable(String sCreateCommand) {
        logger.debug("createTable sCreateCommand= {}",sCreateCommand);
         try (PreparedStatement pst = connection.prepareStatement(sCreateCommand)) {
             pst.execute();
             this.connection.commit();
         } catch (SQLException e) {
             logger.error("create Exception message= {}", e.getMessage());
             throw new IllegalArgumentException(e.getMessage());
         }
    }

    @Override
    public void create(T objectData) throws RuntimeException {
        ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(objectData);
        createTable(parseObject.getCreateCommand());

        try (PreparedStatement pst = connection.prepareStatement(parseObject.getInsertCommand())) {
            Savepoint savePoint = this.connection.setSavepoint(sSavepoint);
            Map<Integer,Object> insertValues = parseObject.getInsertValues();
            for (int i=1; i<= insertValues.size(); i++) {
                pst.setObject(i, insertValues.get(i));
                logger.debug("parameterIndex {}, value {}",i,insertValues.get(i));
            }
            try {
                pst.execute();
                this.connection.commit(); }
            catch (SQLException e) {
                this.connection.rollback(savePoint);
                logger.error("create SQLException message= {}", e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }
        catch (Exception e) {
            logger.error("create Exception message= {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public T load(long id, Class clazz) {
        ParseObjectOrClassImp parseObjectOrClassImp = new ParseObjectOrClassImp(clazz);
        T obj = null;
        try (PreparedStatement pst = connection.prepareStatement(parseObjectOrClassImp.getSelectCommand())) {
            Savepoint savePoint = this.connection.setSavepoint(sSavepoint);
                pst.setObject(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    obj = (T) clazz.getDeclaredConstructor().newInstance();

                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        String fieldName = field.getName();
                        Object value = rs.getObject(fieldName);
                        field.set(obj, value);
                    }
                }
            } catch (SQLException e) {
                this.connection.rollback(savePoint);
                logger.error("executeQuery SQLException message= {}", e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }
        catch (Exception e) {
            logger.error("create Exception message= {}", e.getMessage());
        }
        return obj;
    }

    @Override
    public void update(T objectData) throws RuntimeException {
        ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(objectData);

        try (PreparedStatement pst = connection.prepareStatement(parseObject.getUpdateCommand())) {
            Savepoint savePoint = this.connection.setSavepoint(sSavepoint);
            Map<Integer,Object> updateValues = parseObject.getUpdateValues();
            for (int i=1; i<= updateValues.size(); i++) {
                pst.setObject(i, updateValues.get(i));
                logger.debug("parameterIndex {}, value {}",i,updateValues.get(i));
            }
            pst.setObject( updateValues.size()+1, parseObject.getFieldId().get(objectData));
            try {
                pst.execute();
                this.connection.commit(); }
            catch (SQLException e) {
                this.connection.rollback(savePoint);
                logger.error("update SQLException message= {}", e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }
        catch (Exception e) {
            logger.error("create Exception message= {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void createOrUpdate(T objectData) throws RuntimeException, IllegalAccessException {
        ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(objectData);
        createTable(parseObject.getCreateCommand());
        if (load((Long) parseObject.getFieldId().get(objectData),objectData.getClass()) == null) {
            create(objectData);
        } else {
            update(objectData);
        }
    }


}
