package ru.otus.hw09.sessionmanager;

import java.sql.Connection;
import java.sql.SQLException;

public interface SessionManager {

     Connection getConnection();

     void commitSession();

     void rollbackSession();

     void close();
}
