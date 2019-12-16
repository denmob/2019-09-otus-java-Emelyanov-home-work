package ru.otus.hw09.jdbc;

import org.slf4j.LoggerFactory;
import ru.otus.hw09.service.ParseObject;

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
    public void create(T objectData) {
        ParseObject parseObject = new ParseObject(objectData);
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
                logger.error("executeUpdate SQLException message= {}", e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }
        catch (Exception e) {
            logger.error("create Exception message= {}", e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public Object load(long id, Class clazz) {
        return null;
    }

    @Override
    public void update(Object objectData) {

    }



}
