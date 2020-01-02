package ru.otus.hw11.hw10.config;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.otus.hw11.hw10.model.Address;
import ru.otus.hw11.hw10.model.Phone;
import ru.otus.hw11.hw10.model.User;

public class HibernateConfigDefault implements HibernateConfig{

    private SessionFactory sessionFactory;

    public HibernateConfigDefault() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        Class[] classes = {User.class, Address.class, Phone.class};
        this.sessionFactory = new HibernateConfigImpl(configuration, classes).getSessionFactory();
    }

    @Override
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }
}
