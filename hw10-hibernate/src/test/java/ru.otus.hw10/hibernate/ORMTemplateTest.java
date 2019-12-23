package ru.otus.hw10.hibernate;


import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw10.config.HibernateConfig;
import ru.otus.hw10.config.HibernateConfigImpl;
import ru.otus.hw10.model.Address;
import ru.otus.hw10.model.Phone;
import ru.otus.hw10.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ORMTemplateTest {

    private static final Logger logger = LoggerFactory.getLogger(ORMTemplateTest.class);
    private final HibernateConfig hibernateConfig  = new HibernateConfigImpl();
    private SessionFactory sessionFactory;
    private ORMTemplate ormTemplate;

    @BeforeEach
    void setUp() {
        sessionFactory = hibernateConfig.getSessionFactory();
        ormTemplate = new ORMTemplateImpl(sessionFactory);
    }

    @AfterEach
    void tearDown() {
        sessionFactory.close();
    }


    @Test
    void createWithNull() {
        assertThrows(IllegalArgumentException.class, ()-> { new ORMTemplateImpl(null); });
    }

    @Test
    void saveNullEntity() {
        assertThrows(IllegalArgumentException.class, ()-> { ormTemplate.saveEntity(null); });
    }


    @Test
    void getNullEntity() {
        assertThrows(IllegalArgumentException.class, ()-> { ormTemplate.getEntity(0); });
    }


    @Test
    void getEntityNoFound() {
        long userId = 1;
        Optional<User> user1 = ormTemplate.getEntity(userId);
       // user1.orElseThrow(() -> new ORMTemplateException("User not found with userId " + userId));
        assertFalse(user1.isPresent());
    }

    @Test
    void saveEntity() {
        User user = new User();
        logger.info("user before save: {}",user);
        ormTemplate.saveEntity(user);
        logger.info("user after save: {}",user);
        assertTrue(user.getId()>0);
    }

    @Test
    void getEntity() {
        User user = new User();
        logger.info("user before save: {}",user);
        ormTemplate.saveEntity(user);
        logger.info("user after save: {}",user);
        assertTrue(user.getId()>0);
        Optional<User> user1 = ormTemplate.getEntity(user.getId());
        logger.info("user selected: {}",user);
        assertNotNull(user1);
    }

    @Test
    void logicTest() {
        User user = new User();
        user.setName("Den");
        user.setAge(31);
        Address address = new Address("user_address_street",user);
        user.setAddress(address);
        List<Phone> listPhone = new ArrayList<>();
        listPhone.add(new Phone("user_number_phone  123", user));
        listPhone.add(new Phone("user_number_phone  456", user));
        listPhone.add(new Phone("user_number_phone  789", user));
        user.setPhoneDataSet(listPhone);

        logger.info("user before save: {}",user);
        ormTemplate.saveEntity(user);
        logger.info("user after save: {}",user);

        assertTrue(user.getId()>0);
        Optional<User> optionalUser = ormTemplate.getEntity(user.getId());
        assertTrue(optionalUser.isPresent());

        User selectedUser = optionalUser.get();
        logger.info("user selected: {}",user);
        assertNotNull(selectedUser);


        assertEquals(user.getName(),selectedUser.getName());
        assertEquals(user.getAge(),selectedUser.getAge());

        assertEquals(user.getAddress(),selectedUser.getAddress());

        for (int i=0; i< listPhone.size();i++) {
            assertEquals(listPhone.get(i).getNumber(),selectedUser.getPhoneDataSet().get(i).getNumber());
        }
        logger.info("user PhoneDataSet: {}", Collections.singletonList(user.getPhoneDataSet()));

    }


}
