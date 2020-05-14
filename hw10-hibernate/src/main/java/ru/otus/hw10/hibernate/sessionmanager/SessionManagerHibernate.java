package ru.otus.hw10.hibernate.sessionmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.otus.hw10.api.sessionmanager.SessionManager;
import ru.otus.hw10.api.sessionmanager.SessionManagerException;

import java.sql.Connection;

public class SessionManagerHibernate implements SessionManager {


    private final SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public SessionManagerHibernate(SessionFactory sessionFactory) {
        if (sessionFactory == null) {
            throw new SessionManagerException("SessionFactory is null");
        }
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Session getSession() {
        return session;
    }


    @Override
    public void beginSession() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @Override
    public void commit() {
        try {
            transaction.commit();
            session.close();
        } catch (Exception e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void rollback() {

        try {
            transaction.rollback();
            session.close();
        } catch (Exception e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void close() {

        if (session == null || !session.isConnected()) {
            return;
        }

        if (transaction == null || !transaction.isActive()) {
            return;
        }

        try {
            session.close();
        } catch (Exception e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public Connection getConnection() {
        throw new UnsupportedOperationException("Not implemented");
    }
}



