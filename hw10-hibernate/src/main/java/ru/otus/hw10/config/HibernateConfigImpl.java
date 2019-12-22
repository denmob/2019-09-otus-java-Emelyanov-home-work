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

import java.util.Arrays;
import java.util.Properties;


public class HibernateConfigImpl implements HibernateConfig {

    private SessionFactory sessionFactory;
    private Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
    private Class[] classes = {User.class, Address.class, Phone.class};

    public HibernateConfigImpl() {
        createSessionFactory(this.classes);
    }

    public HibernateConfigImpl(Configuration configuration, Class ...annotatedClasses) {
        checkExternalConfig(configuration);
        if (annotatedClasses.length ==0)  throw new IllegalArgumentException("AnnotatedClasses is empty!");
        this.configuration = configuration;
        this.classes = annotatedClasses;
        createSessionFactory(annotatedClasses);
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

    private void createSessionFactory(Class ...annotatedClasses) {

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Arrays.stream(annotatedClasses).forEach(metadataSources::addAnnotatedClass);
        Metadata metadata = metadataSources.getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
