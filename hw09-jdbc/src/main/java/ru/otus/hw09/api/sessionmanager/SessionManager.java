package ru.otus.hw09.api.sessionmanager;

import java.sql.Connection;

public interface SessionManager {

     Connection getConnection();

     void commitSession();

     void rollbackSession();

     void close();
}
