package ru.otus.hw10.service;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw10.config.HibernateConfig;
import ru.otus.hw10.config.HibernateConfigDefault1;
import ru.otus.hw10.dao.UserDao;
import ru.otus.hw10.dao.UserDaoHibernate;
import ru.otus.hw10.model.Address;
import ru.otus.hw10.model.Phone;
import ru.otus.hw10.model.User;
import ru.otus.hw10.sessionmanager.SessionManager;
import ru.otus.hw10.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HibernateServiceUserTest {

    private static final Logger logger = LoggerFactory.getLogger(HibernateServiceUserTest.class);

    private HibernateConfig hibernateConfig;
    private SessionManager sessionManager;
    private UserDao userDao;
    private ORMServiceUser ormServiceUser;

    @BeforeEach
    void setUp() {
        hibernateConfig = new HibernateConfigDefault1();
        sessionManager = new SessionManagerHibernate(hibernateConfig.getSessionFactory());
        userDao  = new UserDaoHibernate(sessionManager);
        ormServiceUser = new ORMServiceUserImpl(userDao);
    }

    @AfterEach
    void tearDown() {
        sessionManager.close();
    }

    @Test
    void saveEntity() {
        User user = new User();
        logger.info("user before save: {}",user);
        ormServiceUser.saveEntity(user);
        logger.info("user after save: {}",user);
        assertTrue(user.getId()>0);
    }

    @Test
    void getEntity() {
        User user = new User();
        logger.info("user before save: {}",user);
        ormServiceUser.saveEntity(user);
        logger.info("user after save: {}",user);
        assertTrue(user.getId()>0);
        Optional<User> user1 = ormServiceUser.getEntity(user.getId());
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
        ormServiceUser.saveEntity(user);
        logger.info("user after save: {}",user);

        assertTrue(user.getId()>0);
        Optional<User> optionalUser = ormServiceUser.getEntity(user.getId());
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
