package ru.otus.hw09.jdbc;

import ru.otus.hw09.service.DbExecutor;
import ru.otus.hw09.sessionmanager.SessionManager;

import java.sql.SQLException;


public class JDBCTemplateImp<T> implements JDBCTemplate<T> {

    private SessionManager connectionManager;
    private DbExecutor dbExecutor;

    public JDBCTemplateImp(SessionManager connectionManager, DbExecutor dbExecutor) {
        this.connectionManager = connectionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public void createTable(Class<T> clazz) {
            dbExecutor.createTable(connectionManager.getConnection(),clazz);
    }

    @Override
    public Object create(T objectData)  {
        try {
            return dbExecutor.insert(connectionManager.getConnection(), objectData);
        }catch (SQLException e) {
            throw new JDBCTemplateException(e);
        }
    }


    @Override
    public T load(long id, Class clazz) {
       try{
           return (T) dbExecutor.select(connectionManager.getConnection(),id,clazz);
        }catch (SQLException e) {
           throw new JDBCTemplateException(e);
        }
    }

    @Override
    public void update(T objectData){
        try{
            dbExecutor.update(connectionManager.getConnection(),objectData);
        }catch (SQLException e) {
            throw new JDBCTemplateException(e);
        }
    }

    @Override
    public Object createOrUpdate(T objectData) {
        try{
          return dbExecutor.insertOrUpdate(connectionManager.getConnection(),objectData);
        }catch (Exception e) {
            throw new JDBCTemplateException(e);
        }
    }


}
