package ru.otus.hw09;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import ru.otus.hw09.jdbc.MyConnectionImp;
import ru.otus.hw09.model.Account;
import ru.otus.hw09.model.User;
import ru.otus.hw09.service.DbExecutorImp;

import java.math.BigDecimal;

import static org.junit.Assert.*;

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
        assertFalse(dbExecutorImp.saveObject(s));
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


    @Test
    public void loadObjectUser1(){
        User user1 = new User(1,"Bill",2);
        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        dbExecutorImp.saveObject(user1);
        Object user2 = dbExecutorImp.loadObject(user1.getId(),User.class);
        logger.debug("Expected User.toString() = {} ",user1.toString() );
        logger.debug("Actual User.toString() = {} ",user1.toString() );
        assertEquals(user1.toString(),user2.toString());
    }

    @Test
    public void loadObjectUser2(){
        User user1 = new User(1,"Bill",2);
        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        dbExecutorImp.saveObject(user1);
        Object user2 = dbExecutorImp.loadObject(2,User.class);
        assertNull(user2);
    }

    @Test
    public void loadObjectUser3(){
        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        Object user2 = dbExecutorImp.loadObject(2,User.class);
        assertNull(user2);
    }

    @Test
    public void loadObjectAccount1(){
        Account account1 = new Account(1,"type1", BigDecimal.valueOf(77));
        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        dbExecutorImp.saveObject(account1);
        Object account2 = dbExecutorImp.loadObject(account1.getNo(),Account.class);
        logger.debug("Expected Account.toString() = {} ",account1.toString() );
        logger.debug("Actual Account.toString() = {} ",account2.toString() );
        assertEquals(account1.toString(),account2.toString());
    }

    @Test
    public void loadObjectAccount2(){
        Account account1 = new Account(1,"type1", BigDecimal.valueOf(77));
        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        dbExecutorImp.saveObject(account1);
        Object account2 = dbExecutorImp.loadObject(2,Account.class);
        assertNull(account2);
    }

    @Test
    public void loadObjectAccount3(){
        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        Object account2 = dbExecutorImp.loadObject(2,Account.class);
        assertNull(account2);
    }



    @Test
    public void updateObjectUser1() {
        User user1 = new User(1, "Bill", 2);
        logger.debug("Expected User1.toString() = {} ", user1.toString());

        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        dbExecutorImp.saveObject(user1);

        User user2 = (User) dbExecutorImp.loadObject(user1.getId(), User.class);
        logger.debug("loadObject User2.toString() = {} ", user2.toString());

        assertEquals(user1.toString(), user2.toString());

        user2.setName("Will");
        user2.setAge(77);
        dbExecutorImp.updateObject(user2);

        User user3 = (User) dbExecutorImp.loadObject(user1.getId(), User.class);
        logger.debug("loadObject after update, User3.toString() = {} ", user3.toString());


        assertNotEquals(user1.toString(), user3.toString());
    }

    @Test
    public void updateObjectAccount1() {
        Account account1 = new Account(1,"type1", BigDecimal.valueOf(77));
        logger.debug("Expected account1.toString() = {} ", account1.toString());

        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        dbExecutorImp.saveObject(account1);

        Account account2  = (Account) dbExecutorImp.loadObject(account1.getNo(), Account.class);
        logger.debug("loadObject account2.toString() = {} ", account2.toString());

        assertEquals(account1.toString(), account2.toString());

        account2.setType("type33");
        account2.setRest(BigDecimal.valueOf(99));
        dbExecutorImp.updateObject(account2);

        Account account3  = (Account)  dbExecutorImp.loadObject(account1.getNo(), Account.class);
        logger.debug("loadObject after update, account3.toString() = {} ", account3.toString());


        assertNotEquals(account1.toString(), account3.toString());
    }

    @Test
    public void createOrUpdateUser1() {
        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        User user1 = new User(1, "Bill", 2);
        dbExecutorImp.createOrUpdate(user1);
        User user2 = new User(1, "Will", 3);
        dbExecutorImp.createOrUpdate(user2);
        User user3  = (User)  dbExecutorImp.loadObject(user1.getId(), User.class);
        assertEquals(user2.toString(), user3.toString());
    }

    @Test
    public void createOrUpdateUser2() {
        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        User user1 = new User(1, "Bill", 2);
        dbExecutorImp.createOrUpdate(user1);
        user1.setName("Tim");
        user1.setAge(99);
        dbExecutorImp.createOrUpdate(user1);
        User user2  = (User)  dbExecutorImp.loadObject(user1.getId(), User.class);
        assertEquals(user1.toString(), user2.toString());
    }

    @Test
    public void createOrUpdateAccount1() {
        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        Account account1 = new Account(1,"type1", BigDecimal.valueOf(77));
        dbExecutorImp.createOrUpdate(account1);
        Account account2 = new Account(1,"type89", BigDecimal.valueOf(12));
        dbExecutorImp.createOrUpdate(account2);
        Account account3  = (Account)  dbExecutorImp.loadObject(account1.getNo(), Account.class);
        assertEquals(account2.toString(), account3.toString());
    }

    @Test
    public void createOrUpdateAccount2() {
        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        Account account1 = new Account(1,"type1", BigDecimal.valueOf(77));
        dbExecutorImp.createOrUpdate(account1);

        account1.setType("type33");
        account1.setRest(BigDecimal.valueOf(99));

        dbExecutorImp.createOrUpdate(account1);
        Account account2  = (Account)  dbExecutorImp.loadObject(account1.getNo(), Account.class);
        assertEquals(account1.toString(), account2.toString());


    }


}
