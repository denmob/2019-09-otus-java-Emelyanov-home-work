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

public class HibernateConfigDefaultImpl implements HibernateConfig {

    private SessionFactory sessionFactory;

    public HibernateConfigDefaultImpl() {

        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);

        Class[] classes = {User.class, Address.class, Phone.class};
        Arrays.stream(classes).forEach(metadataSources::addAnnotatedClass);

        Metadata metadata = metadataSources.getMetadataBuilder().build();
        this.sessionFactory= metadata.getSessionFactoryBuilder().build();
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
