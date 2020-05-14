package ru.otus.hw10.api.config;

import org.hibernate.SessionFactory;

public interface HibernateConfig {
    SessionFactory getSessionFactory();
}
