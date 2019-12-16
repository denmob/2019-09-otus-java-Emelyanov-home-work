package ru.otus.hw09;


import org.slf4j.LoggerFactory;
import ru.otus.hw09.model.Account;
import ru.otus.hw09.model.User;
import ru.otus.hw09.service.DbExecutorImp;

import java.math.BigDecimal;

public class Main {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)  {

        DbExecutorImp dbExecutorImp = new DbExecutorImp();

        Account account1 = new Account(1,"type1", BigDecimal.valueOf(77));
        dbExecutorImp.saveObject(account1);

        Account account2  = (Account) dbExecutorImp.loadObject(account1.getNo(), Account.class);

        account2.setType("type33");
        account2.setRest(BigDecimal.valueOf(99));

        dbExecutorImp.updateObject(account2);

        Account account3  = (Account)  dbExecutorImp.loadObject(account1.getNo(), Account.class);
        logger.debug("saveObject {}",account1);
        logger.debug("loadObject {}",account2);
        logger.debug("updateObject {}",account3);



        User user1 = new User(1, "Bill", 2);
        dbExecutorImp.saveObject(user1);

        User user2 = (User) dbExecutorImp.loadObject(user1.getId(), User.class);

        user2.setName("Will");
        user2.setAge(77);

        dbExecutorImp.updateObject(user2);

        User user3 = (User) dbExecutorImp.loadObject(user1.getId(), User.class);
        logger.debug("saveObject {}",user1);
        logger.debug("loadObject {}",user2);
        logger.debug("updateObject {}",user3);


        Account account4 = new Account(1,"type1", BigDecimal.valueOf(77));
        dbExecutorImp.createOrUpdate(account1);
        Account account5 = new Account(1,"type89", BigDecimal.valueOf(12));
        dbExecutorImp.createOrUpdate(account2);
        Account account6  = (Account)  dbExecutorImp.loadObject(account1.getNo(), Account.class);

        logger.debug("createOrUpdate first {}",account4);
        logger.debug("createOrUpdate second {}",account5);
        logger.debug("createOrUpdate result {}",account6);


    }
}
