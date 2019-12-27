package ru.otus.hw11.hw10.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.hw11.hw10.model.Address;
import ru.otus.hw11.hw10.model.Phone;
import ru.otus.hw11.hw10.model.User;

import java.util.Arrays;
import java.util.Properties;


public class HibernateConfigImpl implements HibernateConfig {

    private SessionFactory sessionFactory;
    private Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
    private Class[] classes = {User.class, Address.class, Phone.class};

    public HibernateConfigImpl() {
        sessionFactory = createSessionFactory(configuration,classes);
    }

    public HibernateConfigImpl(Configuration configuration, Class ...annotatedClasses) {
        checkExternalConfig(configuration);
        if (annotatedClasses.length ==0)  throw new IllegalArgumentException("AnnotatedClasses is empty!");
        this.configuration = configuration;
        this.classes = annotatedClasses;
        sessionFactory = createSessionFactory(configuration,annotatedClasses);
    }


    private void checkExternalConfig(Configuration configuration) {
        if (configuration == null)
            throw new IllegalArgumentException("Configuration is null!");

        Properties properties = configuration.getProperties();
        if (properties.getProperty("service.connection.url")==null)
            throw new IllegalStateException("service.connection.url is null!");
        if (properties.getProperty("service.dialect")==null)
            throw new IllegalStateException("service.dialect is null!");
        if (properties.getProperty("service.connection.driver_class")==null)
            throw new IllegalStateException("service.connection.driver_class is null!");
    }

    private static SessionFactory createSessionFactory(Configuration configuration, Class... annotatedClasses) {

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Arrays.stream(annotatedClasses).forEach(metadataSources::addAnnotatedClass);
        Metadata metadata = metadataSources.getMetadataBuilder().build();
        return  metadata.getSessionFactoryBuilder().build();
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
