package ru.otus.hw10;

import org.junit.Test;
import ru.otus.hw10.config.Hibernate;
import ru.otus.hw10.hibernate.ORMTemplate;
import ru.otus.hw10.hibernate.ORMTemplateException;
import ru.otus.hw10.hibernate.ORMTemplateImpl;
import ru.otus.hw10.model.Address;
import ru.otus.hw10.model.Phone;
import ru.otus.hw10.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ORMTemplateTest {

    private final Hibernate hibernateConfig = new Hibernate();
    private final ORMTemplate<User> ormTemplateWithType = new ORMTemplateImpl<>(hibernateConfig.getSessionFactory());
    private final ORMTemplate ormTemplateWithOutType = new ORMTemplateImpl(hibernateConfig.getSessionFactory());
    private final User user = new User();

    @Test
    public void createWithNull() {
        assertThrows(IllegalArgumentException.class, ()-> {
            new ORMTemplateImpl(null);
        });
    }

    @Test
    public void saveNullEntity() {

        Hibernate hibernateConfig = new Hibernate();
        ORMTemplate<User> ormTemplate = new ORMTemplateImpl<>(hibernateConfig.getSessionFactory());

        assertThrows(IllegalArgumentException.class, ()-> {
            ormTemplate.saveEntity(null);
        });
    }

    @Test
    public void saveRandomEntity() {

        assertThrows(IllegalStateException.class, ()-> {
            String s = "123";
            ormTemplateWithOutType.saveEntity(s);
        });
    }

    @Test
    public void getNullEntity() {
        assertThrows(IllegalArgumentException.class, ()-> {
            ormTemplateWithOutType.getEntity(null,0);
        });
    }

    @Test
    public void getEntityWithInValidId() {
        assertThrows(IllegalArgumentException.class, ()-> {
            ormTemplateWithOutType.getEntity(String.class,0);
        });
    }

    @Test
    public void getRandomEntity() {
        assertThrows(IllegalStateException.class, ()-> {
            ormTemplateWithOutType.getEntity(String.class,1);
        });
    }

    @Test
    public void getEntityNoFound() {
        assertThrows(ORMTemplateException.class, ()-> {
            ormTemplateWithOutType.getEntity(User.class,1);
        });
    }

    @Test
    public void saveEntity() {
       long id = ormTemplateWithType.saveEntity(user);
       assertTrue(id>0);
    }

    @Test
    public void getEntity() {
        long id = ormTemplateWithType.saveEntity(user);
        assertTrue(id>0);
        User user1 = ormTemplateWithType.getEntity(User.class,id);
        assertNotNull(user1);
    }

    @Test
    public void logicTest() {

        user.setName("Den");
        user.setAge(31);
        Address address = new Address("user_address_street",user);
        user.setAddress(address);
        List<Phone> listPhone = new ArrayList<>();
        listPhone.add(new Phone("user_number_phone  123", user));
        listPhone.add(new Phone("user_number_phone  456", user));
        listPhone.add(new Phone("user_number_phone  789", user));
        user.setPhoneDataSet(listPhone);

        long id = ormTemplateWithType.saveEntity(user);
        assertTrue(id>0);
        User selectedUser = ormTemplateWithType.getEntity(User.class, id);
        assertNotNull(selectedUser);

        assertEquals(user.getName(),selectedUser.getName());
        assertEquals(user.getAge(),selectedUser.getAge());

        assertEquals(user.getAddress(),selectedUser.getAddress());

        for (int i=0; i< listPhone.size();i++) {
            assertEquals(listPhone.get(i).getNumber(),selectedUser.getPhoneDataSet().get(i).getNumber());
        }

    }


}
