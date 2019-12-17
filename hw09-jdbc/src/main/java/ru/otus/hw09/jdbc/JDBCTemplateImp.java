package ru.otus.hw09.jdbc;

import ru.otus.hw09.MyException;
import ru.otus.hw09.service.DbExecutor;
import ru.otus.hw09.sessionmanager.SessionManager;
import java.sql.*;


public class JDBCTemplateImp<T> implements JDBCTemplate<T> {

    private SessionManager connectionManager;
    private DbExecutor dbExecutor;

    public JDBCTemplateImp(SessionManager connectionManager, DbExecutor dbExecutor) {
        this.connectionManager = connectionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public void createTable(Class<T> clazz) {
            dbExecutor.create(connectionManager.getConnection(),clazz);
    }

    @Override
    public void create(T objectData)  {
        try {
            dbExecutor.insert(connectionManager.getConnection(),objectData);
        }catch (SQLException e) {
            throw new MyException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public T load(long id, Class clazz) {
       try{
           return (T) dbExecutor.select(connectionManager.getConnection(),id,clazz);
        }catch (SQLException e) {
            throw new MyException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void update(T objectData){
        try{
            dbExecutor.update(connectionManager.getConnection(),objectData);
        }catch (SQLException e) {
            throw new MyException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void createOrUpdate(T objectData) {
        dbExecutor.createOrUpdate(connectionManager.getConnection(),objectData);

    }


}
