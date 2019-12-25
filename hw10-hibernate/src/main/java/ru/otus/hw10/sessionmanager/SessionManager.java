package ru.otus.hw10.sessionmanager;


import org.hibernate.Session;

import java.sql.Connection;

public interface SessionManager extends AutoCloseable{

     Session getSession();

     Connection getConnection();

     void beginSession();

     void commit();

     void rollback();

     void close();
}
