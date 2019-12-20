package ru.otus.hw10.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.hw10.model.Address;
import ru.otus.hw10.model.Phone;
import ru.otus.hw10.model.User;

import java.util.Properties;

public class Hibernate {

    private SessionFactory sessionFactory;
    private Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

    public Hibernate() {
        createSessionFactory();
    }

    public Hibernate(Configuration configuration) {
        checkExternalConfig(configuration);
        this.configuration = configuration;
        createSessionFactory();
    }

    private void checkExternalConfig(Configuration configuration) {
        if (configuration == null)
            throw new IllegalArgumentException("Configuration is null!");

        Properties properties = configuration.getProperties();
        if (properties.getProperty("hibernate.connection.url")==null)
            throw new IllegalStateException("hibernate.connection.url is null!");
        if (properties.getProperty("hibernate.dialect")==null)
            throw new IllegalStateException("hibernate.dialect is null!");
        if (properties.getProperty("hibernate.connection.driver_class")==null)
            throw new IllegalStateException("hibernate.connection.driver_class is null!");

    }

    private void createSessionFactory() {

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Phone.class)
                .getMetadataBuilder()
                .build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
