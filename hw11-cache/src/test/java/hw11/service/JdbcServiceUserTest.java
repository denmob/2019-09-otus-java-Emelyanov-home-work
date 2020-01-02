package hw11.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.cachehw.HwCache;
import ru.otus.hw11.cachehw.HwCacheImpl;
import ru.otus.hw11.cachehw.HwListener;
import ru.otus.hw11.hw10.dao.UserDao;
import ru.otus.hw11.hw10.dao.UserDaoJdbc;
import ru.otus.hw11.hw10.model.User;
import ru.otus.hw11.hw10.service.ORMServiceUser;
import ru.otus.hw11.hw10.service.ORMServiceUserImpl;
import ru.otus.hw11.hw10.sessionmanager.SessionManager;
import ru.otus.hw11.hw10.sessionmanager.SessionManagerJdbc;

import java.lang.ref.WeakReference;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JdbcServiceUserTest {
    private static final Logger logger = LoggerFactory.getLogger(JdbcServiceUserTest.class);


    private SessionManager sessionManager;
    private UserDao userDao;
    private ORMServiceUser ormServiceUser;
    private HwCache<Long, User> cache;
    private HwListener<Long, User> listener;

    @BeforeEach
    void setUp() {
        sessionManager = new SessionManagerJdbc();
        userDao  = new UserDaoJdbc(sessionManager);
        cache = new HwCacheImpl<>(100, 1);
        listener = (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);
        cache.addListenerWeak(new WeakReference<>(listener));

        ormServiceUser = new ORMServiceUserImpl(userDao,cache);
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

        logger.info("user before save: {}",user);
        ormServiceUser.saveEntity(user);
        logger.info("user after save: {}",user);

        assertTrue(user.getId()>0);
        Optional<User> optionalUser = ormServiceUser.getEntity(user.getId());
        assertTrue(optionalUser.isPresent());


        User selectedUser = optionalUser.get();
        logger.info("user selected: {}",user);
        assertNotNull(selectedUser);

        selectedUser.setName("Max");
        selectedUser.setAge(66);
        ormServiceUser.saveEntity(user);

        optionalUser = ormServiceUser.getEntity(user.getId());
        assertTrue(optionalUser.isPresent());
        User selectedUser1 = optionalUser.get();

        assertEquals(user.getName(),selectedUser1.getName());
        assertEquals(user.getAge(),selectedUser1.getAge());
    }

}
