package ru.otus.hw11.hw10.config;

import org.hibernate.SessionFactory;

public interface HibernateConfig {

    SessionFactory getSessionFactory();

}
