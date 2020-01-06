package hw11.service;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.cachehw.HwCache;
import ru.otus.hw11.cachehw.HwCacheImpl;
import ru.otus.hw11.cachehw.HwListener;
import ru.otus.hw11.hw10.config.HibernateConfig;
import ru.otus.hw11.hw10.config.HibernateConfigDefault;
import ru.otus.hw11.hw10.dao.UserDao;
import ru.otus.hw11.hw10.dao.UserDaoHibernate;
import ru.otus.hw11.hw10.model.Address;
import ru.otus.hw11.hw10.model.Phone;
import ru.otus.hw11.hw10.model.User;
import ru.otus.hw11.hw10.service.ORMServiceUser;
import ru.otus.hw11.hw10.service.ORMServiceUserWithCacheImpl;
import ru.otus.hw11.hw10.service.ORMServiceUserWithoutCacheImpl;
import ru.otus.hw11.hw10.sessionmanager.SessionManager;
import ru.otus.hw11.hw10.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HibernateServiceUserTest {
    private final Logger logger = LoggerFactory.getLogger(HibernateServiceUserTest.class);

    private final HibernateConfig hibernateConfig  = new HibernateConfigDefault();

    private SessionManager sessionManager;
    private UserDao userDao;
    private ORMServiceUser ormServiceUserWithCache;
    private ORMServiceUser ormServiceUserWithoutCache;
    private HwCache<Long, User> cache;
    private HwListener<Long, User> listener;

    @BeforeEach
    void setUp() {

        sessionManager = new SessionManagerHibernate(hibernateConfig.getSessionFactory());
        userDao  = new UserDaoHibernate(sessionManager);

        cache = new HwCacheImpl<>(1000, 1);
        listener = (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);
        cache.addListener(listener);

        ormServiceUserWithCache = new ORMServiceUserWithCacheImpl(userDao,cache);
        ormServiceUserWithoutCache = new ORMServiceUserWithoutCacheImpl(userDao);
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        sessionManager.close();
        Thread.sleep(100);
        System.gc();
        Thread.sleep(100);
    }

    @Test
    void saveEntity() {
        User user = new User();
        logger.info("user before save: {}",user);
        ormServiceUserWithCache.saveEntity(user);
        logger.info("user after save: {}",user);
        assertTrue(user.getId()>0);
    }

    @Test
    void getEntity() {
        User user = new User();
        logger.info("user before save: {}",user);
        ormServiceUserWithCache.saveEntity(user);
        logger.info("user after save: {}",user);
        assertTrue(user.getId()>0);
        Optional<User> user1 = ormServiceUserWithCache.getEntity(user.getId());
        logger.info("user selected: {}",user);
        assertNotNull(user1);
    }

    @Test
    void logicTest() {
        ormServiceUserWithCache = new ORMServiceUserWithCacheImpl(userDao,cache);

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
        ormServiceUserWithCache.saveEntity(user);
        logger.info("user after save: {}",user);

        assertTrue(user.getId()>0);
        Optional<User> optionalUser = ormServiceUserWithCache.getEntity(user.getId());
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

    private User createAnyUse() {
        User user = new User();
        user.setName("Den"+System.nanoTime());
        user.setAge(31);
        Address address = new Address("user_address_street",user);
        user.setAddress(address);
        List<Phone> listPhone = new ArrayList<>();
        listPhone.add(new Phone("user_number_phone  123", user));
        listPhone.add(new Phone("user_number_phone  456", user));
        listPhone.add(new Phone("user_number_phone  789", user));
        user.setPhoneDataSet(listPhone);
        return user;
    }

    private long getTimeSaveAndGetEntity(int countEntity, ORMServiceUser ormServiceUser) {

        for (int i=1;i<countEntity;i++) ormServiceUser.saveEntity(createAnyUse());
        long startTime = System.currentTimeMillis();
        for (int i=1;i<countEntity;i++) ormServiceUser.getEntity(i);
        long endTime = System.currentTimeMillis();
        return  (endTime - startTime);
    }

    private long getTimeGetEntity(int countEntity, ORMServiceUser ormServiceUser) {

        long startTime = System.currentTimeMillis();
        for (int i=1;i<countEntity;i++) ormServiceUser.getEntity(i);
        long endTime = System.currentTimeMillis();
        return  (endTime - startTime);
    }


    @Test
    void cacheVsHibernate_10000elements () {


        long timeSaveAndGetEntityWithoutCache = getTimeSaveAndGetEntity(10000,ormServiceUserWithoutCache);
        long timeSaveAndGetEntityWithCache = getTimeSaveAndGetEntity(10000,ormServiceUserWithCache);
        long timeGetEntityWithCache = getTimeGetEntity(10000,ormServiceUserWithCache);

        logger.info("timeSaveAndGetEntityWithoutCache {} milliseconds", timeSaveAndGetEntityWithoutCache);
        logger.info("timeSaveAndGetEntityWithCache {} milliseconds", timeSaveAndGetEntityWithCache);
        logger.info("timeGetEntityWithCache {} milliseconds", timeGetEntityWithCache);

        assertTrue(timeSaveAndGetEntityWithoutCache>timeSaveAndGetEntityWithCache);
        assertTrue(timeSaveAndGetEntityWithCache>timeGetEntityWithCache);
    }

    @Test
    void cacheVsHibernate_1000elements () {

        long timeSaveAndGetEntityWithoutCache = getTimeSaveAndGetEntity(1000,ormServiceUserWithoutCache);
        long timeSaveAndGetEntityWithCache = getTimeSaveAndGetEntity(1000,ormServiceUserWithCache);
        long timeGetEntityWithCache = getTimeGetEntity(1000,ormServiceUserWithCache);

        logger.info("timeSaveAndGetEntityWithoutCache {} milliseconds", timeSaveAndGetEntityWithoutCache);
        logger.info("timeSaveAndGetEntityWithCache {} milliseconds", timeSaveAndGetEntityWithCache);
        logger.info("timeGetEntityWithCache {} milliseconds", timeGetEntityWithCache);

        assertTrue(timeSaveAndGetEntityWithoutCache<timeSaveAndGetEntityWithCache);
        assertTrue(timeSaveAndGetEntityWithCache>timeGetEntityWithCache);
        assertTrue(timeSaveAndGetEntityWithoutCache>timeGetEntityWithCache);
    }

    @Test
    void cacheVsHibernate_500elements () {
        long timeSaveAndGetEntityWithoutCache = getTimeSaveAndGetEntity(500,ormServiceUserWithoutCache);
        long timeSaveAndGetEntityWithCache = getTimeSaveAndGetEntity(500,ormServiceUserWithCache);
        long timeGetEntityWithCache = getTimeGetEntity(500,ormServiceUserWithCache);

        logger.info("timeSaveAndGetEntityWithoutCache {} milliseconds", timeSaveAndGetEntityWithoutCache);
        logger.info("timeSaveAndGetEntityWithCache {} milliseconds", timeSaveAndGetEntityWithCache);
        logger.info("timeGetEntityWithCache {} milliseconds", timeGetEntityWithCache);

        assertTrue(timeSaveAndGetEntityWithCache>timeGetEntityWithCache);
        assertTrue(timeSaveAndGetEntityWithoutCache>timeGetEntityWithCache);
    }

    @Test
    void cacheVsHibernate_100elements () {
        long timeSaveAndGetEntityWithoutCache = getTimeSaveAndGetEntity(100,ormServiceUserWithoutCache);
        long timeSaveAndGetEntityWithCache = getTimeSaveAndGetEntity(100,ormServiceUserWithCache);
        long timeGetEntityWithCache = getTimeGetEntity(100,ormServiceUserWithCache);

        logger.info("timeSaveAndGetEntityWithoutCache {} milliseconds", timeSaveAndGetEntityWithoutCache);
        logger.info("timeSaveAndGetEntityWithCache {} milliseconds", timeSaveAndGetEntityWithCache);
        logger.info("timeGetEntityWithCache {} milliseconds", timeGetEntityWithCache);

        assertTrue(timeSaveAndGetEntityWithCache>timeGetEntityWithCache);
        assertTrue(timeSaveAndGetEntityWithoutCache>timeGetEntityWithCache);
    }










}
