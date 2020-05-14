package hw10.config;


import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw10.api.config.HibernateConfig;
import ru.otus.hw10.hibernate.config.HibernateConfigDefault1;
import ru.otus.hw10.hibernate.config.HibernateConfigDefault2;
import ru.otus.hw10.hibernate.config.HibernateConfigImpl;
import ru.otus.hw10.api.model.Address;
import ru.otus.hw10.api.model.Phone;
import ru.otus.hw10.api.model.User;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HibernateConfigTest {

    private static final Logger logger = LoggerFactory.getLogger(HibernateConfigTest.class);

    @Test
    void hibernateConfigWithNull() {
        Exception thrown = assertThrows(IllegalArgumentException.class, () -> {
            new HibernateConfigImpl(null);
        });
        logger.info(thrown.getMessage());
    }

    @Test
    void hibernateConfigWithEmptyConfig() {
        Exception thrown = assertThrows(IllegalStateException.class, () -> {
            new HibernateConfigImpl(new Configuration());
        });
        logger.info(thrown.getMessage());
    }

    @Test
    void hibernateConfigInvalid1() {
        Configuration configuration = new Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
                .setProperty("hibernate.connection.url", "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1")
                .setProperty("hibernate.show_sql", "true");

        Exception thrown =  assertThrows(IllegalStateException.class, () -> {
            new HibernateConfigImpl(configuration, User.class, Address.class, Phone.class);
        });
        logger.info(thrown.getMessage());
    }

    @Test
    void hibernateConfigInvalid2() {
        Configuration configuration = new Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        Exception thrown = assertThrows(IllegalStateException.class, () -> {
            new HibernateConfigImpl(configuration);
        });
        logger.info(thrown.getMessage());
    }


    @Test
    void hibernateConfigInvalid3() {
        Configuration configuration = new Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
                .setProperty("hibernate.connection.url", "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1");
        Exception thrown =  assertThrows(IllegalStateException.class, () -> {
            new HibernateConfigImpl(configuration,User.class,  User.class);
        });
        logger.info(thrown.getMessage());
    }


    @Test
    void hibernateConfigValid() {
        Configuration configuration = new Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
                .setProperty("hibernate.connection.url", "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "create");

        HibernateConfigImpl hibernate = new HibernateConfigImpl(configuration, Address.class, Phone.class, User.class);
        assertNotNull(hibernate);
    }

    @Test
    void defaultHibernateConfig1() {
        HibernateConfig hibernateConfigDefault1 = new HibernateConfigDefault1();
        assertNotNull(hibernateConfigDefault1);
        assertNotNull(hibernateConfigDefault1.getSessionFactory());
    }

    @Test
    void defaultHibernateConfig2() {
        HibernateConfig hibernateConfigDefault2 = new HibernateConfigDefault2();
        assertNotNull(hibernateConfigDefault2);
        assertNotNull(hibernateConfigDefault2.getSessionFactory());
    }

  //
}
