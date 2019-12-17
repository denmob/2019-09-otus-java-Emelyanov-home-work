package ru.otus.hw09;


import org.junit.Test;
import org.slf4j.LoggerFactory;
import ru.otus.hw09.model.Account;
import ru.otus.hw09.model.User;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class UserTest {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserTest.class);

    @Test
    public void createUser1()  {
        User user = new User();
        logger.info("user.toString {}", user);
        String sUser = "User: id = 0, name = null, age = 0";
        assertEquals(sUser,user.toString());
    }

    @Test
    public void createUser2()  {
        User user = new User("Test",2);
        logger.info("user.toString {}", user);
        String sUser = "User: id = 1, name = Test, age = 2";
        assertEquals(sUser,user.toString());
    }

    @Test
    public void createAccount1()  {
        Account account = new Account();
        logger.info("account.toString {}", account);
        String sAccount= "Account: no = 0, type = null, rest = null";
        assertEquals(sAccount,account.toString());
    }

    @Test
    public void createAccount2()  {
        BigDecimal bigDecimal = new BigDecimal(2);
        Account account = new Account("Test",bigDecimal);
        logger.info("account.toString {}", account);
        String sAccount= "Account: no = 1, type = Test, rest = 2";
        assertEquals(sAccount,account.toString());
    }


}
