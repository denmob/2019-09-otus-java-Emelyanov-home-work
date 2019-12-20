package ru.otus.hw10;

import org.junit.Test;
import ru.otus.hw10.config.Hibernate;

import org.hibernate.cfg.Configuration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class HibernateDemoTest {

    private static final String URL = "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1";

    private static final  Configuration CONFIGURATION = new Configuration()
            .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
            .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
            .setProperty("hibernate.connection.url", URL)
            .setProperty("hibernate.show_sql", "true")
            .setProperty("hibernate.hbm2ddl.auto", "create")
            .setProperty("hibernate.generate_statistics", "true");
    
    @Test
    public void hibernateConfigWithNull() {
        assertThrows(IllegalArgumentException.class, ()-> {
            new Hibernate(null);
        });
    }

    @Test
    public void hibernateConfigWithEmptyConfig() {

        assertThrows(IllegalStateException.class, ()-> {
            new Hibernate(new Configuration());
        });
    }

    @Test
    public void hibernateConfigWithEmptyConfig1() {
        Hibernate hibernate =  new Hibernate(CONFIGURATION);
        assertNotNull(hibernate);

    }
}
