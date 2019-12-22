package ru.otus.hw10.hibernate;


import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw10.config.HibernateConfig;
import ru.otus.hw10.config.HibernateConfigImpl;
import ru.otus.hw10.model.Address;
import ru.otus.hw10.model.Phone;
import ru.otus.hw10.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ORMTemplateTest {

    private final HibernateConfig hibernateConfig  = new HibernateConfigImpl();
    private SessionFactory sessionFactory;
    private ORMTemplate<User> ormTemplate;

    @BeforeEach
    void setUp() {
        sessionFactory = hibernateConfig.getSessionFactory();
        ormTemplate = new ORMTemplateImpl<>(sessionFactory);
    }

    @AfterEach
    void tearDown() {
        sessionFactory.close();
    }


    @Test
    void createWithNull() {
        assertThrows(IllegalArgumentException.class, ()-> {
            new ORMTemplateImpl(null);
        });
    }

    @Test
    void saveNullEntity() {
        assertThrows(IllegalArgumentException.class, ()-> {
            ormTemplate.saveEntity(null);
        });
    }


    @Test
    void getNullEntity() {
        assertThrows(IllegalArgumentException.class, ()-> {
            ormTemplate.getEntity(null,0);
        });
    }


    @Test
    void getEntityNoFound() {
        assertThrows(ORMTemplateException.class, ()-> {
            ormTemplate.getEntity(User.class,1);
        });
    }

    @Test
    void saveEntity() {
        User user = new User();
       ormTemplate.saveEntity(user);
       assertTrue(user.getId()>0);
    }

    @Test
    void getEntity() {
        User user = new User();
        ormTemplate.saveEntity(user);
        assertTrue(user.getId()>0);
        User user1 = ormTemplate.getEntity(User.class,user.getId());
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


        ormTemplate.saveEntity(user);

        assertTrue(user.getId()>0);
        User selectedUser = ormTemplate.getEntity(User.class, user.getId());
        assertNotNull(selectedUser);

        assertEquals(user.getName(),selectedUser.getName());
        assertEquals(user.getAge(),selectedUser.getAge());

        assertEquals(user.getAddress(),selectedUser.getAddress());

        for (int i=0; i< listPhone.size();i++) {
            assertEquals(listPhone.get(i).getNumber(),selectedUser.getPhoneDataSet().get(i).getNumber());
        }

    }


}
