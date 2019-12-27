package hw11.config;


import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;
import ru.otus.hw11.hw10.config.HibernateConfigImpl;
import ru.otus.hw11.hw10.model.Address;
import ru.otus.hw11.hw10.model.Phone;
import ru.otus.hw11.hw10.model.User;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HibernateConfigTest {

    private static final String URL = "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1";

    private static final  Configuration CONFIGURATION = new Configuration()
            .setProperty("service.dialect", "org.service.dialect.H2Dialect")
            .setProperty("service.connection.driver_class", "org.h2.Driver")
            .setProperty("service.connection.url", URL)
            .setProperty("service.show_sql", "true")
            .setProperty("service.hbm2ddl.auto", "create")
            .setProperty("service.generate_statistics", "true");
    
    @Test
    void hibernateConfigWithNull() {
        assertThrows(IllegalArgumentException.class, ()-> { new HibernateConfigImpl(null); });
    }

    @Test
    void hibernateConfigWithEmptyConfig() {
        assertThrows(IllegalStateException.class, ()-> { new HibernateConfigImpl(new Configuration()); });
    }

    @Test
    void hibernateConfig1() {
        HibernateConfigImpl hibernate =  new HibernateConfigImpl(CONFIGURATION, User.class, Address.class, Phone.class);
        assertNotNull(hibernate);
    }

    @Test
    void hibernateConfig2() {
        HibernateConfigImpl hibernate =  new HibernateConfigImpl(CONFIGURATION, Address.class, Phone.class, User.class);
        assertNotNull(hibernate);
    }
}
