package hw11.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.hw10.api.cachehw.HwCache;
import ru.otus.hw11.hw10.impl.cachehw.HwCacheImpl;
import ru.otus.hw11.hw10.api.cachehw.HwListener;
import ru.otus.hw11.hw10.api.dao.UserDao;
import ru.otus.hw11.hw10.impl.jdbc.UserDaoJdbc;
import ru.otus.hw11.hw10.api.model.User;
import ru.otus.hw11.hw10.api.service.ORMServiceUser;
import ru.otus.hw11.hw10.impl.service.ORMServiceUserWithCacheImpl;
import ru.otus.hw11.hw10.impl.service.ORMServiceUserWithoutCacheImpl;
import ru.otus.hw11.hw10.api.sessionmanager.SessionManager;
import ru.otus.hw11.hw10.impl.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JdbcServiceUserTest {
    private final Logger logger = LoggerFactory.getLogger(JdbcServiceUserTest.class);


    private SessionManager sessionManager;
    private UserDao userDao;
    private ORMServiceUser ormServiceUserWithCache;
    private ORMServiceUser ormServiceUserWithoutCache;
    private HwCache<Long, User> cache;
    private HwListener listener;

    @BeforeEach
    void setUp() {
        sessionManager = new SessionManagerJdbc();
        userDao  = new UserDaoJdbc(sessionManager);
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
        ormServiceUserWithoutCache.saveEntity(user);
        logger.info("user after save: {}",user);
        assertTrue(user.getId()>0);
    }

    @Test
    void getEntity() {
        User user = new User();
        logger.info("user before save: {}",user);
        ormServiceUserWithoutCache.saveEntity(user);
        logger.info("user after save: {}",user);
        assertTrue(user.getId()>0);
        Optional<User> user1 = ormServiceUserWithoutCache.getEntity(user.getId());
        logger.info("user selected: {}",user);
        assertNotNull(user1);
    }

    @Test
    void logicTest() {
        User user = new User();
        user.setName("Den");
        user.setAge(31);

        logger.info("user before save: {}",user);
        ormServiceUserWithoutCache.saveEntity(user);
        logger.info("user after save: {}",user);

        assertTrue(user.getId()>0);
        Optional<User> optionalUser = ormServiceUserWithoutCache.getEntity(user.getId());
        assertTrue(optionalUser.isPresent());


        User selectedUser = optionalUser.get();
        logger.info("user selected: {}",user);
        assertNotNull(selectedUser);

        selectedUser.setName("Max");
        selectedUser.setAge(66);
        ormServiceUserWithoutCache.saveEntity(user);

        optionalUser = ormServiceUserWithoutCache.getEntity(user.getId());
        assertTrue(optionalUser.isPresent());
        User selectedUser1 = optionalUser.get();

        assertEquals(user.getName(),selectedUser1.getName());
        assertEquals(user.getAge(),selectedUser1.getAge());
    }

    private User createAnyUse() {
        User user = new User();
        user.setName("Den"+System.nanoTime());
        user.setAge(31);
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
    void CacheVsJdbc_2000elements () {

        long timeSaveAndGetEntityWithoutCache = getTimeSaveAndGetEntity(2000,ormServiceUserWithoutCache);
        long timeSaveAndGetEntityWithCache = getTimeSaveAndGetEntity(2000,ormServiceUserWithCache);
        long timeGetEntityWithCache = getTimeGetEntity(2000,ormServiceUserWithCache);

        logger.info("timeSaveAndGetEntityWithoutCache {} milliseconds", timeSaveAndGetEntityWithoutCache);
        logger.info("timeSaveAndGetEntityWithCache {} milliseconds", timeSaveAndGetEntityWithCache);
        logger.info("timeGetEntityWithCache {} milliseconds", timeGetEntityWithCache);

        assertTrue(timeSaveAndGetEntityWithoutCache<timeSaveAndGetEntityWithCache);
        assertTrue(timeSaveAndGetEntityWithCache>timeGetEntityWithCache);

    }

    @Test
    void CacheVsJdbc_1000elements () {

        long timeSaveAndGetEntityWithoutCache = getTimeSaveAndGetEntity(1000,ormServiceUserWithoutCache);
        long timeSaveAndGetEntityWithCache = getTimeSaveAndGetEntity(1000,ormServiceUserWithCache);
        long timeGetEntityWithCache = getTimeGetEntity(1000,ormServiceUserWithCache);

        logger.info("timeSaveAndGetEntityWithoutCache {} milliseconds", timeSaveAndGetEntityWithoutCache);
        logger.info("timeSaveAndGetEntityWithCache {} milliseconds", timeSaveAndGetEntityWithCache);
        logger.info("timeGetEntityWithCache {} milliseconds", timeGetEntityWithCache);

        assertTrue(timeSaveAndGetEntityWithoutCache<timeSaveAndGetEntityWithCache);
        assertTrue(timeSaveAndGetEntityWithCache>timeGetEntityWithCache);

    }

    @Test
    void CacheVsJdbc_500elements () {
        long timeSaveAndGetEntityWithoutCache = getTimeSaveAndGetEntity(500,ormServiceUserWithoutCache);
        long timeSaveAndGetEntityWithCache = getTimeSaveAndGetEntity(500,ormServiceUserWithCache);
        long timeGetEntityWithCache = getTimeGetEntity(500,ormServiceUserWithCache);

        logger.info("timeSaveAndGetEntityWithoutCache {} milliseconds", timeSaveAndGetEntityWithoutCache);
        logger.info("timeSaveAndGetEntityWithCache {} milliseconds", timeSaveAndGetEntityWithCache);
        logger.info("timeGetEntityWithCache {} milliseconds", timeGetEntityWithCache);

        assertTrue(timeSaveAndGetEntityWithoutCache<timeSaveAndGetEntityWithCache);
        assertTrue(timeSaveAndGetEntityWithCache>timeGetEntityWithCache);

    }

    @Test
    void CacheVsJdbc_100elements () {
        long timeSaveAndGetEntityWithoutCache = getTimeSaveAndGetEntity(100,ormServiceUserWithoutCache);
        long timeSaveAndGetEntityWithCache = getTimeSaveAndGetEntity(100,ormServiceUserWithCache);
        long timeGetEntityWithCache = getTimeGetEntity(100,ormServiceUserWithCache);

        logger.info("timeSaveAndGetEntityWithoutCache {} milliseconds", timeSaveAndGetEntityWithoutCache);
        logger.info("timeSaveAndGetEntityWithCache {} milliseconds", timeSaveAndGetEntityWithCache);
        logger.info("timeGetEntityWithCache {} milliseconds", timeGetEntityWithCache);

        assertTrue(timeSaveAndGetEntityWithoutCache>timeGetEntityWithCache);
        assertTrue(timeSaveAndGetEntityWithCache>timeGetEntityWithCache);
    }



}
