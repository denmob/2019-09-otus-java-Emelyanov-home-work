package ru.otus.hw09.jdbc;

import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

public class JDBCTemplateImp<T> implements  JDBCTemplate<T> {

    private final Connection connection;
    private static final String sSavepoint = "JDBCTemplate";
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JDBCTemplateImp.class);

    public JDBCTemplateImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(T objectData) {

        try (PreparedStatement pst = connection.prepareStatement(String.valueOf(objectData))) {
            Savepoint savePoint = this.connection.setSavepoint(sSavepoint);
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
    public void update(Object objectData) {

    }

    @Override
    public Object load(long id, Class clazz) {
        return null;
    }

}
