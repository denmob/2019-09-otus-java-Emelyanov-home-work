package ru.otus.hw09;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import ru.otus.hw09.jdbc.MyConnectionImp;
import ru.otus.hw09.model.Account;
import ru.otus.hw09.model.User;
import ru.otus.hw09.service.DbExecutorImp;

import java.math.BigDecimal;

import static org.junit.Assert.assertThrows;

public class DbExecutorTest {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserTest.class);



    @Test
    public void create1 (){
         new DbExecutorImp();
    }

    @Test
    public void create2 (){
         new DbExecutorImp(new MyConnectionImp(false));
    }

    @Test
    public void create3 (){
        assertThrows(IllegalArgumentException.class, () -> {
            new DbExecutorImp(new MyConnectionImp("", false));
        });
    }

    @Test
    public void create4 (){
        assertThrows(IllegalArgumentException.class, () -> {
            new DbExecutorImp(new MyConnectionImp("123213", false));
        });
    }

    @Test
    public void create5 (){
        new DbExecutorImp(new MyConnectionImp("jdbc:h2:mem:",false));
    }


    @Test
    public void saveObject1 (){
        User user = new User(1,"Bill",2);
        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        dbExecutorImp.saveObject(user);
    }

    @Test
    public void saveObject2 (){
        String s = "1";
        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        assertThrows(RuntimeException.class, () -> {
        dbExecutorImp.saveObject(s);
        });
    }

    @Test
    public void saveObject3 (){
        Account account = new Account(1,"Bill", BigDecimal.valueOf(77));
        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        dbExecutorImp.saveObject(account);
    }


    @Test
    public void saveObject4(){
        User user1 = new User(1,"Bill",2);
        User user2 = new User(2,"Max",20);
        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        dbExecutorImp.saveObject(user1);
        dbExecutorImp.saveObject(user2);
    }



}
