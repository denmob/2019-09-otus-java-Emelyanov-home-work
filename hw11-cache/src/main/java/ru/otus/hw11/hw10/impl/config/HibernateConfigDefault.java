package ru.otus.hw11.hw10.impl.config;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.otus.hw11.hw10.api.config.HibernateConfig;
import ru.otus.hw11.hw10.api.model.Address;
import ru.otus.hw11.hw10.api.model.Phone;
import ru.otus.hw11.hw10.api.model.User;

public class HibernateConfigDefault implements HibernateConfig {

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
